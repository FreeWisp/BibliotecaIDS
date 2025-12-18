/**
 * @file ServizioGestioneLibri.java
 * @brief Servizio per la gestione dei libri della biblioteca
 * @author mario
 */

package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.repository.LibroRepository;
import it.unisa.biblioteca.repository.PrestitoRepository;
import java.util.List;

/**
 * @class ServizioGestioneLibri
 * @brief Servizio che gestisce tutte le operazioni relative ai libri
 * 
 * Questa classe implementa la logica di business per la gestione del catalogo
 * dei libri della biblioteca. Si occupa di coordinare le operazioni di 
 * inserimento, modifica, eliminazione e ricerca dei libri, delegando 
 * la persistenza al repository. Include controlli di integrità referenziale
 * per prevenire la cancellazione di libri con prestiti attivi.
 */
public class ServizioGestioneLibri {

    /**
     * @brief Repository per la gestione della persistenza dei libri
     */
    private final LibroRepository reposit;
    
    /**
     * @brief Repository per verificare i prestiti attivi
     */
    private final PrestitoRepository prestitoRepo;

    /**
     * @brief Costruttore del servizio di gestione libri
     * 
     * Inizializza il servizio con i repository forniti, utilizzando
     * il pattern Dependency Injection per disaccoppiare il servizio
     * dall'implementazione specifica dei repository.
     * 
     * @param reposit Il repository dei libri da utilizzare per la persistenza
     * @param prestitoRepo Il repository dei prestiti per controlli di integrità
     */
    public ServizioGestioneLibri(LibroRepository reposit, PrestitoRepository prestitoRepo) {
        this.reposit = reposit;
        this.prestitoRepo = prestitoRepo;
    }

    /**
     * @brief Aggiunge un nuovo libro al catalogo della biblioteca
     * 
     * Inserisce un nuovo libro nel sistema. Il libro viene passato al
     * repository per la persistenza. Il metodo non verifica duplicati
     * basati sull'ISBN (questa logica è delegata al repository).
     * 
     * @param libro Il libro da aggiungere al catalogo
     */
    public void aggiungi(Libro libro) {
        reposit.inserisciLibro(libro);
    }

    /**
     * @brief Modifica le informazioni di un libro esistente
     * 
     * Aggiorna i dati di un libro già presente nel catalogo.
     * Il libro viene identificato tramite il suo ISBN univoco.
     * 
     * @param libro Il libro con le informazioni aggiornate
     */
    public void modifica(Libro libro) {
        reposit.modificaLibro(libro);
    }

    /**
     * @brief Elimina un libro dal catalogo con controllo integrità
     * 
     * Rimuove un libro dalla biblioteca utilizzando il suo codice ISBN.
     * Prima di procedere con l'eliminazione, verifica che non ci siano
     * prestiti attivi per questo libro. Questo previene problemi di
     * integrità referenziale e prestiti "orfani".
     * 
     * @param isbn Il codice ISBN del libro da eliminare
     * @return true se il libro è stato eliminato, false se ci sono prestiti attivi
     */
    public boolean elimina(String isbn) {
        // Verifica se ci sono prestiti attivi per questo libro
        List<Prestito> prestitiAttivi = prestitoRepo.cercaPrestitiPerIsbnLibro(isbn);
        
        if (prestitiAttivi != null && !prestitiAttivi.isEmpty()) {
            // Ci sono prestiti attivi, NON eliminare il libro
            return false;
        }
        
        // Nessun prestito attivo, procedi con l'eliminazione
        reposit.eliminaLibro(isbn);
        return true;
    }
    
    /**
     * @brief Restituisce il repository utilizzato dal servizio
     * 
     * Metodo di accesso al repository sottostante, utile per 
     * operazioni avanzate o per test.
     * 
     * @return Il repository dei libri
     */
    public LibroRepository getRepository() {
        return reposit;
    }

    /**
     * @brief Restituisce la lista completa dei libri ordinata per titolo
     * 
     * Ottiene tutti i libri presenti nel catalogo, ordinati alfabeticamente
     * per titolo in ordine crescente (A-Z).
     * 
     * @return Lista dei libri ordinata per titolo
     */
    public List<Libro> listaOrdinataTitolo() {
        return reposit.listaOrdinataPerTitolo();
    }

    /**
     * @brief Cerca libri nel catalogo tramite codice ISBN
     * 
     * Effettua una ricerca dei libri utilizzando il codice ISBN.
     * Poiché l'ISBN è univoco, la lista conterrà al massimo un elemento.
     * 
     * @param isbn Il codice ISBN da cercare
     * @return Lista contenente il libro trovato (o lista vuota se non trovato)
     */
    public List<Libro> cercaPerIsbn(String isbn) {
        return reposit.cercaTramiteIsbn(isbn);
    }

    /**
     * @brief Cerca libri nel catalogo tramite titolo
     * 
     * Effettua una ricerca dei libri il cui titolo contiene la stringa
     * specificata. La ricerca è case-insensitive e restituisce tutti
     * i libri che corrispondono al criterio.
     * 
     * @param titolo Il titolo (o parte di esso) da cercare
     * @return Lista dei libri che corrispondono al criterio di ricerca
     */
    public List<Libro> cercaPerTitolo(String titolo) {
        return reposit.cercaTramiteTitolo(titolo);
    }

    /**
     * @brief Cerca libri nel catalogo tramite autore
     * 
     * Effettua una ricerca dei libri il cui autore contiene la stringa
     * specificata. La ricerca è case-insensitive e restituisce tutti
     * i libri dello stesso autore.
     * 
     * @param autore L'autore (o parte del nome) da cercare
     * @return Lista dei libri che corrispondono al criterio di ricerca
     */
    public List<Libro> cercaPerAutore(String autore) {
        return reposit.cercaTramiteAutore(autore);
    }
}
