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
public class UtenteTest {
    
    public UtenteTest() {
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
     * Test of getNome method, of class Utente.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        
        assertEquals("Mario", tizio.getNome());
        
                
        
       
    }

    /**
     * Test of setNome method, of class Utente.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        
        tizio.setNome("Giovanni");
        assertEquals("Giovanni", tizio.getNome());
        
    }

    /**
     * Test of getCognome method, of class Utente.
     */
    @Test
    public void testGetCognome() {
        System.out.println("getCognome");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        
        assertEquals("Coppola", tizio.getCognome());
        
    }

    /**
     * Test of setCognome method, of class Utente.
     */
    @Test
    public void testSetCognome() {
        System.out.println("setCognome");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        
        tizio.setCognome("Bodenizza");
        
        assertEquals("Bodenizza", tizio.getCognome());
    }

    /**
     * Test of getMatricola method, of class Utente.
     */
    @Test
    public void testGetMatricola() {
        System.out.println("getMatricola");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        assertEquals("1234567", tizio.getMatricola());
       
    }

    /**
     * Test of setMatricola method, of class Utente.
     */
    @Test
    public void testSetMatricola() {
        System.out.println("setMatricola");
         Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
         
         tizio.setMatricola("4358945");
         assertEquals("4358945", tizio.getMatricola());
         
    }

    /**
     * Test of getEmail method, of class Utente.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        assertEquals("m.coppola@studenti.unisa.it", tizio.getEmail());
        
    }

    /**
     * Test of setEmail method, of class Utente.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        
        tizio.setEmail("mar.coppola@studenti.unisa.it");
        assertEquals("mar.coppola@studenti.unisa.it", tizio.getEmail());
    }

    /**
     * Test of isAttivo method, of class Utente.
     */
    @Test
    public void testIsAttivo() {
        System.out.println("isAttivo");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        assertTrue(tizio.isAttivo());
        
    }

    /**
     * Test of setAttivo method, of class Utente.
     */
    @Test
    public void testSetAttivo() {
        System.out.println("setAttivo");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        tizio.setAttivo(false);
        assertFalse(tizio.isAttivo());
    }

    /**
     * Test of toString method, of class Utente.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        String result=tizio.toString();
        assertTrue(result.contains("Coppola"));
        assertTrue(result.contains("Mario"));
        assertTrue(result.contains("1234567"));
        assertTrue(result.contains("m.coppola@studenti.unisa.it"));
        
        
        
        
    }

    /**
     * Test of equals method, of class Utente.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        Utente instance = new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
      
    }

    /**
     * Test of hashCode method, of class Utente.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Utente tizio= new Utente("Mario", "Coppola", "1234567","m.coppola@studenti.unisa.it");
        Utente tizia= new Utente("Giovanna", "Botta","1234567", "g.botta@studenti.unisa.it");
        
        assertEquals(tizio.hashCode(), tizia.hashCode());
    }
    
}
