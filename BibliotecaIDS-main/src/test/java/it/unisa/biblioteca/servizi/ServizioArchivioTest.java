/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.model.*;
import org.junit.jupiter.api.*;

import java.nio.file.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ernes
 */
public class ServizioArchivioTest {
    
    private Biblioteca biblioteca;
    private ServizioArchivio servizio;
    private Path fileTemp;
    
    @BeforeEach
    void setUp() throws Exception {

       Files.deleteIfExists(Path.of("biblioteca.json"));

    biblioteca = new Biblioteca();

    // sicurezza: pulizia repository
    biblioteca.getLibroRepository().svuota();
    biblioteca.getUtenteRepository().svuota();
    biblioteca.getPrestitoRepository().svuota();

    servizio = new ServizioArchivio(biblioteca);

    fileTemp = Files.createTempFile("archivio_test", ".json");

    
    Libro libro = new Libro(
            "Clean Code",
            "Robert Martin",
            "ISBN001",
            2008,
            3
    );

    Utente utente = new Utente(
            "Mario",
            "Rossi",
            "M001",
            "mario.rossi@studenti.unisa.it"
    );

    Prestito prestito = new Prestito(
            utente.getMatricola(),
            libro.getIsbn(),
            LocalDate.of(2024, 6, 1)
    );

    biblioteca.getLibroRepository().inserisciLibro(libro);
    biblioteca.getUtenteRepository().inserisciUtente(utente);
    biblioteca.getPrestitoRepository().inserisciPrestito(prestito);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(fileTemp);
    }

    /**
     * Test of salva method, of class ServizioArchivio.
     */
    @Test
    public void testSalva() throws Exception {
        System.out.println("salva");
        servizio.salva(fileTemp.toString());

        assertTrue(Files.exists(fileTemp));
        assertTrue(Files.size(fileTemp) > 0);
    }

    /**
     * Test of carica method, of class ServizioArchivio.
     */
    @Test
    public void testCarica() throws Exception {
        System.out.println("carica");
       servizio.salva(fileTemp.toString());

    ServizioArchivio.ArchivioData dati = servizio.carica(fileTemp.toString());

    assertNotNull(dati);
    assertEquals(1, dati.libri.size());
    assertEquals(1, dati.utenti.size());
    assertEquals(1, dati.prestiti.size());

    assertEquals("Clean Code", dati.libri.get(0).getTitolo());
    assertEquals("Mario", dati.utenti.get(0).getNome());
    assertEquals(LocalDate.of(2024, 6, 1),
                 dati.prestiti.get(0).getDataRestituzione());
    }

    /**
     * Test of aggiornaBiblioteca method, of class ServizioArchivio.
     */
    @Test
    public void testAggiornaBiblioteca() throws Exception {
       servizio.salva(fileTemp.toString());
    ServizioArchivio.ArchivioData dati = servizio.carica(fileTemp.toString());

    biblioteca.getLibroRepository().svuota();
    biblioteca.getUtenteRepository().svuota();
    biblioteca.getPrestitoRepository().svuota();

    servizio.aggiornaBiblioteca(dati);

    assertEquals(1, biblioteca.getLibroRepository().listaCompleta().size());
    assertEquals(1, biblioteca.getUtenteRepository().listaCompleta().size());
    assertEquals(1, biblioteca.getPrestitoRepository().listaCompleta().size());
    }
    
}