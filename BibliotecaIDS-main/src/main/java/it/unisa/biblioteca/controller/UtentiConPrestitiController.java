/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package it.unisa.biblioteca.controller;

/**
 *
 * @author cuposabatino
 */

import it.unisa.biblioteca.model.UtenteConPrestiti;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @class UtenteConPrestitiCell
 * @brief Custom cell per visualizzare utenti con prestiti in formato compatto
 *
 * Crea una visualizzazione multi-riga per ogni utente, mostrando:
 * - Header con nome, matricola e numero prestiti
 * - Lista dei libri in prestito con scadenze
 * - Evidenziazione visiva per prestiti in ritardo e utenti al limite
 */
public class UtentiConPrestitiController extends ListCell<UtenteConPrestiti> {

    private VBox content;
    private Label headerLabel;
    private VBox prestitiBox;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public UtentiConPrestitiController() {
        super();

        // Container principale
        content = new VBox(5);
        content.setPadding(new Insets(10));

        // Header (nome utente + info)
        headerLabel = new Label();
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Box per i prestiti
        prestitiBox = new VBox(3);
        prestitiBox.setPadding(new Insets(5, 0, 0, 20));

        content.getChildren().addAll(headerLabel, prestitiBox);
    }

    @Override
    protected void updateItem(UtenteConPrestiti item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setStyle("");
        } else {

            /* ================= HEADER ================= */
            StringBuilder header = new StringBuilder();

            // Icona stato
            if (item.haPrestitiInRitardo()) {
                header.append("🔴 ");
            } else if (item.isAlLimite()) {
                header.append("⚠️ ");
            } else {
                header.append("📚 ");
            }

            // Nome e info
            header.append(item.getNomeCompleto())
                  .append(" (").append(item.getMatricola()).append(") - ")
                  .append(item.getNumeroPrestiti())
                  .append(item.getNumeroPrestiti() == 1 ? " prestito" : " prestiti");

            if (item.isAlLimite()) {
                header.append(" [LIMITE RAGGIUNTO]");
            }

            headerLabel.setText(header.toString());
            headerLabel.setStyle("-fx-text-fill: black;");

            /* ================= PRESTITI ================= */
            prestitiBox.getChildren().clear();

            for (UtenteConPrestiti.DettaglioPrestito p : item.getPrestiti()) {

                VBox prestitoBox = new VBox(2);

                // Titolo libro
                Label titoloLabel = new Label();
                String iconaPrestito = p.isInRitardo() ? "🔴" : "•";

                String testoTitolo = iconaPrestito + " " + p.getTitoloLibro();

                if (!p.getAutoreLibro().isEmpty()) {
                    testoTitolo += " (" + p.getAutoreLibro() + ")";
                }

                if (p.isInRitardo()) {
                    testoTitolo += " [IN RITARDO]";
                }

                titoloLabel.setText(testoTitolo);
                titoloLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
                
                
                 // Colore del testo: rosso se in ritardo, altrimenti NERO
                if (p.isInRitardo()) {
                    titoloLabel.setStyle("-fx-text-fill: #cc0000;");
                }else {
                    titoloLabel.setStyle("-fx-text-fill: black;");
                }

                // Scadenza
                Label scadenzaLabel = new Label(
                        " Scadenza: " + p.getScadenza().format(DATE_FORMATTER)
                );
                scadenzaLabel.setFont(Font.font("System", 11));
               // TESTO SCADENZA SEMPRE GRIGIO SCURO (leggibile)
                scadenzaLabel.setStyle("-fx-text-fill: #333333;");

                prestitoBox.getChildren().addAll(titoloLabel, scadenzaLabel);
                prestitiBox.getChildren().add(prestitoBox);
            }

            /* STILE */
            String backgroundColor = "#ffffff";

            if (item.haPrestitiInRitardo()) {
                backgroundColor = "#ffe6e6"; // rosso chiaro
            } else if (item.isAlLimite()) {
                backgroundColor = "#fff9e6"; // giallo chiaro
            }
            
             // Quando selezionato, sfondo diventa celeste
            if (isSelected()) {
                backgroundColor = "#d4e8f7"; // celeste - testo nero resta leggibile
            }

            content.setStyle(
                    "-fx-background-color: " + backgroundColor + ";" +
                    "-fx-border-color: #cccccc;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;"
            );

            setGraphic(content);
        }
    }
}
