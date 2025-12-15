/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.repository.inmemoria;

import it.unisa.biblioteca.model.Utente;
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
public class InMemoriaUtenteRepositoryTest {
  
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
     * Test of inserisciUtente method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testInserisciUtente() {
        System.out.println("inserisciUtente");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        Utente utente = new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it");
        repository.inserisciUtente(utente);

        List<Utente> risultato = repository.cercaPerMatricola("U001");
        assertEquals(1, risultato.size());
        assertEquals(utente, risultato.get(0));
    }

    /**
     * Test of modificaUtente method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testModificaUtente() {
        System.out.println("modificaUtente");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        Utente utente = new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it");
        repository.inserisciUtente(utente);

        Utente modificato = new Utente("Mario", "Rossi", "U001", "nuova.email@studenti.unisa.it");
        repository.modificaUtente(modificato);

        Utente result = repository.cercaPerMatricola("U001").get(0);
        assertEquals("nuova.email@studenti.unisa.it", result.getEmail());
   }
    /**
     * Test of eliminaUtente method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testEliminaUtente() {
        System.out.println("eliminaUtente");
        System.out.println("eliminaUtente");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        Utente utente = new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it");
        repository.inserisciUtente(utente);
        repository.eliminaUtente("U001");

        assertTrue(repository.cercaPerMatricola("U001").isEmpty());
   }

    /**
     * Test of cercaPerMatricola method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testCercaPerMatricola() {
        System.out.println("cercaPerMatricola");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        List<Utente> result = repository.cercaPerMatricola("X");
        assertTrue(result.isEmpty()); 
    }

    /**
     * Test of cercaPerCognome method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testCercaPerCognome() {
        System.out.println("cercaPerCognome");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        repository.inserisciUtente(new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it"));
        repository.inserisciUtente(new Utente("Luigi", "Bianchi", "U002", "l.bianchi@studenti.unisa.it"));

        List<Utente> result = repository.cercaPerCognome("Rossi");

        assertEquals(1, result.size());
        assertEquals("Rossi", result.get(0).getCognome());
    }

    /**
     * Test of listaOrdinataPerCognomeNome method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testListaOrdinataPerCognomeNome() {
        System.out.println("listaOrdinataPerCognomeNome");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        repository.inserisciUtente(new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it"));
        repository.inserisciUtente(new Utente("Luigi", "Bianchi", "U002", "l.bianchi@studenti.unisa.it"));

        List<Utente> result = repository.listaOrdinataPerCognomeNome();

        assertEquals("Bianchi", result.get(0).getCognome());
        assertEquals("Rossi", result.get(1).getCognome());
    }


    /**
     * Test of listaCompleta method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testListaCompleta() {
        System.out.println("listaCompleta");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        repository.inserisciUtente(new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it"));
        repository.inserisciUtente(new Utente("Luigi", "Bianchi", "U002", "l.bianchi@studenti.unisa.it"));

        List<Utente> result = repository.listaCompleta();

        assertEquals(2, result.size());
    }

    /**
     * Test of svuota method, of class InMemoriaUtenteRepository.
     */
    @Test
    public void testSvuota() {
        System.out.println("svuota");
        InMemoriaUtenteRepository repository = new InMemoriaUtenteRepository();

        repository.inserisciUtente(new Utente("Mario", "Rossi", "U001", "m.rossi@studenti.unisa.it"));
        repository.svuota();

        assertTrue(repository.listaCompleta().isEmpty());
 }
    
}
