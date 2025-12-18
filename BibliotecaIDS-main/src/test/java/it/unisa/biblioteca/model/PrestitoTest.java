/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.model;

import java.time.LocalDate;
import java.time.Month;
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
public class PrestitoTest {
    
    public PrestitoTest() {
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
     * Test of getMatricolaUtente method, of class Prestito.
     */
    @Test
    public void testGetMatricolaUtente() {
        System.out.println("getMatricolaUtente");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        assertEquals("0612708", p.getMatricolaUtente());
        
    }

    /**
     * Test of setMatricolaUtente method, of class Prestito.
     */
    @Test
    public void testSetMatricolaUtente() {
        System.out.println("setMatricolaUtente");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        p.setMatricolaUtente("0812708");
        assertEquals("0812708", p.getMatricolaUtente());
    }

    /**
     * Test of getIsbnLibro method, of class Prestito.
     */
    @Test
    public void testGetIsbnLibro() {
        System.out.println("getIsbnLibro");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        assertEquals("094673812",p.getIsbnLibro());
    }

    /**
     * Test of setIsbnLibro method, of class Prestito.
     */
    @Test
    public void testSetIsbnLibro() {
        System.out.println("setIsbnLibro");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        p.setIsbnLibro("034673812");
        assertEquals("034673812", p.getIsbnLibro());
        
    }

    /**
     * Test of getDataRestituzione method, of class Prestito.
     */
    @Test
    public void testGetDataRestituzione() {
        System.out.println("getDataRestituzione");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        assertEquals(LocalDate.of(2026,1,10), p.getDataRestituzione());
        
    }

    /**
     * Test of setDataRestituzione method, of class Prestito.
     */
    @Test
    public void testSetDataRestituzione() {
        System.out.println("setDataRestituzione");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        p.setDataRestituzione(LocalDate.of(2026,2,10));
        assertEquals(LocalDate.of(2026,2,10),p.getDataRestituzione());
        
    }

    /**
     * Test of toString method, of class Prestito.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        String risultato=p.toString();
        
        assertTrue(risultato.contains("0612708"));
        assertTrue(risultato.contains("094673812"));
        assertTrue(risultato.contains("2026-01-10"));
        
        
        
     }

    /**
     * Test of setDataResituzione method, of class Prestito.
     */
    @Test
    public void testSetDataResituzione() {
    System.out.println("setDataResituzione");

    Prestito p = new Prestito("0612708","094673812",LocalDate.of(2026, 1, 10));

    assertThrows(UnsupportedOperationException.class, () -> {
        p.setDataResituzione(true);
    });
}


    /**
     * Test of equals method, of class Prestito.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        Prestito p2= new Prestito("0612708", "094673812",LocalDate.of(2027,1,10) );
        
        assertEquals(p,p2);
        
        
    }

    /**
     * Test of hashCode method, of class Prestito.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Prestito p= new Prestito("0612708","094673812", LocalDate.of(2026,1,10) );
        Prestito p2= new Prestito("0612708", "094673812",LocalDate.of(2027,1,10) );
        
        assertEquals(p.hashCode(),p2.hashCode());
        
    }
    
}
