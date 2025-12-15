/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.UtenteRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaUtenteRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ernes
 */
public class ServizioGestioneUtentiTest {
    
    private ServizioGestioneUtenti servizio;
    private UtenteRepository repository;
    
    
    @BeforeEach
    public void setUp() {
        repository = new InMemoriaUtenteRepository();
        servizio = new ServizioGestioneUtenti(repository);
    }
    
    

    /**
     * Test of aggiungi method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testAggiungi() {
        System.out.println("aggiungi");
         Utente u = new Utente("Mario", "Rossi", "M1", "mario@mail.it");

        servizio.aggiungi(u);

        List<Utente> result = repository.cercaPerMatricola("M1");
        assertEquals(1, result.size());
        assertEquals(u, result.get(0));
    }

    /**
     * Test of modifica method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testModifica() {
        System.out.println("modifica");
        Utente originale = new Utente("Mario", "Rossi", "M1", "vecchia@mail.it");
        repository.inserisciUtente(originale);

        Utente modificato = new Utente("Mario", "Rossi", "M1", "nuova@mail.it");
        servizio.modifica(modificato);

        Utente result = repository.cercaPerMatricola("M1").get(0);
        assertEquals("nuova@mail.it", result.getEmail());
    }

    /**
     * Test of elimina method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testElimina() {
        System.out.println("elimina");
         Utente u = new Utente("Mario", "Rossi", "M1", "mario@mail.it");
        repository.inserisciUtente(u);

        servizio.elimina("M1");

        assertTrue(repository.cercaPerMatricola("M1").isEmpty());
    }

    /**
     * Test of getRepository method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testGetRepository() {
        System.out.println("getRepository");
        assertSame(repository, servizio.getRepository());
    }

    /**
     * Test of cercaPerMatricola method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testCercaPerMatricola() {
        System.out.println("cercaPerMatricola");
         Utente u = new Utente("Mario", "Rossi", "M1", "mario@mail.it");
        repository.inserisciUtente(u);

        List<Utente> result = servizio.cercaPerMatricola("M1");

        assertEquals(1, result.size());
        assertEquals(u, result.get(0));
    }

    /**
     * Test of cercaPerCognome method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testCercaPerCognome() {
        System.out.println("cercaPerCognome");
        repository.inserisciUtente(new Utente("Mario", "Rossi", "M1", "a@mail.it"));
        repository.inserisciUtente(new Utente("Luigi", "Bianchi", "M2", "b@mail.it"));

        List<Utente> result = servizio.cercaPerCognome("ross");

        assertEquals(1, result.size());
        assertEquals("Rossi", result.get(0).getCognome());
    }

    /**
     * Test of listaOrdinata method, of class ServizioGestioneUtenti.
     */
    @Test
    public void testListaOrdinata() {
        System.out.println("listaOrdinata");
        repository.inserisciUtente(new Utente("Luigi", "Rossi", "M1", "a@mail.it"));
        repository.inserisciUtente(new Utente("Mario", "Bianchi", "M2", "b@mail.it"));
        repository.inserisciUtente(new Utente("Anna", "Rossi", "M3", "c@mail.it"));

        List<Utente> result = servizio.listaOrdinata();

        assertEquals("Bianchi", result.get(0).getCognome());
        assertEquals("Rossi", result.get(1).getCognome());
        assertEquals("Anna", result.get(1).getNome());
    }
    
}