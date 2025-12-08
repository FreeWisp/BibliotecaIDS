package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
    
    @FXML private Label lblTotaleLibri;
    @FXML private Label lblTotaleUtenti;
    @FXML private Label lblPrestitiAttivi;
    @FXML private Label lblStatus;
    
    private Biblioteca biblioteca;
    
    @FXML
    private void initialize() {
        // Inizializza la biblioteca
        biblioteca = new Biblioteca();
        
        // Aggiorna le statistiche
        aggiornaStatistiche();
        
        lblStatus.setText("Sistema inizializzato - Benvenuto!");
    }
    
    private void aggiornaStatistiche() {
        lblTotaleLibri.setText(String.valueOf(
            biblioteca.getLibriService().listaOrdinataTitolo().size()
        ));
        lblTotaleUtenti.setText(String.valueOf(
            biblioteca.getUtentiService().listaOrdinata().size()
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
            
            // Passa la biblioteca al controller
            LibriController controller = loader.getController();
            controller.setBiblioteca(biblioteca);
            controller.setMainController(this);
            
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
            
            UtentiController controller = loader.getController();
            controller.setBiblioteca(biblioteca);
            controller.setMainController(this);
            
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
            
            PrestitiController controller = loader.getController();
            controller.setBiblioteca(biblioteca);
            controller.setMainController(this);
            
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
    
    // Metodo pubblico per aggiornare le statistiche da altri controller
    public void aggiornaStatisticheDashboard() {
        aggiornaStatistiche();
    }
}
