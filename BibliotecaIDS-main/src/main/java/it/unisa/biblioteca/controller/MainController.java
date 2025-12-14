package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import it.unisa.biblioteca.servizi.ServizioArchivio.ArchivioData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML private Label lblTotaleLibri;
    @FXML private Label lblTotaleUtenti;
    @FXML private Label lblPrestitiAttivi;
    @FXML private Label lblStatus;
    @FXML private Button btnSalva;
    @FXML private Button btnCarica;

    private Biblioteca biblioteca;
    private ServizioArchivio archivio;

    /**
     * Imposta la biblioteca e inizializza il servizio archivio
     */
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.archivio = biblioteca.getArchivioService();
        caricaDatiIniziali();
    }
    
    private void caricaDatiIniziali() {
    try {
        File file = new File("biblioteca/biblioteca.json");
        if (file.exists()) {
            ArchivioData dati = archivio.carica("biblioteca/biblioteca.json");
            if (dati != null) {
                archivio.aggiornaBiblioteca(dati);
                aggiornaStatistiche(); // ✅ Aggiorna SUBITO le statistiche!
                lblStatus.setText("✅ Dati caricati: " + 
                    dati.libri.size() + " libri, " + 
                    dati.utenti.size() + " utenti, " + 
                    dati.prestiti.size() + " prestiti");
                return;
            }
        }
        
        // Se il file non esiste, mostra 0
        aggiornaStatistiche();
        lblStatus.setText("⚠️ Nessun dato - Inizia aggiungendo libri!");
        
    } catch (Exception e) {
        aggiornaStatistiche();
        lblStatus.setText("⚠️ Errore caricamento: " + e.getMessage());
    }
}

    @FXML
    private void initialize() {
        if (btnSalva != null)
            btnSalva.setOnAction(e -> salvaSuFile());
        if (btnCarica != null)
            btnCarica.setOnAction(e -> {
                caricaDaFile();
                aggiornaStatisticheDashboard();
            });

        lblStatus.setText("Sistema inizializzato - Benvenuto!");
    }
    
    

    private void aggiornaStatistiche() {
        lblTotaleLibri.setText(String.valueOf(
            biblioteca.getLibriService().listaOrdinataTitolo().size()
        ));
        lblTotaleUtenti.setText(String.valueOf(
            biblioteca.getUtentiService().listaOrdinata().stream()
                .filter(Utente::isAttivo) 
                .count()
        ));
        lblPrestitiAttivi.setText(String.valueOf(
            biblioteca.getPrestitiService().prestitiAttiviOrdinati().size()
        ));
    }

    @FXML
    private void handleLibri() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibriView.fxml"));
            Parent root = loader.load();

            var controller = loader.getController();
            controller.getClass().getMethod("setBiblioteca", Biblioteca.class).invoke(controller, biblioteca);
            controller.getClass().getMethod("setMainController", MainController.class).invoke(controller, this);

            Stage stage = new Stage();
            stage.setTitle("Gestione Libri");
            stage.setScene(new Scene(root));
            stage.show();

            lblStatus.setText("Aperta finestra Gestione Libri");
        } catch (Exception e) {
            lblStatus.setText("Errore apertura Gestione Libri: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUtenti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UtentiView.fxml"));
            Parent root = loader.load();

            var controller = loader.getController();
            controller.getClass().getMethod("setBiblioteca", Biblioteca.class).invoke(controller, biblioteca);
            controller.getClass().getMethod("setMainController", MainController.class).invoke(controller, this);

            Stage stage = new Stage();
            stage.setTitle("Gestione Utenti");
            stage.setScene(new Scene(root));
            stage.show();

            lblStatus.setText("Aperta finestra Gestione Utenti");
        } catch (Exception e) {
            lblStatus.setText("Errore apertura Gestione Utenti: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrestiti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PrestitiView.fxml"));
            Parent root = loader.load();

            var controller = loader.getController();
            controller.getClass().getMethod("setBiblioteca", Biblioteca.class).invoke(controller, biblioteca);
            controller.getClass().getMethod("setMainController", MainController.class).invoke(controller, this);

            Stage stage = new Stage();
            stage.setTitle("Gestione Prestiti");
            stage.setScene(new Scene(root));
            stage.show();

            lblStatus.setText("Aperta finestra Gestione Prestiti");
        } catch (Exception e) {
            lblStatus.setText("Errore apertura Gestione Prestiti: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void aggiornaStatisticheDashboard() {
        aggiornaStatistiche();
    }

    private void salvaSuFile() {
        try {
            archivio.salva("biblioteca.json");
            mostraMessaggio("✅ Dati salvati correttamente in biblioteca.json");
        } catch (Exception ex) {
            mostraErrore("Errore durante il salvataggio: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void caricaDaFile() {
        try {
            ArchivioData dati = archivio.carica("biblioteca.json");
            if (dati != null) {
                archivio.aggiornaBiblioteca(dati);
                aggiornaStatistiche();
                lblStatus.setText("✅ Dati caricati correttamente da biblioteca.json");
            }
        } catch (Exception ex) {
            mostraErrore("Errore durante il caricamento: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostraMessaggio(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
