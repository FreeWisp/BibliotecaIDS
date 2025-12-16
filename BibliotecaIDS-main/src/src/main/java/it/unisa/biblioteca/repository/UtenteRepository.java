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
 * @file UtenteRepository.java
 * @brief Interfaccia per la gestione degli utenti del sistema Biblioteca.
 *
 * Definisce le operazioni per aggiungere, modificare, eliminare e cercare utenti
 * all’interno del sistema, oltre a fornire una lista ordinata degli stessi.
 */
import it.unisa.biblioteca.model.Utente;
import java.util.List;

/**
 * @interface UtenteRepository
 * @brief Repository dedicato alla gestione delle entità Utente.
 *
 * Include le operazioni CRUD e i metodi di ricerca basati sugli attributi principali
 * degli utenti registrati nel sistema.
 */
public interface UtenteRepository {
    
      /**
     * @brief Inserisce un nuovo utente nel sistema.
     * @param utente Oggetto Utente da aggiungere.
     */
    void inserisciUtente (Utente utente);
    
    /**
     * @brief Modifica i dati di un utente già presente.
     * @param utente Oggetto Utente aggiornato.
     */
    void modificaUtente (Utente utente);
    
    /**
     * @brief Elimina un utente tramite la sua matricola.
     * @param matricola Identificativo univoco dell’utente da rimuovere.
     */
    void eliminaUtente (String matricola);

    
    
    /**
     * @brief Cerca un utente tramite la matricola.
     * @param matricola Identificativo univoco dell’utente.
     * @return Lista di utenti creata in base alla matricola.
     */
    List<Utente> cercaPerMatricola(String matricola);
    
     /**
     * @brief Cerca gli utenti tramite il cognome.
     * @param cognome Cognome dell’utente da ricercare.
     * @return Lista di utenti creata per cognome .
     */
    List<Utente> cercaPerCognome(String cognome);

     /**
     * @brief Restituisce la lista degli utenti ordinata per cognome e poi nome.
     * @return Lista ordinata di utenti.
     */
    List<Utente> listaOrdinataPerCognomeNome();
    
    
    /**
    * @brief Restituisce tutti gli utenti presenti.
    * @return Lista di tutti gli utenti
    */
    List<Utente> listaCompleta();

    /**
     * @brief Rimuove tutti gli utenti.
     * 
     */
    void svuota();

    
}
