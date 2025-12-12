/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.repository;

/**
 *
 * @author sabatinocupo
 */

/**
 * @file PrestitoRepository.java
 * @brief Interfaccia per la gestione dei prestiti all'interno del sistema Biblioteca.
 *
 * Questa interfaccia definisce le operazioni necessarie per inserire, modificare,
 * eliminare e ricercare i prestiti, oltre a controllare eventuali limiti imposti agli utenti.
 */
import it.unisa.biblioteca.model.Prestito;
import java.util.List;

/**
 * @interface PrestitoRepository
 * @brief Repository per la gestione delle entità Prestito.
 *
 * Fornisce le operazioni CRUD e ulteriori servizi dedicati al monitoraggio dello stato
 * dei prestiti e al controllo delle regole del sistema, come il limite massimo di prestiti per utente.
 */
public interface PrestitoRepository {
    
    /**
     * @brief Inserisce un nuovo prestito nel sistema.
     * @param prestito Oggetto Prestito da registrare.
     */
    void inserisciPrestito (Prestito prestito);  
    
    /**
     * @brief Elimina un prestito dal sistema (restituzione del libro).
     * @param prestito Oggetto Prestito da rimuovere.
     */
    void eliminaPrestito (Prestito prestito);
    
    /**
     * @brief Modifica un prestito già presente, come un aggiornamento della data di restituzione.
     * @param prestito Oggetto Prestito aggiornato.
     */
    void modificaPrestito(Prestito prestito);

    /**
     * @brief Restituisce la lista dei prestiti attivi.
     * @return Lista di prestiti non ancora restituiti.
     */
    List<Prestito> cercaPrestitiAttivi();   
    
    /**
     * @brief Cerca i prestiti effettuati da un determinato utente tramite la sua matricola.
     * @param matricola Matricola dell'utente.
     * @return Lista di prestiti associati a quell'utente.
     */
    List<Prestito> cercaPrestitiPerMatricolaUtente(String matricola);
    
     /**
     * @brief Cerca i prestiti relativi a un determinato libro tramite ISBN.
     * @param isbn Codice ISBN del libro.
     * @return Lista di prestiti che coinvolgono quel libro.
     */
    List<Prestito> cercaPrestitiPerIsbnLibro(String isbn);

    /**
     * @brief Restituisce i prestiti attivi ordinati per data di restituzione prevista.
     * @return Lista di prestiti attivi ordinata per data.
     */
    List<Prestito> cercaPrestitiAttiviOrdinatiPerDataRestituzione();

     /**
     * @brief Conta i prestiti attivi di un utente, utile per verificare i limiti di prestito.
     * @param matricola Matricola dell'utente.
     * @return Numero di prestiti attivi dell'utente.
     */
    int contoPrestitiAttiviUtente(String matricola);

     /**
     * Restituisce tutti i prestiti, attivi e non.
     */
    List<Prestito> listaCompleta();

    /**
     * Rimuove tutti i prestiti.
     */
    void svuota();

    
}
