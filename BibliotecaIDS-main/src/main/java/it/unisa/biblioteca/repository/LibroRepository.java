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
 * @file LibroRepository.java
 * @brief Interfaccia per la gestione delle operazioni sui libri nel sistema Biblioteca.
 *
 * Questa interfaccia definisce i metodi fondamentali per inserire, modificare,
 * eliminare e cercare libri all'interno del file o del sistema di persistenza utilizzato.
 */


import it.unisa.biblioteca.model.Libro;
import java.util.List;

/**
 * @interface LibroRepository
 * @brief Repository per la gestione delle entità Libro.
 *
 * Fornisce le operazioni CRUD(Create, Read, Update, Delete) e i metodi di ricerca basati su diversi attributi del libro.
 */

public interface LibroRepository {
 
    /**
     * @brief Inserisce un nuovo libro nel sistema.
     * @param libro Oggetto Libro da aggiungere.
     */
    void inserisciLibro(Libro libro);     
    
    /**
     * @brief Modifica un libro già presente nel sistema.
     * @param libro Oggetto Libro aggiornato.
     */
    void modificaLibro(Libro libro);   
    
    /**
     * @brief Elimina un libro dal sistema tramite il suo ISBN.
     * @param isbn Codice ISBN univoco del libro da rimuovere.
     */
    void eliminaLibro(String isbn);              

 
    /**
     * @brief Cerca i libri tramite ISBN.
     * @param isbn Codice ISBN da ricercare.
     * @return Lista di libri che corrispondono all’ISBN fornito.
     */
    List<Libro> cercaTramiteIsbn(String isbn);
    
    /**
     * @brief Cerca i libri tramite il titolo.
     * @param titolo Titolo del libro da ricercare.
     * @return Lista di libri con titolo corrispondente.
     */
    List<Libro> cercaTramiteTitolo(String titolo);
    
    /**
     * @brief Cerca i libri tramite il nome dell’autore.
     * @param autore Nome dell’autore da ricercare.
     * @return Lista di libri scritti dall’autore indicato.
     */
    List<Libro> cercaTramiteAutore(String autore);

   
     /**
     * @brief Restituisce la lista dei libri ordinata alfabeticamente per titolo.
     * @return Lista di libri ordinata per titolo.
     */
    List<Libro> listaOrdinataPerTitolo();
    
    /**
    *@ brief Restituisce tutti i libri presenti.
     @ return Lista di tutti i libri presenti.
     */
    List<Libro> listaCompleta();

    /**
    * @ brief Rimuove tutti i libri.
    */
    void svuota();
}



