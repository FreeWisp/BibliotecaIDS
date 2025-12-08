package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @FXML private Button btnElimina;
    @FXML private Button btnToggleAttivo;
    
    private Biblioteca biblioteca;
    private MainController mainController;
    private ObservableList<Utente> listaUtenti;
    private Utente utenteSelezionato;
    private boolean modalitaModifica = false;
    
    @FXML
    private void initialize() {
        listaUtenti = FXCollections.observableArrayList();
        tblUtenti.setItems(listaUtenti);
        
        // Listener selezione tabella
        tblUtenti.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    btnModifica.setDisable(false);
                    btnElimina.setDisable(false);
                    btnToggleAttivo.setDisable(false);
                } else {
                    btnModifica.setDisable(true);
                    btnElimina.setDisable(true);
                    btnToggleAttivo.setDisable(true);
                }
            }
        );
        
        btnModifica.setDisable(true);
        btnElimina.setDisable(true);
        btnToggleAttivo.setDisable(true);
    }
    
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        caricaUtenti();
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    private void caricaUtenti() {
        List<Utente> utenti = biblioteca.getUtentiService().listaOrdinata();
        listaUtenti.clear();
        listaUtenti.addAll(utenti);
        lblStatus.setText("Caricati " + utenti.size() + " utenti");
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
        listaUtenti.addAll(risultatiCognome);
        
        // Cerca per matricola
        List<Utente> risultatiMatricola = biblioteca.getUtentiService().cercaPerMatricola(keyword);
        for (Utente u : risultatiMatricola) {
            if (!listaUtenti.contains(u)) {
                listaUtenti.add(u);
            }
        }
        
        lblStatus.setText("Trovati " + listaUtenti.size() + " risultati per: " + keyword);
    }
    
    @FXML
    private void handleMostraTutti() {
        txtCerca.clear();
        caricaUtenti();
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
                utenteSelezionato.setEmail(email);  // CORRETTO: usa setEmail
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

            caricaUtenti();
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
        txtEmail.setText(utenteSelezionato.getEmail());  // CORRETTO: usa getEmail
        chkAttivo.setSelected(utenteSelezionato.isAttivo());
        
        lblFormTitle.setText("✏️ Modifica Utente");
        modalitaModifica = true;
        lblStatus.setText("Modalità modifica attiva");
    }
    
    @FXML
    private void handleElimina() {
        Utente utente = tblUtenti.getSelectionModel().getSelectedItem();
        if (utente == null) {
            lblStatus.setText("⚠️ Seleziona un utente da eliminare");
            return;
        }
        
        // Conferma eliminazione
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Eliminazione");
        alert.setHeaderText("Eliminare l'utente?");
        alert.setContentText("Sei sicuro di voler eliminare " + utente.getNome() + " " + utente.getCognome() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            biblioteca.getUtentiService().elimina(utente.getMatricola());
            caricaUtenti();
            lblStatus.setText("✅ Utente eliminato!");
            
            if (mainController != null) {
                mainController.aggiornaStatisticheDashboard();
            }
        }
    }
    
    @FXML
    private void handleToggleAttivo() {
        Utente utente = tblUtenti.getSelectionModel().getSelectedItem();
        if (utente == null) {
            lblStatus.setText("⚠️ Seleziona un utente");
            return;
        }
        
        utente.setAttivo(!utente.isAttivo());
        biblioteca.getUtentiService().modifica(utente);
        tblUtenti.refresh();
        
        String stato = utente.isAttivo() ? "attivato" : "disattivato";
        lblStatus.setText("✅ Utente " + stato + "!");
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
