/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
import it.unisa.biblioteca.servizi.ServizioArchivio;
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
public class PrestitiControllerTest {
    
    public PrestitiControllerTest() {
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
     * Test of setBiblioteca method, of class PrestitiController.
     */
    @Test
    public void testSetBiblioteca() {
        System.out.println("setBiblioteca");
        Biblioteca biblioteca = null;
        PrestitiController instance = new PrestitiController();
        instance.setBiblioteca(biblioteca);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMainController method, of class PrestitiController.
     */
    @Test
    public void testSetMainController() {
        System.out.println("setMainController");
        MainController mainController = null;
        PrestitiController instance = new PrestitiController();
        instance.setMainController(mainController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setServizioArchivio method, of class PrestitiController.
     */
    @Test
    public void testSetServizioArchivio() {
        System.out.println("setServizioArchivio");
        ServizioArchivio archivio = null;
        PrestitiController instance = new PrestitiController();
        instance.setServizioArchivio(archivio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setArchivio method, of class PrestitiController.
     */
    @Test
    public void testSetArchivio() {
        System.out.println("setArchivio");
        ServizioArchivio archivio = null;
        PrestitiController instance = new PrestitiController();
        instance.setArchivio(archivio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
