package it.unisa.biblioteca.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica il MainView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
            // Crea la scena
            Scene scene = new Scene(root);
            
            // Configura e mostra la finestra principale
            primaryStage.setTitle("Sistema Gestione Biblioteca");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
            
            System.out.println("✅ Applicazione avviata con successo!");
            
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'avvio dell'applicazione:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
