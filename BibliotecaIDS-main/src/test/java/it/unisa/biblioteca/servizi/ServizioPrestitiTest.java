/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.servizi;


import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.inmemoria.*;
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.repository.PrestitoRepository;
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
public class ServizioPrestitiTest {
    private InMemoriaPrestitoRepository prestitoRepo;
    private InMemoriaUtenteRepository utenteRepo;
    private InMemoriaLibroRepository libroRepo;
    private ServizioPrestiti servizio;
    private Utente utente;
    private Libro libro;
    
    public ServizioPrestitiTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        prestitoRepo = new InMemoriaPrestitoRepository();
        utenteRepo = new InMemoriaUtenteRepository();
        libroRepo = new InMemoriaLibroRepository();
        servizio = new ServizioPrestiti(prestitoRepo, utenteRepo, libroRepo);
        
        utente = new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it");
        libro = new Libro("1984", "George Orwell", "ISBN001", 2021, 5);
        
        utenteRepo.inserisciUtente(utente);
        libroRepo.inserisciLibro(libro);
        
    }
    
    
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of registraPrestito method, of class ServizioPrestiti.
     */
    @Test
    public void testRegistraPrestito() {
        System.out.println("registraPrestito");
        LocalDate dataRest = LocalDate.now().plusDays(7);
        boolean result = servizio.registraPrestito("U001", "ISBN001", dataRest);
        assertTrue(result, "Il prestito deve essere registrato correttamente");
        
        Prestito p = prestitoRepo.cercaPrestitiAttivi().get(0);
        assertEquals("U001", p.getMatricolaUtente());
        assertEquals("ISBN001", p.getIsbnLibro());
        assertEquals(4, libroRepo.cercaTramiteIsbn("ISBN001").get(0).getNumCopie());
    }

    /**
     * Test of getRepository method, of class ServizioPrestiti.
     */
    @Test
    public void testGetRepository() {
        System.out.println("getRepository");
        
        assertEquals(prestitoRepo, servizio.getRepository(), "Il repository restituito deve essere quello inizializzato");
    }

    /**
     * Test of registraRestituzione method, of class ServizioPrestiti.
     */
    @Test
    public void testRegistraRestituzione() {
        System.out.println("registraRestituzione");
        LocalDate dataRest = LocalDate.now().plusDays(7);
        servizio.registraPrestito("U001", "ISBN001", dataRest);
        Prestito p = prestitoRepo.cercaPrestitiAttivi().get(0);

        boolean result = servizio.registraRestituzione(p);
        assertTrue(result, "La restituzione deve essere registrata correttamente");
        assertEquals(5, libroRepo.cercaTramiteIsbn("ISBN001").get(0).getNumCopie());
        assertTrue(prestitoRepo.cercaPrestitiAttivi().isEmpty(), "La lista dei prestiti attivi deve essere vuota");
    }

    /**
     * Test of prestitiAttiviOrdinati method, of class ServizioPrestiti.
     */
    @Test
    public void testPrestitiAttiviOrdinati() {
        System.out.println("prestitiAttiviOrdinati");
        servizio.registraPrestito("U001", "ISBN001", LocalDate.now().plusDays(7));
        servizio.registraPrestito("U001", "ISBN001", LocalDate.now().plusDays(3));

        List<Prestito> attivi = servizio.prestitiAttiviOrdinati();
        assertEquals(2, attivi.size());
        assertTrue(attivi.get(0).getDataRestituzione().isBefore(attivi.get(1).getDataRestituzione()));
    }

    /**
     * Test of prestitiInRitardo method, of class ServizioPrestiti.
     */
    @Test
    public void testPrestitiInRitardo() {
        System.out.println("prestitiInRitardo");
        servizio.registraPrestito("U001", "ISBN001", LocalDate.now().minusDays(1));
        servizio.registraPrestito("U001", "ISBN001", LocalDate.now().plusDays(1));

        List<Prestito> ritardo = servizio.prestitiInRitardo();
        assertEquals(1, ritardo.size());
        assertTrue(ritardo.get(0).getDataRestituzione().isBefore(LocalDate.now()));
    
    }
    
}
