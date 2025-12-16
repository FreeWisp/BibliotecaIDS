package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.Optional;

public class LibriController {

    @FXML private  TableView<Libro> tblLibri;
    @FXML private TextField txtCerca;
    @FXML private TextField txtTitolo;
    @FXML private TextField txtAutore;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtAnno;
    @FXML private TextField txtCopie;
    @FXML private Label lblFormTitle;
    @FXML private  Label lblStatus;
    @FXML private  Button btnModifica;
    @FXML private Button btnElimina;

    private Biblioteca biblioteca;
    private MainController mainController;
    private ObservableList<Libro> listaLibri;
    private Libro libroSelezionato;
    private boolean modalitaModifica = false;
    private ServizioArchivio servizioArchivio;

    @FXML
     void initialize() {
        listaLibri = FXCollections.observableArrayList();
        tblLibri.setItems(listaLibri);

        tblLibri.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSel, newSel) -> {
                boolean sel = newSel != null;
                btnModifica.setDisable(!sel);
                btnElimina.setDisable(!sel);
            }
        );

        btnModifica.setDisable(true);
        btnElimina.setDisable(true);
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        caricaDatiJSON(); // Carica automaticamente all'avvio
        caricaLibri();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setServizioArchivio(ServizioArchivio servizioArchivio) {
        this.servizioArchivio = servizioArchivio;
    }

    private void caricaLibri() {
        List<Libro> libri = biblioteca.getLibriService().listaOrdinataTitolo();
        listaLibri.clear();
        listaLibri.addAll(libri);
        lblStatus.setText("Caricati " + libri.size() + " libri");
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
    private void handleCerca() {
        String keyword = txtCerca.getText().trim();
        if (keyword.isEmpty()) {
            lblStatus.setText("Inserisci un termine di ricerca");
            return;
        }

        listaLibri.clear();
        biblioteca.getLibriService().cercaPerTitolo(keyword).forEach(listaLibri::add);
        biblioteca.getLibriService().cercaPerAutore(keyword).forEach(l -> { if (!listaLibri.contains(l)) listaLibri.add(l); });
        biblioteca.getLibriService().cercaPerIsbn(keyword).forEach(l -> { if (!listaLibri.contains(l)) listaLibri.add(l); });

        lblStatus.setText("Trovati " + listaLibri.size() + " risultati per: " + keyword);
    }

    @FXML
    private void handleMostraTutti() {
        txtCerca.clear();
        caricaLibri();
    }

    @FXML
    private void handleSalva() {
        if (!validaCampi()) return;

        try {
            String titolo = txtTitolo.getText().trim();
            String autore = txtAutore.getText().trim();
            String isbn = txtIsbn.getText().trim();
            int anno = Integer.parseInt(txtAnno.getText().trim());
            int copie = Integer.parseInt(txtCopie.getText().trim());

            if (modalitaModifica && libroSelezionato != null) {
                libroSelezionato.setTitolo(titolo);
                libroSelezionato.setAutore(autore);
                libroSelezionato.setIsbn(isbn);
                libroSelezionato.setAnnoPubblicazione(anno);
                libroSelezionato.setNumCopie(copie);
                biblioteca.getLibriService().modifica(libroSelezionato);
                lblStatus.setText("✅ Libro modificato con successo!");
            } else {
                Libro nuovoLibro = new Libro(titolo, autore, isbn, anno, copie);
                biblioteca.getLibriService().aggiungi(nuovoLibro);
                lblStatus.setText("✅ Libro aggiunto con successo!");
            }

            caricaLibri();
            pulisciForm();
            salvaJSON();

            if (mainController != null) mainController.aggiornaStatisticheDashboard();

        } catch (NumberFormatException e) {
            mostraErrore("Anno e Numero Copie devono essere numeri validi!");
        } catch (Exception e) {
            mostraErrore("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    @FXML
    private void handleModifica() {
        libroSelezionato = tblLibri.getSelectionModel().getSelectedItem();
        if (libroSelezionato == null) { lblStatus.setText("⚠️ Seleziona un libro da modificare"); return; }

        txtTitolo.setText(libroSelezionato.getTitolo());
        txtAutore.setText(libroSelezionato.getAutore());
        txtIsbn.setText(libroSelezionato.getIsbn());
        txtAnno.setText(String.valueOf(libroSelezionato.getAnnoPubblicazione()));
        txtCopie.setText(String.valueOf(libroSelezionato.getNumCopie()));

        lblFormTitle.setText("✏️ Modifica Libro");
        modalitaModifica = true;
        lblStatus.setText("Modalità modifica attiva");
    }

    @FXML
    private void handleElimina() {
        Libro libro = tblLibri.getSelectionModel().getSelectedItem();
        if (libro == null) { lblStatus.setText("⚠️ Seleziona un libro da eliminare"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Eliminazione");
        alert.setHeaderText("Eliminare il libro?");
        alert.setContentText("Sei sicuro di voler eliminare \"" + libro.getTitolo() + "\"?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            biblioteca.getLibriService().elimina(libro.getIsbn());
            caricaLibri();
            lblStatus.setText("✅ Libro eliminato!");
            salvaJSON();

            if (mainController != null) mainController.aggiornaStatisticheDashboard();
        }
    }

    @FXML
    private void handlePulisci() { 
        pulisciForm();
    }

    private void pulisciForm() {
        txtTitolo.clear();
        txtAutore.clear();
        txtIsbn.clear();
        txtAnno.clear();
        txtCopie.clear();
        lblFormTitle.setText("➕ Aggiungi Nuovo Libro");
        modalitaModifica = false;
        libroSelezionato = null;
        tblLibri.getSelectionModel().clearSelection();
    }

    private boolean validaCampi() {
        if (txtTitolo.getText().trim().isEmpty()) { mostraErrore("Il titolo è obbligatorio!"); return false; }
        if (txtAutore.getText().trim().isEmpty()) { mostraErrore("L'autore è obbligatorio!"); return false; }
        if (txtIsbn.getText().trim().isEmpty()) { mostraErrore("L'ISBN è obbligatorio!"); return false; }
        if (txtAnno.getText().trim().isEmpty()) { mostraErrore("L'anno di pubblicazione è obbligatorio!"); return false; }
        if (txtCopie.getText().trim().isEmpty()) { mostraErrore("Il numero di copie è obbligatorio!"); return false; }
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

