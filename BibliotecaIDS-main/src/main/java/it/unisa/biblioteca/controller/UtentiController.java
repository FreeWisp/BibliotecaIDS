package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import it.unisa.biblioteca.servizi.ServizioArchivio;

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
    private String matricolaOriginale;
    private boolean modalitaModifica = false;
    private boolean mostraDisattivati = false;  // Flag per sapere quale lista mostrare
    private ServizioArchivio servizioArchivio;
    
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
        caricaDatiJSON();
        caricaUtentiAttivi();
        // Di default mostra solo attivi
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     *
     * @param servizioArchivio
     */
    public void setServizioArchivio(ServizioArchivio servizioArchivio) {
        this.servizioArchivio = servizioArchivio;
    }
    
    /**
 * Carica i dati degli utenti da JSON all'avvio
 */
    private void caricaDatiJSON() {
        try {
            File file = new File("biblioteca/biblioteca.json");
            if (file.exists()) {
                 ServizioArchivio.ArchivioData dati = biblioteca.getArchivioService().carica("biblioteca/utenti.json");
                 biblioteca.getArchivioService().aggiornaBiblioteca(dati);
            }
         } catch (Exception e) {
            lblStatus.setText("❌ Errore caricamento JSON utenti: " + e.getMessage());
         }
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

        // Ottieni tutti gli utenti
        List<Utente> tuttiUtenti = biblioteca.getUtentiService().listaOrdinata();

        // Filtra per cognome, nome o matricola (case-insensitive)
        for (Utente u : tuttiUtenti) {
            boolean match = false;

            // Cerca nel cognome
            if (u.getCognome() != null && 
                u.getCognome().toLowerCase().contains(keyword.toLowerCase())) {
                match = true;
            }

            // Cerca nel nome
            if (u.getNome() != null && 
                u.getNome().toLowerCase().contains(keyword.toLowerCase())) {
                match = true;
            }

            // Cerca nella matricola
            if (u.getMatricola() != null && 
                u.getMatricola().contains(keyword)) {
                match = true;
            }

            // Aggiungi se c'è un match e non è già nella lista
            if (match && !listaUtenti.contains(u)) {
                listaUtenti.add(u);
            }
        }
    
    // Filtra in base allo stato di visualizzazione corrente
    if (!mostraDisattivati) {
        listaUtenti.removeIf(u -> !u.isAttivo());
    } else {
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
        if (!validaCampi()) return;

        try {
            String nome = txtNome.getText().trim();
            String cognome = txtCognome.getText().trim();
            String matricola = txtMatricola.getText().trim();
            String email = txtEmail.getText().trim();
            boolean attivo = chkAttivo.isSelected();

            if (modalitaModifica && utenteSelezionato != null) {
                // ✅ PRIMA: Elimina l'utente con la MATRICOLA ORIGINALE se cambiata
                if (!matricolaOriginale.equals(matricola)) {
                    // La matricola è cambiata, elimina il vecchio
                    biblioteca.getUtentiService().elimina(matricolaOriginale);
                }

                // POI: Aggiorna/Inserisci con la nuova matricola
                utenteSelezionato.setNome(nome);
                utenteSelezionato.setCognome(cognome);
                utenteSelezionato.setMatricola(matricola);
                utenteSelezionato.setEmail(email);
                utenteSelezionato.setAttivo(attivo);

                if (matricolaOriginale.equals(matricola)) {
                    // Matricola non cambiata, usa modifica normale
                    biblioteca.getUtentiService().modifica(utenteSelezionato);
                } else {
                    // Matricola cambiata, inserisci come nuovo
                    biblioteca.getUtentiService().aggiungi(utenteSelezionato);
                }

                lblStatus.setText("✅ Utente modificato con successo!");
            } else {
                // Inserimento nuovo utente
                Utente nuovoUtente = new Utente(nome, cognome, matricola, email);
                nuovoUtente.setAttivo(attivo);
                biblioteca.getUtentiService().aggiungi(nuovoUtente);
                lblStatus.setText("✅ Utente aggiunto con successo!");
            }

            pulisciForm();
            if (mostraDisattivati) {
                caricaUtentiDisattivati();
            } else {
                caricaUtentiAttivi();
            }
            
            // Salvataggio automatico su JSON
            salvaJSON();


            if (mainController != null) mainController.aggiornaStatisticheDashboard();

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
        
        matricolaOriginale = utenteSelezionato.getMatricola();

        // Carica i dati nel form...
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
                
                // Salvataggio automatico su JSON
                salvaJSON();
                
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
        matricolaOriginale = null;
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
    
    /**
    * Salva i dati degli utenti su JSON
    */
    private void salvaJSON() {
        try {
            File dir = new File("biblioteca");
             if (!dir.exists()) dir.mkdirs();
             biblioteca.getArchivioService().salva("biblioteca/biblioteca.json");
         } catch (Exception e) {
            lblStatus.setText("❌ Errore salvataggio JSON utenti: " + e.getMessage());
         }
    }
    
}