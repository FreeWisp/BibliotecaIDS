/**
 * @file ServizioPrestiti.java
 * @brief Servizio per la gestione dei prestiti della biblioteca
 * @author Sistema Gestione Biblioteca
 * @date 2025-12-15
 */

package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

/**
 * @class ServizioPrestiti
 * @brief Servizio che gestisce tutte le operazioni relative ai prestiti
 * 
 * Questa classe implementa la logica di business per la gestione dei prestiti
 * di libri della biblioteca. Si occupa di coordinare le operazioni di 
 * registrazione prestiti, restituzione, verifica disponibilità e ricerca
 * dei prestiti attivi e in ritardo. Coordina tre repository (prestiti, 
 * utenti e libri) per garantire la coerenza dei dati.
 */
public class ServizioPrestiti {

    /**
     * @brief Repository per la gestione della persistenza dei prestiti
     */
    private final PrestitoRepository prestitoReposit;
    
    /**
     * @brief Repository per la gestione degli utenti
     */
    private final UtenteRepository utenteReposit;
    
    /**
     * @brief Repository per la gestione dei libri
     */
    private final LibroRepository libroReposit;

    /**
     * @brief Costruttore del servizio di gestione prestiti
     * 
     * Inizializza il servizio con i tre repository necessari, utilizzando
     * il pattern Dependency Injection. Questo servizio coordina le operazioni
     * tra prestiti, utenti e libri.
     * 
     * @param prestitoReposit Il repository dei prestiti
     * @param utenteReposit Il repository degli utenti
     * @param libroReposit Il repository dei libri
     */
    public ServizioPrestiti(PrestitoRepository prestitoReposit,
                           UtenteRepository utenteReposit,
                           LibroRepository libroReposit) {
        this.prestitoReposit = prestitoReposit;
        this.utenteReposit = utenteReposit;
        this.libroReposit = libroReposit;
    }

    /**
     * @brief Registra un nuovo prestito di un libro a un utente
     * 
     * Registra un prestito verificando tutte le condizioni necessarie:
     * - L'utente esiste nel sistema
     * - Il libro esiste nel sistema
     * - Ci sono copie disponibili del libro (numCopie > 0)
     * - L'utente non ha già 3 prestiti attivi
     * 
     * Se tutte le condizioni sono soddisfatte, crea il prestito e
     * decrementa automaticamente il numero di copie disponibili del libro.
     * 
     * @param matricola La matricola dell'utente che effettua il prestito
     * @param isbn Il codice ISBN del libro da prestare
     * @param dataRestituzione La data prevista per la restituzione
     * @return true se il prestito è stato registrato con successo, false altrimenti
     */
    public boolean registraPrestito(String matricola, String isbn, LocalDate dataRestituzione) {
        // Cerca utente - il metodo restituisce una Lista
        List<Utente> utentiTrovati = utenteReposit.cercaPerMatricola(matricola);
        if (utentiTrovati == null || utentiTrovati.isEmpty()) {
            return false;
        }
        Utente u = utentiTrovati.get(0);  // Prende il primo risultato
        
        // Cerca libro - il metodo restituisce una Lista
        List<Libro> libriTrovati = libroReposit.cercaTramiteIsbn(isbn);
        if (libriTrovati == null || libriTrovati.isEmpty()) {
            return false;
        }
        Libro l = libriTrovati.get(0);  // Prende il primo risultato
        
        // Verifica disponibilità copie
        if (l.getNumCopie() <= 0) {
            return false;
        }
        
        // Verifica limite prestiti utente (massimo 3 prestiti contemporanei)
        if (prestitoReposit.contoPrestitiAttiviUtente(matricola) >= 3) {
            return false;
        }
        
        // Registra prestito
        Prestito p = new Prestito(matricola, isbn, dataRestituzione);
        prestitoReposit.inserisciPrestito(p);
        
        // Decrementa copie disponibili
        l.setNumCopie(l.getNumCopie() - 1);
        libroReposit.modificaLibro(l);
        
        return true;
    }
    
    /**
     * @brief Restituisce il repository utilizzato dal servizio
     * 
     * Metodo di accesso al repository dei prestiti sottostante, 
     * utile per operazioni avanzate o per test.
     * 
     * @return Il repository dei prestiti
     */
    public PrestitoRepository getRepository() {
        return prestitoReposit;
    }

    /**
     * @brief Registra la restituzione di un libro prestato
     * 
     * Gestisce la restituzione di un libro, rimuovendo il prestito dalla
     * lista dei prestiti attivi e incrementando automaticamente il numero
     * di copie disponibili del libro.
     * 
     * @param p Il prestito da chiudere (restituzione)
     * @return true se la restituzione è stata registrata con successo, false altrimenti
     */
    public boolean registraRestituzione(Prestito p) {
        // Cerca libro
        List<Libro> libriTrovati = libroReposit.cercaTramiteIsbn(p.getIsbnLibro());
        if (libriTrovati == null || libriTrovati.isEmpty()) {
            return false;
        }
        Libro l = libriTrovati.get(0);

        // Rimuovi il prestito dalla lista
        prestitoReposit.eliminaPrestito(p);

        // Incrementa le copie disponibili
        l.setNumCopie(l.getNumCopie() + 1);
        libroReposit.modificaLibro(l);

        return true;
    }

    /**
     * @brief Restituisce tutti i prestiti attivi ordinati per data di restituzione
     * 
     * Ottiene la lista completa di tutti i prestiti attualmente attivi,
     * ordinata per data di restituzione prevista (dal più vicino al più lontano).
     * Utile per visualizzare i prestiti che devono essere restituiti a breve.
     * 
     * @return Lista dei prestiti attivi ordinata per data di restituzione
     */
    public List<Prestito> prestitiAttiviOrdinati() {
        return prestitoReposit.cercaPrestitiAttiviOrdinatiPerDataRestituzione();
    }

    /**
     * @brief Restituisce tutti i prestiti in ritardo
     * 
     * Filtra tutti i prestiti attivi e restituisce solo quelli la cui
     * data di restituzione prevista è anteriore alla data odierna.
     * Utile per identificare gli utenti che devono essere sollecitati
     * per la restituzione.
     * 
     * @return Lista dei prestiti in ritardo
     */
    public List<Prestito> prestitiInRitardo() {
        LocalDate oggi = LocalDate.now();
        return prestitoReposit.cercaPrestitiAttivi().stream()
                .filter(p -> p.getDataRestituzione().isBefore(oggi))
                .collect(Collectors.toList());
    }
}