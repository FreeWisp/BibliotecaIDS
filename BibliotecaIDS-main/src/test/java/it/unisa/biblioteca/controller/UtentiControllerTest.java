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
public class UtentiControllerTest {
    
    public UtentiControllerTest() {
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
     * Test of setBiblioteca method, of class UtentiController.
     */
    @Test
    public void testSetBiblioteca() {
        System.out.println("setBiblioteca");
        Biblioteca biblioteca = null;
        UtentiController instance = new UtentiController();
        instance.setBiblioteca(biblioteca);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMainController method, of class UtentiController.
     */
    @Test
    public void testSetMainController() {
        System.out.println("setMainController");
        MainController mainController = null;
        UtentiController instance = new UtentiController();
        instance.setMainController(mainController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
