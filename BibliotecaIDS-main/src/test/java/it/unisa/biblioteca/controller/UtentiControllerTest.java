package it.unisa.biblioteca.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtentiControllerTest {

    /**
     * Test of setMainController method, of class UtentiController.
     */
    @Test
    public void testSetMainController() {
        UtentiController controller = new UtentiController();

        // Setter semplice: non deve lanciare eccezioni
        assertDoesNotThrow(() -> controller.setMainController(null));
    }
}
