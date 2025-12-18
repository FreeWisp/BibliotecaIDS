package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitiControllerTest {

    /**
     * Test of setMainController method, of class PrestitiController.
     */
    @Test
    public void testSetMainController() {
        PrestitiController controller = new PrestitiController();

        // Setter semplice: non deve lanciare eccezioni
        assertDoesNotThrow(() -> controller.setMainController(null));
    }

    /**
     * Test of setServizioArchivio method, of class PrestitiController.
     */
    @Test
    public void testSetServizioArchivio() {
        Biblioteca biblioteca = new Biblioteca();
        ServizioArchivio archivio = new ServizioArchivio(biblioteca);
        PrestitiController controller = new PrestitiController();

        assertDoesNotThrow(() -> controller.setServizioArchivio(archivio));
    }

    /**
     * Test of setArchivio method, of class PrestitiController.
     */
    @Test
    public void testSetArchivio() {
        Biblioteca biblioteca = new Biblioteca();
        ServizioArchivio archivio = new ServizioArchivio(biblioteca);
        PrestitiController controller = new PrestitiController();

        assertDoesNotThrow(() -> controller.setArchivio(archivio));
    }
}