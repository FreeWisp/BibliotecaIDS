package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibriControllerTest {

    @Test
    public void testSetMainController() {
        LibriController controller = new LibriController();
        assertDoesNotThrow(() -> controller.setMainController(null));
    }

    @Test
    public void testSetServizioArchivio() {
        Biblioteca biblioteca = new Biblioteca();
        ServizioArchivio servizioArchivio = new ServizioArchivio(biblioteca);
        LibriController controller = new LibriController();

        assertDoesNotThrow(() -> controller.setServizioArchivio(servizioArchivio));
    }
}