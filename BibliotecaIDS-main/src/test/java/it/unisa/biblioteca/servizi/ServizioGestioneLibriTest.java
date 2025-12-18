/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.servizi.ServizioGestioneLibri;
import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaPrestitoRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaLibroRepository;
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
public class ServizioGestioneLibriTest {
    
    private ServizioGestioneLibri servizio;
    
    public ServizioGestioneLibriTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
   @BeforeEach
    public void setUp() {
        InMemoriaLibroRepository libroReposit = new InMemoriaLibroRepository();
        InMemoriaPrestitoRepository prestitoReposit = new InMemoriaPrestitoRepository();

        servizio = new ServizioGestioneLibri(libroReposit, prestitoReposit);
}   

    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of aggiungi method, of class ServizioGestioneLibri.
     */
    @Test
    public void testAggiungi() {
        System.out.println("aggiungi");
        Libro libro = new Libro("1984", "George Orwell", "ISBN1", 1949, 5);
        servizio.aggiungi(libro);

        List<Libro> risultato = servizio.cercaPerIsbn("ISBN1");
        assertEquals(1, risultato.size());
        assertEquals(libro, risultato.get(0));
    }

    /**
     * Test of modifica method, of class ServizioGestioneLibri.
     */
    @Test
    public void testModifica() {
        System.out.println("modifica");
        Libro libro = new Libro("1984", "George Orwell", "ISBN1", 1949, 5);
        servizio.aggiungi(libro);

        Libro modificato = new Libro("1984", "George Orwell", "ISBN1", 1949, 10);
        servizio.modifica(modificato);

        Libro result = servizio.cercaPerIsbn("ISBN1").get(0);
        assertEquals(10, result.getNumCopie());
    }

    /**
     * Test of elimina method, of class ServizioGestioneLibri.
     */
    @Test
    public void testElimina() {
        System.out.println("elimina");
        Libro libro = new Libro("1984", "George Orwell", "ISBN1", 1949, 5);
        servizio.aggiungi(libro);

        servizio.elimina("ISBN1");

        assertTrue(servizio.cercaPerIsbn("ISBN1").isEmpty());
    }

    /**
     * Test of getRepository method, of class ServizioGestioneLibri.
     */
    @Test
    public void testGetRepository() {
        System.out.println("getRepository");
        
        assertNotNull(servizio.getRepository());
    }

    /**
     * Test of listaOrdinataTitolo method, of class ServizioGestioneLibri.
     */
    @Test
    public void testListaOrdinataTitolo() {
        System.out.println("listaOrdinataTitolo");
        servizio.aggiungi(new Libro("Zoo", "Autore1", "1", 2020, 1));
        servizio.aggiungi(new Libro("Animal Farm", "Autore2", "2", 1945, 2));

        List<Libro> result = servizio.listaOrdinataTitolo();

        assertEquals("Animal Farm", result.get(0).getTitolo());
        assertEquals("Zoo", result.get(1).getTitolo());
    }

    /**
     * Test of cercaPerIsbn method, of class ServizioGestioneLibri.
     */
    @Test
    public void testCercaPerIsbn() {
        System.out.println("cercaPerIsbn");
        servizio.aggiungi(new Libro("1984", "Orwell", "ISBN1", 1949, 5));

        List<Libro> result = servizio.cercaPerIsbn("ISBN1");
        assertEquals(1, result.size());
    }

    /**
     * Test of cercaPerTitolo method, of class ServizioGestioneLibri.
     */
    @Test
    public void testCercaPerTitolo() {
        System.out.println("cercaPerTitolo");
        servizio.aggiungi(new Libro("1984", "Orwell", "1", 1949, 5));
        servizio.aggiungi(new Libro("Animal Farm", "Orwell", "2", 1945, 3));

        List<Libro> result = servizio.cercaPerTitolo("1984");

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitolo());

    }

    /**
     * Test of cercaPerAutore method, of class ServizioGestioneLibri.
     */
    @Test
    public void testCercaPerAutore() {
        System.out.println("cercaPerAutore");
        servizio.aggiungi(new Libro("1984", "Orwell", "1", 1949, 5));
        servizio.aggiungi(new Libro("Harry Potter", "Rowling", "2", 1997, 10));

        List<Libro> result = servizio.cercaPerAutore("Orwell");

        assertEquals(1, result.size());
        assertEquals("Orwell", result.get(0).getAutore());
    }
    
}
