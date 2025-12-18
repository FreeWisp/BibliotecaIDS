package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.model.UtenteConPrestiti;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import java.io.File;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PrestitiController {
    
    @FXML private TableView<Prestito> tblPrestiti;
    @FXML private TableColumn<Prestito, String> colLibro;
    @FXML private TableColumn<Prestito, String> colUtente;
    @FXML private TableColumn<Prestito, String> colDataRestituzione;
    
    @FXML private ListView<UtenteConPrestiti> listPrestitiPerUtente;
    
    @FXML private ComboBox<Libro> cmbLibri;
    @FXML private ComboBox<Utente> cmbUtenti;
    @FXML private TextField txtDurataPrestito;
    @FXML private Label lblCopieDisponibili;
    @FXML private Label lblPrestitiUtente;
    @FXML private Label lblDataPrestito;
    @FXML private Label lblDataScadenza;
    @FXML private Label lblPrestitiCount;
    @FXML private Label lblStatus;
    @FXML private Button btnRestituzione;
    
    private Biblioteca biblioteca;
    private MainController mainController;
    private ObservableList<Prestito> listaPrestiti;
    private ObservableList<UtenteConPrestiti> listaUtentiConPrestiti;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ServizioArchivio archivio;
    
    @FXML
    private void initialize() {
        listaPrestiti = FXCollections.observableArrayList();
        tblPrestiti.setItems(listaPrestiti);
        
        listaUtentiConPrestiti = FXCollections.observableArrayList();
        if (listPrestitiPerUtente != null) {
            listPrestitiPerUtente.setItems(listaUtentiConPrestiti);
            listPrestitiPerUtente.setCellFactory(lv -> new UtentiConPrestitiController());
            listPrestitiPerUtente.setVisible(false);
        }
        
        configuraCelleTabella();
        
        tblPrestiti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                boolean isRestituibile = newSelection != null && 
                    newSelection.getDataRestituzione() != null;  
                btnRestituzione.setDisable(!isRestituibile);
            }
        );
        
        cmbLibri.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    aggiornaCopieDisponibili(newSelection);
                    aggiornaInfoPrestito();
                }
            }
        );
        
        cmbUtenti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    aggiornaPrestitiUtente(newSelection);
                    aggiornaInfoPrestito();
                }
            }
        );
        
        txtDurataPrestito.textProperty().addListener((obs, old, val) -> aggiornaInfoPrestito());
        
        btnRestituzione.setDisable(true);
    }
    
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        caricaDatiJSON();
        caricaPrestiti();
        caricaComboLibri();
        caricaComboUtenti();
        aggiornaInfoPrestito();
    }

    private void caricaDatiJSON() {
        try {
            File file = new File("biblioteca/biblioteca.json");
            if (file.exists()) {
                ServizioArchivio.ArchivioData dati = biblioteca.getArchivioService().carica("biblioteca/biblioteca.json");
                biblioteca.getArchivioService().aggiornaBiblioteca(dati);
            }
        } catch (Exception e) {
            lblStatus.setText("❌ Errore caricamento JSON: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleVistaUtente() {
        tblPrestiti.setVisible(false);
        listPrestitiPerUtente.setVisible(true);
        caricaPrestitiPerUtente();
    }
    
    private void caricaPrestitiPerUtente() {
        List<UtenteConPrestiti> utenti = biblioteca.getPrestitiService().utentiConPrestiti();
        listaUtentiConPrestiti.clear();
        listaUtentiConPrestiti.addAll(utenti);
        
        int totPrestiti = utenti.stream().mapToInt(UtenteConPrestiti::getNumeroPrestiti).sum();
        lblStatus.setText("👥 " + utenti.size() + " utenti con " + totPrestiti + " prestiti attivi");
        aggiornaConteggioPrestiti();
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void setServizioArchivio(ServizioArchivio servizioArchivio) {
        this.archivio = servizioArchivio;
    }
    
    public void setArchivio(ServizioArchivio archivio) {
        this.archivio = archivio;
    }

    private void configuraCelleTabella() {
        colLibro.setCellValueFactory(cellData -> {
            String isbn = cellData.getValue().getIsbnLibro();
            List<Libro> libri = biblioteca.getLibriService().cercaPerIsbn(isbn);
            String titolo = libri.isEmpty() ? "ISBN: " + isbn : libri.get(0).getTitolo();
            return new SimpleStringProperty(titolo);
        });
        
        colUtente.setCellValueFactory(cellData -> {
            String matricola = cellData.getValue().getMatricolaUtente();
            List<Utente> utenti = biblioteca.getUtentiService().cercaPerMatricola(matricola);
            String nome = utenti.isEmpty() ? "Matr: " + matricola : utenti.get(0).getCognome() + " " + utenti.get(0).getNome();
            return new SimpleStringProperty(nome);
        });
        
        colDataRestituzione.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDataRestituzione().format(formatter))
        );
    }
    
    private void caricaPrestiti() {
        tblPrestiti.setVisible(true);
        listPrestitiPerUtente.setVisible(false);
        
        List<Prestito> prestiti = biblioteca.getPrestitiService().prestitiAttiviOrdinati();
        listaPrestiti.clear();
        listaPrestiti.addAll(prestiti);
        aggiornaConteggioPrestiti();
        lblStatus.setText("Caricati " + prestiti.size() + " prestiti attivi");
    }
    
    @FXML
    private void handleFiltraTutti() {
        caricaPrestiti();
    }
    
    @FXML
    private void handleFiltraAttivi() {
        tblPrestiti.setVisible(true);
        listPrestitiPerUtente.setVisible(false);
        
        List<Prestito> prestiti = biblioteca.getPrestitiService().prestitiAttiviOrdinati();
        listaPrestiti.clear();
        listaPrestiti.addAll(prestiti);
        lblStatus.setText("Filtro: Solo attivi (" + prestiti.size() + ")");
        aggiornaConteggioPrestiti();
    }
    
    @FXML
    private void handleFiltraRitardo() {
        tblPrestiti.setVisible(true);
        listPrestitiPerUtente.setVisible(false);
        
        List<Prestito> prestiti = biblioteca.getPrestitiService().prestitiInRitardo();
        listaPrestiti.clear();
        listaPrestiti.addAll(prestiti);
        lblStatus.setText("Filtro: In ritardo (" + prestiti.size() + ")");
        aggiornaConteggioPrestiti();
    }
    
    // ✅ RIPRISTINATA: Formattazione ComboBox Libri
    private void caricaComboLibri() {
        List<Libro> libri = biblioteca.getLibriService().listaOrdinataTitolo();
        cmbLibri.setItems(FXCollections.observableArrayList(libri));
        
        // Formattazione ComboBox
        cmbLibri.setCellFactory(lv -> new ListCell<Libro>() {
            @Override
            protected void updateItem(Libro libro, boolean empty) {
                super.updateItem(libro, empty);
                setText(empty || libro == null ? null : libro.getTitolo() + " - " + libro.getAutore());
            }
        });
        cmbLibri.setButtonCell(new ListCell<Libro>() {
            @Override
            protected void updateItem(Libro libro, boolean empty) {
                super.updateItem(libro, empty);
                setText(empty || libro == null ? null : libro.getTitolo());
            }
        });
    }
    
    // ✅ RIPRISTINATA: Formattazione ComboBox Utenti
    private void caricaComboUtenti() {
        List<Utente> utenti = biblioteca.getUtentiService().listaOrdinata().stream()
            .filter(Utente::isAttivo)
            .collect(Collectors.toList());
        cmbUtenti.setItems(FXCollections.observableArrayList(utenti));
        
        // Formattazione ComboBox
        cmbUtenti.setCellFactory(lv -> new ListCell<Utente>() {
            @Override
            protected void updateItem(Utente utente, boolean empty) {
                super.updateItem(utente, empty);
                setText(empty || utente == null ? null : 
                    utente.getNome() + " " + utente.getCognome() + " (" + utente.getMatricola() + ")");
            }
        });
        cmbUtenti.setButtonCell(new ListCell<Utente>() {
            @Override
            protected void updateItem(Utente utente, boolean empty) {
                super.updateItem(utente, empty);
                setText(empty || utente == null ? null : utente.getNome() + " " + utente.getCognome());
            }
        });
    }
    
    private void aggiornaCopieDisponibili(Libro libro) {
        // Conta prestiti attivi per questo libro
        int prestitiAttivi = (int) biblioteca.getPrestitiService().prestitiAttiviOrdinati().stream()
            .filter(p -> p.getIsbnLibro().equals(libro.getIsbn()))
            .count();
        int copieDisp = libro.getNumCopie() - prestitiAttivi;
        
        lblCopieDisponibili.setText("Copie disponibili: " + copieDisp + "/" + libro.getNumCopie());
        lblCopieDisponibili.setStyle(copieDisp > 0 ? 
            "-fx-text-fill: #27ae60; -fx-font-weight: bold;" : 
            "-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
    }
    
    private void aggiornaPrestitiUtente(Utente utente) {
        int prestitiAttivi = (int) biblioteca.getPrestitiService().prestitiAttiviOrdinati().stream()
            .filter(p -> p.getMatricolaUtente().equals(utente.getMatricola()))
            .count();
        
        lblPrestitiUtente.setText("Prestiti attivi utente: " + prestitiAttivi + "/3");
        lblPrestitiUtente.setStyle(prestitiAttivi < 3 ? 
            "-fx-text-fill: #27ae60; -fx-font-weight: bold;" : 
            "-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
    }
    
    private void aggiornaInfoPrestito() {
        LocalDate oggi = LocalDate.now();
        lblDataPrestito.setText("Data prestito: " + oggi.format(formatter));
        
        int giorni = getDurataSelezionata();
        LocalDate scadenza = oggi.plusDays(giorni);
        lblDataScadenza.setText("Data scadenza: " + scadenza.format(formatter));
    }
    
    private int getDurataSelezionata() {
        try {
            int giorni = Integer.parseInt(txtDurataPrestito.getText().trim());
            if (giorni > 0 && giorni <= 365) {
                return giorni;
            }
        } catch (NumberFormatException e) {
            // Se non è un numero valido, usa default
        }
        return 14; // default
    }
    
    private void aggiornaConteggioPrestiti() {
        int totali = biblioteca.getPrestitiService().prestitiAttiviOrdinati().size();
        int ritardo = biblioteca.getPrestitiService().prestitiInRitardo().size();
        lblPrestitiCount.setText("Prestiti: " + totali + " (Ritardo: " + ritardo + ")");
    }
    
    @FXML
    private void handleNuovoPrestito() {
        Libro libro = cmbLibri.getValue();
        Utente utente = cmbUtenti.getValue();
        
        if (libro == null || utente == null) {
            mostraErrore("Seleziona libro e utente!");
            return;
        }
        
        int giorni = getDurataSelezionata();
        LocalDate dataRestituzione = LocalDate.now().plusDays(giorni);

        try {
            boolean successo = biblioteca.getPrestitiService().registraPrestito(
                utente.getMatricola(),
                libro.getIsbn(),
                dataRestituzione
            );
            
            if (successo) {
                lblStatus.setText("✅ Prestito registrato con successo!");
                
                if (listPrestitiPerUtente.isVisible()) {
                    caricaPrestitiPerUtente();
                } else {
                    caricaPrestiti();
                }
                
                caricaComboLibri();
                pulisciForm();
                salvaJSON();
                
                if (mainController != null) mainController.aggiornaStatisticheDashboard();
            } else {
                mostraErrore("Impossibile registrare il prestito. Verifica:\n- Copie disponibili\n- Utente non ha già 3 prestiti attivi");
            }
            
        } catch (Exception e) {
            mostraErrore("Errore: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleRestituzione() {
        Prestito prestito = tblPrestiti.getSelectionModel().getSelectedItem();
        if (prestito == null) {
            lblStatus.setText("⚠️ Seleziona un prestito dalla tabella");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Restituzione");
        alert.setHeaderText("Registrare la restituzione del libro?");
        
        List<Libro> libri = biblioteca.getLibriService().cercaPerIsbn(prestito.getIsbnLibro());
        List<Utente> utenti = biblioteca.getUtentiService().cercaPerMatricola(prestito.getMatricolaUtente());
        
        String titoloLibro = libri.isEmpty() ? "ISBN: " + prestito.getIsbnLibro() : libri.get(0).getTitolo();
        String nomeUtente = utenti.isEmpty() ? "Matr: " + prestito.getMatricolaUtente() : 
            utenti.get(0).getNome() + " " + utenti.get(0).getCognome();
        
        alert.setContentText("Libro: " + titoloLibro + "\nUtente: " + nomeUtente);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean successo = biblioteca.getPrestitiService().registraRestituzione(prestito);
            
            if (successo) {
                lblStatus.setText("✅ Restituzione registrata!");
                
                if (listPrestitiPerUtente.isVisible()) {
                    caricaPrestitiPerUtente();
                } else {
                    caricaPrestiti();
                }
                
                caricaComboLibri();
                salvaJSON();
                
                if (mainController != null) mainController.aggiornaStatisticheDashboard();
            } else {
                mostraErrore("Errore durante la restituzione");
            }
        }
    }
    
    @FXML
    private void handlePulisci() {
        pulisciForm();
    }
    
    private void pulisciForm() {
        cmbLibri.setValue(null);
        cmbUtenti.setValue(null);
        txtDurataPrestito.setText("14");
        lblCopieDisponibili.setText("Copie disponibili: -");
        lblPrestitiUtente.setText("Prestiti attivi utente: -");
        lblDataScadenza.setText("Data scadenza: -");
        aggiornaInfoPrestito();
    }
    
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
        lblStatus.setText("❌ " + messaggio);
    }
    
    private void salvaJSON() {
        try {
            File dir = new File("biblioteca");
            if (!dir.exists()) dir.mkdirs();
            biblioteca.getArchivioService().salva("biblioteca/biblioteca.json");
        } catch (Exception e) {
            lblStatus.setText("❌ Errore salvataggio JSON: " + e.getMessage());
        }
    }
}