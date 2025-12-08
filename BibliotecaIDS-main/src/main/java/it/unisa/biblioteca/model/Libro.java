/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.biblioteca.model;
/**
 *
 * @author mario
 */
public class Libro {

    private String titolo;
    private String autore;
    private String isbn;
    private int annoPubblicazione;
    private int numCopie;

    public Libro(String titolo, String autore, String isbn, int annoPubblicazione, int numCopie) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.annoPubblicazione = annoPubblicazione;
        this.numCopie= numCopie;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getAnnoPubblicazione(){
        return annoPubblicazione;
    }
    public void setAnnoPubblicazione(int annoPubblicazione){
        this.annoPubblicazione = annoPubblicazione;
    }
    
     public int getNumCopie() {
        return numCopie;
    }

    public void setNumCopie(int numCopie) {
        this.numCopie = numCopie;
    }

    @Override
    public String toString() {
        return  "Titolo del libro:" + titolo + "/n" + "Gli autori sono:"  + autore + "/n " + "Anno di pubblicazione" + annoPubblicazione + "/n"  + "ISBN:" + isbn + "/n" + "Numero di copie: " + numCopie;
    }
}


/*




*/
