package it.unisa.biblioteca.app;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Biblioteca biblioteca;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Crea l'istanza della biblioteca (inizializza repository e servizi)
            biblioteca = new Biblioteca();

            // Carica il MainView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            // Passa la biblioteca al controller
            it.unisa.biblioteca.controller.MainController controller = loader.getController();
            controller.setBiblioteca(biblioteca);

            // Crea la scena
            Scene scene = new Scene(root);

            // Configura e mostra la finestra principale
            primaryStage.setTitle("Sistema Gestione Biblioteca");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

            System.out.println("✅ Applicazione avviata con successo!");

            // **Salvataggio iniziale su file (facoltativo)**
            ServizioArchivio archivio = biblioteca.getArchivioService();
            archivio.salva("biblioteca.json");  // crea il file se non esiste

        } catch (Exception e) {
            System.err.println("❌ Errore durante l'avvio dell'applicazione:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

