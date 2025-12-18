/**
 * @file ServizioGestioneUtenti.java
 * @brief Servizio per la gestione degli utenti della biblioteca
 * @author mario
 */

package it.unisa.biblioteca.servizi;

import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.UtenteRepository;
import java.util.List;

/**
 * @class ServizioGestioneUtenti
 * @brief Servizio che gestisce tutte le operazioni relative agli utenti
 * 
 * Questa classe implementa la logica di business per la gestione degli
 * utenti della biblioteca. Si occupa di coordinare le operazioni di 
 * registrazione, modifica, eliminazione e ricerca degli utenti, delegando 
 * la persistenza al repository.
 */
public class ServizioGestioneUtenti {

    /**
     * @brief Repository per la gestione della persistenza degli utenti
     */
    private final UtenteRepository reposit;

    /**
     * @brief Costruttore del servizio di gestione utenti
     * 
     * Inizializza il servizio con il repository fornito, utilizzando
     * il pattern Dependency Injection per disaccoppiare il servizio
     * dall'implementazione specifica del repository.
     * 
     * @param reposit Il repository degli utenti da utilizzare per la persistenza
     */
    public ServizioGestioneUtenti(UtenteRepository reposit) {
        this.reposit = reposit;
    }

    /**
     * @brief Registra un nuovo utente nel sistema
     * 
     * Inserisce un nuovo utente nella biblioteca. L'utente viene passato al
     * repository per la persistenza. Il metodo non verifica duplicati
     * basati sulla matricola (questa logica è delegata al repository).
     * 
     * @param utente L'utente da registrare nel sistema
     */
    public void aggiungi(Utente utente) {
        reposit.inserisciUtente(utente);
    }

    /**
     * @brief Modifica le informazioni di un utente esistente
     * 
     * Aggiorna i dati di un utente già presente nel sistema.
     * L'utente viene identificato tramite la sua matricola univoca.
     * 
     * @param utente L'utente con le informazioni aggiornate
     */
    public void modifica(Utente utente) {
        reposit.modificaUtente(utente);
    }

    /**
     * @brief Elimina un utente dal sistema
     * 
     * Rimuove un utente dalla biblioteca utilizzando la sua matricola.
     * L'operazione rimuove definitivamente l'utente dal sistema.
     * Attenzione: verificare che l'utente non abbia prestiti attivi
     * prima di eliminarlo.
     * 
     * @param matricola La matricola dell'utente da eliminare
     */
    public void elimina(String matricola) {
        reposit.eliminaUtente(matricola);
    }
    
    /**
     * @brief Restituisce il repository utilizzato dal servizio
     * 
     * Metodo di accesso al repository sottostante, utile per 
     * operazioni avanzate o per test.
     * 
     * @return Il repository degli utenti
     */
    public UtenteRepository getRepository() {
        return reposit;
    }

    /**
     * @brief Cerca utenti tramite numero di matricola
     * 
     * Effettua una ricerca degli utenti utilizzando il numero di matricola.
     * Poiché la matricola è univoca, la lista conterrà al massimo un elemento.
     * 
     * @param matricola Il numero di matricola da cercare
     * @return Lista contenente l'utente trovato (o lista vuota se non trovato)
     */
    public List<Utente> cercaPerMatricola(String matricola) {
        return reposit.cercaPerMatricola(matricola);
    }

    /**
     * @brief Cerca utenti tramite cognome
     * 
     * Effettua una ricerca degli utenti il cui cognome contiene la stringa
     * specificata. La ricerca è case-insensitive e restituisce tutti
     * gli utenti che corrispondono al criterio.
     * 
     * @param cognome Il cognome (o parte di esso) da cercare
     * @return Lista degli utenti che corrispondono al criterio di ricerca
     */
    public List<Utente> cercaPerCognome(String cognome) {
        return reposit.cercaPerCognome(cognome);
    }

    /**
     * @brief Restituisce la lista completa degli utenti ordinata
     * 
     * Ottiene tutti gli utenti presenti nel sistema, ordinati alfabeticamente
     * prima per cognome e poi per nome in ordine crescente (A-Z).
     * 
     * @return Lista degli utenti ordinata per cognome e nome
     */
    public List<Utente> listaOrdinata() {
        return reposit.listaOrdinataPerCognomeNome();
    }
}