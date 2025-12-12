package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.model.Utente;
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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Servizio archivio per salvataggi su file
    private ServizioArchivio archivio;
    
    @FXML
    private void initialize() {
        listaPrestiti = FXCollections.observableArrayList();
        tblPrestiti.setItems(listaPrestiti);
        
        // Configura le colonne per mostrare i dati corretti
        configuraCelleTabella();
        
        // Listener selezione tabella
        tblPrestiti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                // Un prestito è attivo se la data restituzione è nel futuro
                boolean isAttivo = newSelection != null && 
                    newSelection.getDataRestituzione() != null &&
                    newSelection.getDataRestituzione().isAfter(LocalDate.now());
                btnRestituzione.setDisable(!isAttivo);
            }
        );
        
        // Listener selezione libro
        cmbLibri.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    aggiornaCopieDisponibili(newSelection);
                    aggiornaInfoPrestito();
                }
            }
        );
        
        // Listener selezione utente
        cmbUtenti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    aggiornaPrestitiUtente(newSelection);
                    aggiornaInfoPrestito();
                }
            }
        );
        
        // Listener durata
        txtDurataPrestito.textProperty().addListener((obs, old, val) -> aggiornaInfoPrestito());
        
        btnRestituzione.setDisable(true);
    }
    
    public void setBiblioteca(Biblioteca biblioteca) {
      this.biblioteca = biblioteca;
      caricaDatiJSON(); // aggiungi questo metodo
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

    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Setter archivio (due nomi per compatibilità)
    public void setServizioArchivio(ServizioArchivio archivio) {
        this.archivio = archivio;
    }
    public void setArchivio(ServizioArchivio archivio) {
        this.archivio = archivio;
    }
    
    @SuppressWarnings("unchecked")
    private void configuraCelleTabella() {
        // Colonna Libro - trova il libro dall'ISBN
        TableColumn<Prestito, String> colLibro = (TableColumn<Prestito, String>) tblPrestiti.getColumns().get(0);
        colLibro.setCellValueFactory(cellData -> {
            Prestito prestito = cellData.getValue();
            List<Libro> libri = biblioteca.getLibriService().cercaPerIsbn(prestito.getIsbnLibro());
            String titolo = !libri.isEmpty() ? libri.get(0).getTitolo() : "Sconosciuto";
            return new SimpleStringProperty(titolo);
        });
        
        // Colonna Utente - trova l'utente dalla matricola
        TableColumn<Prestito, String> colUtente = (TableColumn<Prestito, String>) tblPrestiti.getColumns().get(1);
        colUtente.setCellValueFactory(cellData -> {
            Prestito prestito = cellData.getValue();
            List<Utente> utenti = biblioteca.getUtentiService().cercaPerMatricola(prestito.getMatricolaUtente());
            String nomeCompleto = !utenti.isEmpty() ? 
                utenti.get(0).getNome() + " " + utenti.get(0).getCognome() : "Sconosciuto";
            return new SimpleStringProperty(nomeCompleto);
        });
        
        // Colonna Data Restituzione
        TableColumn<Prestito, String> colData = (TableColumn<Prestito, String>) tblPrestiti.getColumns().get(4);
        colData.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getDataRestituzione();
            String dataStr = data != null ? data.format(formatter) : "-";
            return new SimpleStringProperty(dataStr);
        });
    }
    
    private void caricaPrestiti() {
        List<Prestito> prestiti = biblioteca.getPrestitiService().prestitiAttiviOrdinati();
        listaPrestiti.clear();
        listaPrestiti.addAll(prestiti);
        lblPrestitiCount.setText("Prestiti: " + prestiti.size());
        lblStatus.setText("Caricati " + prestiti.size() + " prestiti");
    }
    
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
    
    @FXML
    private void handleNuovoPrestito() {
        Libro libro = cmbLibri.getSelectionModel().getSelectedItem();
        Utente utente = cmbUtenti.getSelectionModel().getSelectedItem();

        if (libro == null) {
            mostraErrore("Seleziona un libro!");
            return;
        }
        if (utente == null) {
            mostraErrore("Seleziona un utente!");
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
                caricaPrestiti();
                handlePulisci();
                salvaJSON();

                if (mainController != null) mainController.aggiornaStatisticheDashboard();

                mostraInfo("Prestito registrato!",
                    "Il libro \"" + libro.getTitolo() + "\" è stato prestato a " +
                    utente.getNome() + " " + utente.getCognome());
            } else {
                mostraErrore("Impossibile registrare il prestito!\n" +
                    "Verifica che ci siano copie disponibili e che l'utente non abbia già 3 prestiti attivi.");
            }
        } catch (Exception e) {
            mostraErrore("Errore: " + e.getMessage());
        }
}
    
    @FXML
    private void handleRestituzione() {
        Prestito prestito = tblPrestiti.getSelectionModel().getSelectedItem();
        if (prestito == null) {
            lblStatus.setText("⚠️ Seleziona un prestito");
            return;
        }

        List<Libro> libri = biblioteca.getLibriService().cercaPerIsbn(prestito.getIsbnLibro());
        List<Utente> utenti = biblioteca.getUtentiService().cercaPerMatricola(prestito.getMatricolaUtente());

        if (libri.isEmpty() || utenti.isEmpty()) {
            mostraErrore("Errore: dati non trovati");
            return;
        }

        Libro libro = libri.get(0);
        Utente utente = utenti.get(0);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Restituzione");
        alert.setHeaderText("Registrare la restituzione?");
        alert.setContentText("Libro: " + libro.getTitolo() + "\n" +
                            "Utente: " + utente.getNome() + " " + utente.getCognome());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean successo = biblioteca.getPrestitiService().registraRestituzione(prestito);
            if (successo) {
                caricaPrestiti();
                lblStatus.setText("✅ Restituzione registrata!");
                salvaJSON();

                if (mainController != null) mainController.aggiornaStatisticheDashboard();
            } else {
                mostraErrore("Errore durante la restituzione");
            }
        }
}
    
    @FXML
    private void handleFiltraTutti() {
        caricaPrestiti();
    }
    
    @FXML
    private void handleFiltraAttivi() {
        List<Prestito> attivi = biblioteca.getPrestitiService().prestitiAttiviOrdinati();
        listaPrestiti.clear();
        listaPrestiti.addAll(attivi);
        lblPrestitiCount.setText("Prestiti attivi: " + attivi.size());
        lblStatus.setText("Filtro: solo prestiti attivi");
    }
    
    @FXML
    private void handleFiltraRitardo() {
        List<Prestito> inRitardo = biblioteca.getPrestitiService().prestitiInRitardo();
        listaPrestiti.clear();
        listaPrestiti.addAll(inRitardo);
        lblPrestitiCount.setText("Prestiti in ritardo: " + inRitardo.size());
        lblStatus.setText("Filtro: prestiti in ritardo");
    }
    
    @FXML
    private void handlePulisci() {
        cmbLibri.getSelectionModel().clearSelection();
        cmbUtenti.getSelectionModel().clearSelection();
        txtDurataPrestito.setText("14");
        lblCopieDisponibili.setText("Copie disponibili: -");
        lblPrestitiUtente.setText("Prestiti attivi utente: -");
        aggiornaInfoPrestito();
    }
    
    // Salvataggio silenzioso con gestione eccezione
    private void salvaArchivioSilenzioso() {
        if (archivio == null) return;
        try {
            archivio.salva("biblioteca.json");
        } catch (Exception e) {
            mostraErrore("Errore salvataggio archivio: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
        lblStatus.setText("❌ " + messaggio);
    }
    
    private void mostraInfo(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    
    // Metodo per salvare JSON
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
