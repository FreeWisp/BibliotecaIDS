/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.model;


import java.time.LocalDate;
import java.util.List;
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
public class UtenteConPrestitiTest {
    
    public UtenteConPrestitiTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

   /**
     * Test del costruttore e dei getter base
     */
    @Test
    public void testCostruttoreEGetter() {
        System.out.println("costruttore + getter");

        UtenteConPrestiti utente = new UtenteConPrestiti("12345", "Rossi Mario");

        assertEquals("12345", utente.getMatricola());
        assertEquals("Rossi Mario", utente.getNomeCompleto());
        assertEquals(0, utente.getNumeroPrestiti());
        assertFalse(utente.haPrestitiInRitardo());
        assertFalse(utente.isAlLimite());
        assertNotNull(utente.getPrestiti());
        assertTrue(utente.getPrestiti().isEmpty());
    }

    /**
     * Test aggiunta di un singolo prestito
     */
    @Test
    public void testAggiungiPrestito() {
        System.out.println("aggiungiPrestito");

        UtenteConPrestiti utente = new UtenteConPrestiti("12345", "Rossi Mario");

        LocalDate scadenza = LocalDate.now().plusDays(7);
        utente.aggiungiPrestito("Il Signore degli Anelli", "Tolkien", scadenza, false);

        assertEquals(1, utente.getNumeroPrestiti());
        assertFalse(utente.haPrestitiInRitardo());
        assertFalse(utente.isAlLimite());

        List<UtenteConPrestiti.DettaglioPrestito> prestiti = utente.getPrestiti();
        assertEquals(1, prestiti.size());

        UtenteConPrestiti.DettaglioPrestito p = prestiti.get(0);
        assertEquals("Il Signore degli Anelli", p.getTitoloLibro());
        assertEquals("Tolkien", p.getAutoreLibro());
        assertEquals(scadenza, p.getScadenza());
        assertFalse(p.isInRitardo());
    }

    /**
     * Test rilevamento prestito in ritardo
     */
    @Test
    public void testPrestitoInRitardo() {
        System.out.println("prestito in ritardo");

        UtenteConPrestiti utente = new UtenteConPrestiti("12345", "Rossi Mario");

        utente.aggiungiPrestito(
                "1984",
                "Orwell",
                LocalDate.now().minusDays(3),
                true
        );

        assertEquals(1, utente.getNumeroPrestiti());
        assertTrue(utente.haPrestitiInRitardo());
        assertFalse(utente.isAlLimite());
    }

    /**
     * Test raggiungimento limite prestiti (3)
     */
    @Test
    public void testLimitePrestiti() {
        System.out.println("limite prestiti");

        UtenteConPrestiti utente = new UtenteConPrestiti("12345", "Rossi Mario");

        utente.aggiungiPrestito("Libro 1", "Autore 1", LocalDate.now().plusDays(10), false);
        utente.aggiungiPrestito("Libro 2", "Autore 2", LocalDate.now().plusDays(10), false);
        utente.aggiungiPrestito("Libro 3", "Autore 3", LocalDate.now().plusDays(10), false);

        assertEquals(3, utente.getNumeroPrestiti());
        assertTrue(utente.isAlLimite());
        assertFalse(utente.haPrestitiInRitardo());
    }

    /**
     * Test combinato: limite + prestito in ritardo
     */
    @Test
    public void testLimiteConRitardo() {
        System.out.println("limite + ritardo");

        UtenteConPrestiti utente = new UtenteConPrestiti("12345", "Rossi Mario");

        utente.aggiungiPrestito("Libro 1", "Autore 1", LocalDate.now().plusDays(5), false);
        utente.aggiungiPrestito("Libro 2", "Autore 2", LocalDate.now().minusDays(1), true);
        utente.aggiungiPrestito("Libro 3", "Autore 3", LocalDate.now().plusDays(7), false);

        assertEquals(3, utente.getNumeroPrestiti());
        assertTrue(utente.isAlLimite());
        assertTrue(utente.haPrestitiInRitardo());
    }
}
