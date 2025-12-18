/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.repository.inmemoria;

import it.unisa.biblioteca.model.Libro;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ernes
 */
public class InMemoriaLibroRepositoryTest {
    
    public InMemoriaLibroRepositoryTest() {
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
     * Test of inserisciLibro method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testInserisciLibro() {
        System.out.println("inserisciLibro");
        InMemoriaLibroRepository repository= new InMemoriaLibroRepository();
        Libro libro = new Libro("1984", "George Orwell", "9837483", 2021,5);
        repository.inserisciLibro(libro);
        List<Libro> risultato= repository.cercaTramiteIsbn("9837483");
        assertEquals(1, risultato.size());
        assertEquals(libro, risultato.get(0));
        
    }

    /**
     * Test of modificaLibro method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testModificaLibro() {
        InMemoriaLibroRepository repository = new InMemoriaLibroRepository();

    Libro libro = new Libro("1984", "George Orwell", "9837483", 2021, 5);
    repository.inserisciLibro(libro);

    // CAMBIO numCopie
    Libro modificato = new Libro("1984", "George Orwell", "9837483", 2021, 10);
    repository.modificaLibro(modificato);

    Libro result = repository.cercaTramiteIsbn("9837483").get(0);
    assertEquals(10, result.getNumCopie());
        
      
    }

    /**
     * Test of eliminaLibro method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testEliminaLibro() {
        System.out.println("eliminaLibro");
        InMemoriaLibroRepository repository= new InMemoriaLibroRepository();
        Libro libro = new Libro("1984", "George Orwell", "9837483", 2021,5);
        repository.inserisciLibro(libro);
        repository.eliminaLibro("9837483");
        assertTrue(repository.cercaTramiteIsbn("1").isEmpty());
        
    }

    /**
     * Test of cercaTramiteIsbn method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testCercaTramiteIsbn() {
        System.out.println("cercaTramiteIsbn");
        InMemoriaLibroRepository repository = new InMemoriaLibroRepository();

        List<Libro> result = repository.cercaTramiteIsbn("X");
        assertTrue(result.isEmpty());
        
    }

    /**
     * Test of cercaTramiteTitolo method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testCercaTramiteTitolo() {
        System.out.println("cercaTramiteTitolo");
        InMemoriaLibroRepository repository = new InMemoriaLibroRepository();

        repository.inserisciLibro(new Libro("1984", "Orwell", "1", 2020, 3));
        repository.inserisciLibro(new Libro("Animal Farm", "Orwell", "2", 1945, 2));

        List<Libro> result = repository.cercaTramiteTitolo("1984");

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitolo());
    }   

    /**
     * Test of cercaTramiteAutore method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testCercaTramiteAutore() {
        System.out.println("cercaTramiteAutore");
       InMemoriaLibroRepository repository = new InMemoriaLibroRepository();

        repository.inserisciLibro(new Libro("1984", "Orwell", "1", 2020, 3));
        repository.inserisciLibro(new Libro("Harry Potter", "Rowling", "2", 1997, 5));

        List<Libro> result = repository.cercaTramiteAutore("Orwell");

        assertEquals(1, result.size());
        assertEquals("Orwell", result.get(0).getAutore());
    }

    /**
     * Test of listaOrdinataPerTitolo method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testListaOrdinataPerTitolo() {
        System.out.println("listaOrdinataPerTitolo");
        InMemoriaLibroRepository repository= new InMemoriaLibroRepository();
        repository.inserisciLibro(new Libro("Zoo", "A", "1", 2020, 1));
        repository.inserisciLibro(new Libro("Animal Farm", "B", "2", 1945, 2));

        List<Libro> result = repository.listaOrdinataPerTitolo();

        assertEquals("Animal Farm", result.get(0).getTitolo());
        assertEquals("Zoo", result.get(1).getTitolo());
        
        
    }

    /**
     * Test of listaCompleta method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testListaCompleta() {
        System.out.println("listaCompleta");
        InMemoriaLibroRepository repository = new InMemoriaLibroRepository();
        repository.inserisciLibro(new Libro("Zoo", "A", "1", 2020, 1));
        repository.inserisciLibro(new Libro("Animal Farm", "B", "2", 1945, 2));
        
        List<Libro> result = repository.listaCompleta();
        
        assertEquals(2, result.size());
        
    }

    /**
     * Test of svuota method, of class InMemoriaLibroRepository.
     */
    @Test
    public void testSvuota() {
        System.out.println("svuota");
        InMemoriaLibroRepository repository = new InMemoriaLibroRepository();
        repository.inserisciLibro(new Libro("1984", "Orwell", "1", 2020, 3));
        repository.svuota();

        assertTrue(repository.listaCompleta().isEmpty());
       
    }
    
}
