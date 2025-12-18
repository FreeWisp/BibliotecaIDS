/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.model;

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
public class LibroTest {
    
    public LibroTest() {
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
     * Test of getTitolo method, of class Libro.
     */
    @Test
    public void testGetTitolo() {
        System.out.println("getTitolo");
        Libro instance = new Libro();
        assertNull(instance.getTitolo());
        
       
     }

    /**
     * Test of setTitolo method, of class Libro.
     */
    @Test
    public void testSetTitolo() {
        System.out.println("setTitolo");
        Libro instance = new Libro();
        String titolo= "1984";
        instance.setTitolo(titolo);
        assertEquals(titolo, instance.getTitolo());
        
    }

    /**
     * Test of getAutore method, of class Libro.
     */
    @Test
    public void testGetAutore() {
        System.out.println("getAutore");
        Libro instance = new Libro();
        assertNull(instance.getAutore());
        
    }

    /**
     * Test of setAutore method, of class Libro.
     */
    @Test
    public void testSetAutore() {
        System.out.println("setAutore");
        Libro instance = new Libro();
        String autore="George Orwell";
        instance.setAutore(autore);
        assertEquals(autore, instance.getAutore());
        
    }

    /**
     * Test of getIsbn method, of class Libro.
     */
    @Test
    public void testGetIsbn() {
        System.out.println("getIsbn");
        Libro instance = new Libro();
        assertNull(instance.getIsbn());
    }

    /**
     * Test of setIsbn method, of class Libro.
     */
    @Test
    public void testSetIsbn() {
        System.out.println("setIsbn");
        Libro instance = new Libro();
        String isbn = "IBND75686NNVF";
        instance.setIsbn(isbn);
        assertEquals(isbn, instance.getIsbn());
        
    }

    /**
     * Test of getAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testGetAnnoPubblicazione() {
        System.out.println("getAnnoPubblicazione");
        Libro instance = new Libro();
        int expResult = 0;
        int result = instance.getAnnoPubblicazione();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testSetAnnoPubblicazione() {
        System.out.println("setAnnoPubblicazione");
        int annoPubblicazione = 2020;
        Libro instance = new Libro();
        instance.setAnnoPubblicazione(annoPubblicazione);
        assertEquals(annoPubblicazione, instance.getAnnoPubblicazione());
        
    }

    /**
     * Test of getNumCopie method, of class Libro.
     */
    @Test
    public void testGetNumCopie() {
        System.out.println("getNumCopie");
        Libro instance = new Libro();
        int expResult = 0;
        int result = instance.getNumCopie();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setNumCopie method, of class Libro.
     */
    @Test
    public void testSetNumCopie() {
        System.out.println("setNumCopie");
        int numCopie = 5;
        Libro instance = new Libro();
        instance.setNumCopie(numCopie);
        assertEquals(numCopie,instance.getNumCopie());
       
    }

    /**
     * Test of toString method, of class Libro.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Libro instance = new Libro("1984","George Orwell","IBND75686NNVF",2020, 5);
        
        String result= instance.toString();
        assertTrue(result.contains("1984"));
        assertTrue(result.contains("George Orwell"));
        assertTrue(result.contains("IBND75686NNVF"));
        assertTrue(result.contains("2020"));
        assertTrue(result.contains("5"));
        
        
       
       
    }
    
}
