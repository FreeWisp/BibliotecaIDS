/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * @file Prestito.java
 * @brief Classe che rappresenta un prestito di un libro
 * @author Mario
 * @date 2025-12-07
 */

package it.unisa.biblioteca.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @class Prestito
 * @brief Rappresenta un prestito di un libro da parte di un utente
 * 
 * Contiene le informazioni sulla matricola dell'utente che ha preso in prestito
 * il libro, l'ISBN del libro prestato e la data prevista di restituzione.
 */
public class Prestito {
    
    private String matricolaUtente;
    private String isbnLibro;
    private LocalDate dataRestituzione;

    /**
     * @brief Costruisce un nuovo prestito
     * 
     * @param matricolaUtente La matricola dell'utente che effettua il prestito
     * @param isbnLibro Il codice ISBN del libro prestato
     * @param dataRestituzione La data prevista per la restituzione
     */
    public Prestito(String matricolaUtente, String isbnLibro, LocalDate dataRestituzione) {
        this.matricolaUtente = matricolaUtente;
        this.isbnLibro = isbnLibro;
        this.dataRestituzione = dataRestituzione;
    }

    /**
     * @brief Restituisce la matricola dell'utente
     * @return La matricola dell'utente che ha effettuato il prestito
     */
    public String getMatricolaUtente() { 
        return matricolaUtente; 
    }
    
    /**
     * @brief Imposta la matricola dell'utente
     * @param matricolaUtente La nuova matricola
     */
    public void setMatricolaUtente(String matricolaUtente) {
        this.matricolaUtente = matricolaUtente;
    }
    
    /**
     * @brief Restituisce l'ISBN del libro prestato
     * @return Il codice ISBN del libro
     */
    public String getIsbnLibro() {
        return isbnLibro; 
    }
    
    /**
     * @brief Imposta l'ISBN del libro prestato
     * @param isbnLibro Il nuovo codice ISBN
     */
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }
    
    /**
     * @brief Restituisce la data prevista di restituzione
     * @return La data di restituzione prevista
     */
    public LocalDate getDataRestituzione() { 
        return dataRestituzione; 
    }
    
    /**
     * @brief Imposta la data prevista di restituzione
     * @param dataRestituzione La nuova data di restituzione
     */
    public void setDataRestituzione(LocalDate dataRestituzione) {
        this.dataRestituzione = dataRestituzione;
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale del prestito
     * @return Stringa contenente le informazioni del prestito
     */
    @Override
    public String toString() {
        return "Matricola: " + matricolaUtente + "\n " + 
               "Isbn del libro: " + isbnLibro + "\n" + 
               "Data di restituzione: " + dataRestituzione;
    }

    /**
     * @brief Registra la restituzione del libro (metodo non implementato)
     * @param b Parametro non utilizzato
     * @throws UnsupportedOperationException Metodo non ancora supportato
     */
    public void setDataResituzione(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // altrimenti elimina prestito non è felice
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestito prestito = (Prestito) o;
        return matricolaUtente.equals(prestito.matricolaUtente) &&
               isbnLibro.equals(prestito.isbnLibro);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(matricolaUtente, isbnLibro);
    }
}