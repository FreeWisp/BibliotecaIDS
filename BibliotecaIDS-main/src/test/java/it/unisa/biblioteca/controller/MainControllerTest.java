/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.controller;

import it.unisa.biblioteca.model.Biblioteca;
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
public class MainControllerTest {
    
    public MainControllerTest() {
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
     * Test of setBiblioteca method, of class MainController.
     */
    @Test
    public void testSetBiblioteca() {
        System.out.println("setBiblioteca");
        Biblioteca biblioteca = null;
        MainController instance = new MainController();
        instance.setBiblioteca(biblioteca);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiornaStatisticheDashboard method, of class MainController.
     */
    @Test
    public void testAggiornaStatisticheDashboard() {
        System.out.println("aggiornaStatisticheDashboard");
        MainController instance = new MainController();
        instance.aggiornaStatisticheDashboard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caricaDaFile method, of class MainController.
     */
    @Test
    public void testCaricaDaFile() {
        System.out.println("caricaDaFile");
        MainController instance = new MainController();
        instance.caricaDaFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
