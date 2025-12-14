package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public class UtentiController {
    
    @FXML private TableView<Utente> tblUtenti;
    @FXML private TextField txtCerca;
    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private TextField txtMatricola;
    @FXML private TextField txtEmail;
    @FXML private CheckBox chkAttivo;
    @FXML private Label lblFormTitle;
    @FXML private Label lblStatus;
    @FXML private Button btnModifica;
    @FXML private Button btnDisattiva;
    @FXML private Button btnMostraDisattivati;  // Rinominato da btnMostraTutti
    
    private Biblioteca biblioteca;
    private MainController mainController;
    private ObservableList<Utente> listaUtenti;
    private Utente utenteSelezionato;
    private boolean modalitaModifica = false;
    private boolean mostraDisattivati = false;  // Flag per sapere quale lista mostrare
    
    @FXML
    private void initialize() {
        listaUtenti = FXCollections.observableArrayList();
        tblUtenti.setItems(listaUtenti);
        
        // Listener selezione tabella
        tblUtenti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    btnModifica.setDisable(false);
                    btnDisattiva.setDisable(false);
                    // Aggiorna il testo del bottone in base allo stato dell'utente
                    aggiornaTestoBottoneDisattiva(newSelection);
                } else {
                    btnModifica.setDisable(true);
                    btnDisattiva.setDisable(true);
                }
            }
        );
        
        btnModifica.setDisable(true);
        btnDisattiva.setDisable(true);
    }
    
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        caricaUtentiAttivi();  // Di default mostra solo attivi
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Carica solo gli utenti ATTIVI
     */
    private void caricaUtentiAttivi() {
        List<Utente> tuttiUtenti = biblioteca.getUtentiService().listaOrdinata();
        List<Utente> utentiAttivi = tuttiUtenti.stream()
                .filter(Utente::isAttivo)
                .collect(Collectors.toList());
        
        listaUtenti.clear();
        listaUtenti.addAll(utentiAttivi);
        mostraDisattivati = false;
        btnMostraDisattivati.setText("📋 Mostra Disattivati");
        lblStatus.setText("Caricati " + utentiAttivi.size() + " utenti attivi");
    }
    
    /**
     * Carica solo gli utenti DISATTIVATI
     */
    private void caricaUtentiDisattivati() {
        List<Utente> tuttiUtenti = biblioteca.getUtentiService().listaOrdinata();
        List<Utente> utentiDisattivati = tuttiUtenti.stream()
                .filter(u -> !u.isAttivo())
                .collect(Collectors.toList());
        
        listaUtenti.clear();
        listaUtenti.addAll(utentiDisattivati);
        mostraDisattivati = true;
        btnMostraDisattivati.setText("✅ Mostra Attivi");
        lblStatus.setText("Caricati " + utentiDisattivati.size() + " utenti disattivati");
    }
    
    @FXML
    private void handleCerca() {
        String keyword = txtCerca.getText().trim();
        if (keyword.isEmpty()) {
            lblStatus.setText("Inserisci un termine di ricerca");
            return;
        }
        
        listaUtenti.clear();
        
        // Cerca per cognome
        List<Utente> risultatiCognome = biblioteca.getUtentiService().cercaPerCognome(keyword);
        
        // Cerca per matricola
        List<Utente> risultatiMatricola = biblioteca.getUtentiService().cercaPerMatricola(keyword);
        
        // Combina i risultati
        for (Utente u : risultatiCognome) {
            if (!listaUtenti.contains(u)) {
                listaUtenti.add(u);
            }
        }
        for (Utente u : risultatiMatricola) {
            if (!listaUtenti.contains(u)) {
                listaUtenti.add(u);
            }
        }
        
        // Filtra in base allo stato di visualizzazione corrente
        if (!mostraDisattivati) {
            // Se stiamo mostrando attivi, filtra solo attivi
            listaUtenti.removeIf(u -> !u.isAttivo());
        } else {
            // Se stiamo mostrando disattivati, filtra solo disattivati
            listaUtenti.removeIf(Utente::isAttivo);
        }
        
        lblStatus.setText("Trovati " + listaUtenti.size() + " risultati per: " + keyword);
    }
    
    /**
     * Toggle tra mostra attivi e mostra disattivati
     */
    @FXML
    private void handleMostraDisattivati() {
        txtCerca.clear();
        if (mostraDisattivati) {
            caricaUtentiAttivi();
        } else {
            caricaUtentiDisattivati();
        }
    }
    
    @FXML
    private void handleSalva() {
        // Validazione
        if (!validaCampi()) {
            return;
        }
        
        try {
            String nome = txtNome.getText().trim();
            String cognome = txtCognome.getText().trim();
            String matricola = txtMatricola.getText().trim();
            String email = txtEmail.getText().trim();
            boolean attivo = chkAttivo.isSelected();
            
            if (modalitaModifica && utenteSelezionato != null) {
                // Modifica utente esistente
                utenteSelezionato.setNome(nome);
                utenteSelezionato.setCognome(cognome);
                utenteSelezionato.setMatricola(matricola);
                utenteSelezionato.setEmail(email);
                utenteSelezionato.setAttivo(attivo);
                
                biblioteca.getUtentiService().modifica(utenteSelezionato);
                lblStatus.setText("✅ Utente modificato con successo!");
                
            } else {
                // Controlla se la matricola esiste già
                List<Utente> esistenti = biblioteca.getUtentiService().cercaPerMatricola(matricola);
                if (esistenti != null && !esistenti.isEmpty()) {
                    mostraErrore("Matricola già esistente! Ogni utente deve avere una matricola unica.");
                    return;
                }

                // Aggiungi nuovo utente
                Utente nuovoUtente = new Utente(nome, cognome, matricola, email);
                nuovoUtente.setAttivo(attivo);
                biblioteca.getUtentiService().aggiungi(nuovoUtente);
                lblStatus.setText("✅ Utente aggiunto con successo!");
            }

            // Ricarica la lista appropriata
            if (mostraDisattivati) {
                caricaUtentiDisattivati();
            } else {
                caricaUtentiAttivi();
            }
            
            pulisciForm();
            
            // Aggiorna statistiche nel main
            if (mainController != null) {
                mainController.aggiornaStatisticheDashboard();
            }
            
        } catch (Exception e) {
            mostraErrore("Errore durante il salvataggio: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleModifica() {
        utenteSelezionato = tblUtenti.getSelectionModel().getSelectedItem();
        if (utenteSelezionato == null) {
            lblStatus.setText("⚠️ Seleziona un utente da modificare");
            return;
        }
        
        // Popola il form con i dati dell'utente selezionato
        txtNome.setText(utenteSelezionato.getNome());
        txtCognome.setText(utenteSelezionato.getCognome());
        txtMatricola.setText(utenteSelezionato.getMatricola());
        txtEmail.setText(utenteSelezionato.getEmail());
        chkAttivo.setSelected(utenteSelezionato.isAttivo());
        
        lblFormTitle.setText("✏️ Modifica Utente");
        modalitaModifica = true;
        lblStatus.setText("Modalità modifica attiva");
    }
    
    /**
     * Disattiva o riattiva un utente (non lo elimina mai!)
     */
    @FXML
    private void handleDisattiva() {
        Utente utente = tblUtenti.getSelectionModel().getSelectedItem();
        if (utente == null) {
            lblStatus.setText("⚠️ Seleziona un utente");
            return;
        }
        
        String azione = utente.isAttivo() ? "disattivare" : "riattivare";
        String azionePassata = utente.isAttivo() ? "disattivato" : "riattivato";
        
        // Conferma azione
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma " + azione.substring(0, 1).toUpperCase() + azione.substring(1));
        alert.setHeaderText(azione.substring(0, 1).toUpperCase() + azione.substring(1) + " l'utente?");
        alert.setContentText("Sei sicuro di voler " + azione + " " + 
                           utente.getNome() + " " + utente.getCognome() + "?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Inverti lo stato
                utente.setAttivo(!utente.isAttivo());
                biblioteca.getUtentiService().modifica(utente);
                
                // Ricarica la lista appropriata
                if (mostraDisattivati) {
                    caricaUtentiDisattivati();
                } else {
                    caricaUtentiAttivi();
                }
                
                lblStatus.setText("✅ Utente " + azionePassata + "!");
                
                if (mainController != null) {
                    mainController.aggiornaStatisticheDashboard();
                }
            }
        });
    }
    
    /**
     * Aggiorna il testo del bottone Disattiva/Riattiva in base allo stato dell'utente
     */
    private void aggiornaTestoBottoneDisattiva(Utente utente) {
        if (utente != null) {
            if (utente.isAttivo()) {
                btnDisattiva.setText("🔒 Disattiva");
                btnDisattiva.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            } else {
                btnDisattiva.setText("✅ Riattiva");
                btnDisattiva.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            }
        }
    }
    
    @FXML
    private void handlePulisci() {
        pulisciForm();
    }
    
    private void pulisciForm() {
        txtNome.clear();
        txtCognome.clear();
        txtMatricola.clear();
        txtEmail.clear();
        chkAttivo.setSelected(true);
        lblFormTitle.setText("➕ Aggiungi Nuovo Utente");
        modalitaModifica = false;
        utenteSelezionato = null;
        tblUtenti.getSelectionModel().clearSelection();
    }
    
    private boolean validaCampi() {
        if (txtNome.getText().trim().isEmpty()) {
            mostraErrore("Il nome è obbligatorio!");
            return false;
        }
        if (txtCognome.getText().trim().isEmpty()) {
            mostraErrore("Il cognome è obbligatorio!");
            return false;
        }
        if (txtMatricola.getText().trim().isEmpty()) {
            mostraErrore("La matricola è obbligatoria!");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            mostraErrore("L'email è obbligatoria!");
            return false;
        }
        if (!txtEmail.getText().contains("@")) {
            mostraErrore("Email non valida!");
            return false;
        }
        return true;
    }
    
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
        lblStatus.setText("❌ " + messaggio);
    }
}