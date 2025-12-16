/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.model;

import it.unisa.biblioteca.repository.inmemoria.InMemoriaLibroRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaPrestitoRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaUtenteRepository;
import it.unisa.biblioteca.servizi.ServizioArchivio;
import it.unisa.biblioteca.servizi.ServizioGestioneLibri;
import it.unisa.biblioteca.servizi.ServizioGestioneUtenti;
import it.unisa.biblioteca.servizi.ServizioPrestiti;
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
public class BibliotecaTest {
    
    public BibliotecaTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    

    /**
     * Test of getLibri method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetLibri() {
        System.out.println("getLibri");
        Biblioteca biblioteca= new Biblioteca();
       List<Libro> libri = biblioteca.getLibri();
        assertNotNull(libri);
        assertTrue(libri.isEmpty());
       
    }

    /**
     * Test of getUtenti method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetUtenti() {
        System.out.println("getUtenti");
        Biblioteca biblioteca= new Biblioteca();
        List<Utente> utenti = biblioteca.getUtenti();
        assertNotNull(utenti);
        assertTrue(utenti.isEmpty());
        
    }

    /**
     * Test of getPrestiti method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetPrestiti() {
        System.out.println("getPrestiti");
        Biblioteca biblioteca = new Biblioteca();
        List<Prestito> prestiti = biblioteca.getPrestiti();
        assertNotNull(prestiti);
        assertTrue(prestiti.isEmpty());
        
    }

    /**
     * Test of getLibriService method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetLibriService() {
        System.out.println("getLibriService");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getLibriService());
        
    }

    /**
     * Test of getUtentiService method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetUtentiService() {
        System.out.println("getUtentiService");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getUtentiService());
        
        
    }

    /**
     * Test of getPrestitiService method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetPrestitiService() {
        System.out.println("getPrestitiService");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getPrestitiService());
     
        
    }

    /**
     * Test of getArchivioService method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetArchivioService() {
        System.out.println("getArchivioService");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getArchivioService());
        
       
        
    }

    /**
     * Test of getLibroRepository method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetLibroRepository() {
        System.out.println("getLibroRepository");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getLibroRepository());
   
        
    }

    /**
     * Test of getUtenteRepository method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetUtenteRepository() {
        System.out.println("getUtenteRepository");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getUtenteRepository());
       
    }

    /**
     * Test of getPrestitoRepository method, of class Biblioteca.
     */
    @org.junit.jupiter.api.Test
    public void testGetPrestitoRepository() {
        System.out.println("getPrestitoRepository");
        Biblioteca biblioteca = new Biblioteca();
        assertNotNull(biblioteca.getPrestitoRepository());
        
    }
    
}