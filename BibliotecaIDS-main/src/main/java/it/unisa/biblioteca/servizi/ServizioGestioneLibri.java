/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.servizi;

/**
 *
 * @author cupos
 */

import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.repository.LibroRepository;

import java.util.List;

/**
 *
 * @author cupos
 */
public class ServizioGestioneLibri {

    private final LibroRepository reposit;

    public ServizioGestioneLibri(LibroRepository reposit) {
        this.reposit = reposit;
    }

    public void aggiungi(Libro libro) {
        reposit.inserisciLibro(libro);
    }

    public void modifica (Libro libro) {
        reposit.modificaLibro(libro);
    }

    public void elimina(String isbn) {
        reposit.eliminaLibro(isbn);
    }

    public List<Libro> listaOrdinataTitolo() {
        return reposit.listaOrdinataPerTitolo();
    }

    public List<Libro> cercaPerIsbn(String isbn) {
        return reposit.cercaTramiteIsbn(isbn);
    }

    public List<Libro> cercaPerTitolo(String titolo) {
        return reposit.cercaTramiteTitolo(titolo);
    }

    public List<Libro> cercaPerAutore(String autore) {
        return reposit.cercaTramiteAutore(autore);
    }
}
