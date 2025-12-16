/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.repository.inmemoria;

import it.unisa.biblioteca.model.Prestito;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 *
 * @author ernes
 */
public class InMemoriaPrestitoRepositoryTest {
    
    public InMemoriaPrestitoRepositoryTest() {
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
     * Test of inserisciPrestito method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testInserisciPrestito() {
        System.out.println("inserisciPrestito");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();
        Prestito p = new Prestito("M1", "ISBN1", LocalDate.now());

        repo.inserisciPrestito(p);

        assertEquals(1, repo.listaCompleta().size());
        assertEquals(p, repo.listaCompleta().get(0));
    }

    /**
     * Test of eliminaPrestito method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testEliminaPrestito() {
        System.out.println("eliminaPrestito");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();
        Prestito p = new Prestito("M1", "ISBN1", LocalDate.now());

        repo.inserisciPrestito(p);
        repo.eliminaPrestito(p);

        assertTrue(repo.listaCompleta().isEmpty());
    }

    /**
     * Test of cercaPrestitiAttivi method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testCercaPrestitiAttivi() {
        System.out.println("cercaPrestitiAttivi");
       InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M2", "ISBN2", LocalDate.now()));

        List<Prestito> result = repo.cercaPrestitiAttivi();
        assertEquals(2, result.size());
    }

    /**
     * Test of cercaPrestitiPerMatricolaUtente method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testCercaPrestitiPerMatricolaUtente() {
        System.out.println("cercaPrestitiPerMatricolaUtente");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M2", "ISBN2", LocalDate.now()));

        List<Prestito> result = repo.cercaPrestitiPerMatricolaUtente("M1");

        assertEquals(1, result.size());
        assertEquals("M1", result.get(0).getMatricolaUtente());
    }

    /**
     * Test of cercaPrestitiPerIsbnLibro method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testCercaPrestitiPerIsbnLibro() {
        System.out.println("cercaPrestitiPerIsbnLibro");
         InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M2", "ISBN2", LocalDate.now()));

        List<Prestito> result = repo.cercaPrestitiPerIsbnLibro("ISBN2");

        assertEquals(1, result.size());
        assertEquals("ISBN2", result.get(0).getIsbnLibro());
    }

    /**
     * Test of cercaPrestitiAttiviOrdinatiPerDataRestituzione method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testCercaPrestitiAttiviOrdinatiPerDataRestituzione() {
        System.out.println("cercaPrestitiAttiviOrdinatiPerDataRestituzione");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        Prestito p1 = new Prestito("M1", "ISBN1", LocalDate.of(2024, 5, 10));
        Prestito p2 = new Prestito("M1", "ISBN2", LocalDate.of(2024, 5, 1));

        repo.inserisciPrestito(p1);
        repo.inserisciPrestito(p2);

        List<Prestito> result = repo.cercaPrestitiAttiviOrdinatiPerDataRestituzione();
        assertEquals(p2, result.get(0)); 
        assertEquals(p1, result.get(1));

    }

    /**
     * Test of contoPrestitiAttiviUtente method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testContoPrestitiAttiviUtente() {
        System.out.println("contoPrestitiAttiviUtente");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M1", "ISBN2", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M2", "ISBN3", LocalDate.now()));

        assertEquals(2, repo.contoPrestitiAttiviUtente("M1"));
        
    }

    /**
     * Test of modificaPrestito method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testModificaPrestito() {
        System.out.println("modificaPrestito");
         InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();
        Prestito p = new Prestito("M1", "ISBN1", LocalDate.now());

        assertThrows(UnsupportedOperationException.class, () -> {
        repo.modificaPrestito(p);
    });
    }

    /**
     * Test of listaCompleta method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testListaCompleta() {
        System.out.println("listaCompleta");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.inserisciPrestito(new Prestito("M2", "ISBN2", LocalDate.now()));

        assertEquals(2, repo.listaCompleta().size());
    }

    /**
     * Test of svuota method, of class InMemoriaPrestitoRepository.
     */
    @Test
    public void testSvuota() {
        System.out.println("svuota");
        InMemoriaPrestitoRepository repo = new InMemoriaPrestitoRepository();

        repo.inserisciPrestito(new Prestito("M1", "ISBN1", LocalDate.now()));
        repo.svuota();

        assertTrue(repo.listaCompleta().isEmpty());
    }
    
}