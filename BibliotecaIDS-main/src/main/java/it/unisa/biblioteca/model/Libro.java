/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * @file Libro.java
 * @brief Classe che rappresenta un libro della biblioteca
 * @author mario
 * @date 2025-12-07
 */

package it.unisa.biblioteca.model;

/**
 * @class Libro
 * @brief Rappresenta un libro con le sue informazioni principali
 * 
 * Questa classe contiene tutti i dati relativi a un libro presente
 * in biblioteca: informazioni bibliografiche (titolo, autore, ISBN, anno)
 * e informazioni gestionali (numero di copie disponibili).
 */
public class Libro {

    private String titolo;
    private String autore;
    private String isbn;
    private int annoPubblicazione;
    private int numCopie;

    
    public Libro() {
    // Necessario per Gson/Jackson
    }
    
    
    /**
     * @brief Costruisce un nuovo oggetto Libro con i parametri specificati
     * 
     * Crea un'istanza di Libro inizializzando tutti i suoi attributi
     * con i valori forniti.
     * 
     * @param titolo Il titolo del libro
     * @param autore L'autore del libro
     * @param isbn Il codice ISBN univoco del libro
     * @param annoPubblicazione L'anno di pubblicazione del libro
     * @param numCopie Il numero di copie disponibili in biblioteca
     */
    public Libro(String titolo, String autore, String isbn, int annoPubblicazione, int numCopie) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.annoPubblicazione = annoPubblicazione;
        this.numCopie = numCopie;
    }

    /**
     * @brief Restituisce il titolo del libro
     * 
     * @return Il titolo del libro come stringa
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @brief Imposta il titolo del libro
     * 
     * @param titolo Il nuovo titolo da assegnare al libro
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @brief Restituisce l'autore del libro
     * 
     * @return L'autore del libro come stringa
     */
    public String getAutore() {
        return autore;
    }

    /**
     * @brief Imposta l'autore del libro
     * 
     * @param autore Il nuovo autore da assegnare al libro
     */
    public void setAutore(String autore) {
        this.autore = autore;
    }

    /**
     * @brief Restituisce il codice ISBN del libro
     * 
     * @return Il codice ISBN univoco del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @brief Imposta il codice ISBN del libro
     * 
     * @param isbn Il nuovo codice ISBN da assegnare al libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    /**
     * @brief Restituisce l'anno di pubblicazione del libro
     * 
     * @return L'anno di pubblicazione come intero
     */
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
    
    /**
     * @brief Imposta l'anno di pubblicazione del libro
     * 
     * @param annoPubblicazione Il nuovo anno di pubblicazione
     */
    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
    
    /**
     * @brief Restituisce il numero di copie disponibili
     * 
     * @return Il numero di copie fisiche presenti in biblioteca
     */
    public int getNumCopie() {
        return numCopie;
    }

    /**
     * @brief Imposta il numero di copie disponibili
     * 
     * @param numCopie Il nuovo numero di copie disponibili
     */
    public void setNumCopie(int numCopie) {
        this.numCopie = numCopie;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del libro
     * 
     * Genera una stringa contenente tutte le informazioni del libro
     * in formato leggibile.
     * 
     * @return Stringa con i dettagli del libro
     */
    @Override
    public String toString() {
        return "Titolo del libro:" + titolo + "\n" + 
               "Gli autori sono:" + autore + "\n" + 
               "Anno di pubblicazione" + annoPubblicazione + "\n" + 
               "ISBN:" + isbn + "\n" + 
               "Numero di copie: " + numCopie;
    }
}

/*



*/
