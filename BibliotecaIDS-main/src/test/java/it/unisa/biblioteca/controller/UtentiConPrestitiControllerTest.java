/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.UtenteConPrestiti;
import javafx.application.Platform;
import javafx.scene.Node;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author cupos
 */
public class UtentiConPrestitiControllerTest {
    
   @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX già inizializzato
        }
    }

    @Test
    public void testUpdateItem_nonVuoto() {
        UtentiConPrestitiController cell = new UtentiConPrestitiController();

        UtenteConPrestiti item = new UtenteConPrestiti(
                "12345",
                "Mario Rossi"
        );

        assertDoesNotThrow(() -> cell.updateItem(item, false));
        assertNotNull(cell.getGraphic());
    }

    @Test
    public void testUpdateItem_vuoto() {
        UtentiConPrestitiController cell = new UtentiConPrestitiController();

        assertDoesNotThrow(() -> cell.updateItem(null, true));
        assertNull(cell.getGraphic());
    }
}