
package it.unisa.biblioteca.model;

/**
 * @file Biblioteca.java
 * @brief Classe principale che gestisce il sistema biblioteca
 * @author ernes
 * @date 2025-12-07
 */

import it.unisa.biblioteca.repository.inmemoria.InMemoriaLibroRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaUtenteRepository;
import it.unisa.biblioteca.repository.inmemoria.InMemoriaPrestitoRepository;

import it.unisa.biblioteca.servizi.ServizioGestioneLibri;
import it.unisa.biblioteca.servizi.ServizioGestioneUtenti;
import it.unisa.biblioteca.servizi.ServizioPrestiti;
import it.unisa.biblioteca.servizi.ServizioArchivio;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Biblioteca
 * @brief Classe contenitore e coordinatore per tutti i servizi della biblioteca
 * 
 * Questa classe funge da punto di accesso centrale per tutte le operazioni
 * del sistema biblioteca. Mantiene le liste in memoria di libri, utenti e prestiti,
 * e fornisce accesso ai servizi di gestione attraverso il pattern Service Layer
 * e Repository per la persistenza dei dati.
 * 
 * @details La classe inizializza automaticamente tutti i repository in memoria
 * e i servizi necessari per la gestione della biblioteca. Non richiede configurazione
 * esterna e può essere utilizzata immediatamente dopo l'istanziazione.
 */

public class Biblioteca {

    // Dati mantenuti nella biblioteca
    private List<Libro> libri;
    private List<Utente> utenti;
    private List<Prestito> prestiti;

    //  Repository 
    private InMemoriaLibroRepository libroRepository;
    private InMemoriaUtenteRepository utenteRepository;
    private InMemoriaPrestitoRepository prestitoRepository;

    //Servizi 
    private ServizioGestioneLibri libriService;
    private ServizioGestioneUtenti utentiService;
    private ServizioPrestiti prestitiService;
    private ServizioArchivio archivioService;

    /**
     * @brief Costruttore della classe Biblioteca
     * 
     * Inizializza tutte le strutture dati necessarie (liste di libri, utenti, prestiti),
     * crea i repository in memoria per la persistenza dei dati e inizializza tutti
     * i servizi di gestione collegandoli ai rispettivi repository.
     * 
     * @details Il costruttore esegue le seguenti operazioni:
     * - Crea liste vuote per libri, utenti e prestiti
     * - Inizializza i repository InMemoria per ogni entità
     * - Crea i servizi passando i repository necessari come dipendenze
     * 
     * @note Non lancia eccezioni. Dopo la creazione l'oggetto è pronto all'uso.
     */
    
    public Biblioteca() {

        // Collezioni locali
        this.libri = new ArrayList<>();
        this.utenti = new ArrayList<>();
        this.prestiti = new ArrayList<>();

        // Repository InMemoria
        this.libroRepository = new InMemoriaLibroRepository();
        this.utenteRepository = new InMemoriaUtenteRepository();
        this.prestitoRepository = new InMemoriaPrestitoRepository();

        // Servizi
        this.libriService = new ServizioGestioneLibri(libroRepository);
        this.utentiService = new ServizioGestioneUtenti(utenteRepository);
        this.prestitiService = new ServizioPrestiti(prestitoRepository, utenteRepository, libroRepository);
        this.archivioService = new ServizioArchivio(this);
        
        //  Carica i dati dal file all'avvio (se esiste)
         try {
             var dati = archivioService.carica("biblioteca.json");
             if (dati != null) {
                 archivioService.aggiornaBiblioteca(dati);
             }
         } catch (Exception e) {
             System.out.println("Nessun archivio trovato, si parte con dati vuoti.");
         }
    }

    // Getter collezioni
    
   /**
    * @brief Restituisce una copia della lista dei libri
    * 
    * @return Copia della lista di tutti i libri in biblioteca*/
    public List<Libro> getLibri() {
        return new ArrayList<>(libri);
    }
    /**
    * @brief Restituisce una copia della lista degli utenti
    * 
    * @return Copia della lista di tutti gli utenti registrati*/
    public List<Utente> getUtenti() {
        return new ArrayList<>(utenti); 
    }
    /**
    * @brief Restituisce una copia della lista dei prestiti 
    * 
    * @return Copia della lista di tutti i prestiti(attivi e chiusi)*/
    public List<Prestito> getPrestiti() {
        return new ArrayList<>(prestiti);
    }

    // Getter servizi
    /** @brief Restituisce il servizio di gestione libri
     * 
     * Fornisce accesso alle operazioni CRUD sui libri: inserimento, modifica,
     * eliminazione, ricerca e ordinamento.
     * 
     * @return Istanza del servizio ServizioGestioneLibri
     * @see ServizioGestioneLibri
     */
    public ServizioGestioneLibri getLibriService() { 
        return libriService; 
    }
    /** @brief Restituisce il servizio di gestione utenti
     * 
     * Fornisce accesso alle operazioni CRUD sui libri: inserimento, modifica,
     * eliminazione, ricerca e ordinamento.
     * 
     * @return Istanza del servizio ServizioGestioneLibri
     * @see ServizioGestioneLibri
     */
    public ServizioGestioneUtenti getUtentiService() {
        return utentiService; 
    }
    /**
     * @brief Restituisce il servizio di gestione prestiti
     * 
     * Fornisce accesso alle funzionalità di prestito e restituzione libri,
     * inclusi controlli su disponibilità copie e limiti per utente.
     * 
     * @return Istanza del servizio ServizioPrestiti
     * @see ServizioPrestiti
     */
    public ServizioPrestiti getPrestitiService() { 
        return prestitiService;
    }
    
    /**
     * @brief Restituisce il servizio di archiviazione
     * 
     * Fornisce accesso alle funzionalità di salvataggio e caricamento dati,
     * backup e ripristino del sistema biblioteca.
     * 
     * @return Istanza del servizio ServizioArchivio
     * @see ServizioArchivio
     */
    public ServizioArchivio getArchivioService() { 
        return archivioService;
    }
    
     /** GETTER dei repository (aggiunti ora) */
    public InMemoriaLibroRepository getLibroRepository() {
        return libroRepository;
    }

    public InMemoriaUtenteRepository getUtenteRepository() {
        return utenteRepository;
    }

    public InMemoriaPrestitoRepository getPrestitoRepository() {
        return prestitoRepository;
    }
    
    
}