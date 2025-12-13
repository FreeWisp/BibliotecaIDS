/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.repository.inmemoria;

/**
 *
 * @author cupos
 */
import it.unisa.biblioteca.model.Libro;
import it.unisa.biblioteca.repository.LibroRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoriaLibroRepository implements LibroRepository {

    private final Map<String, Libro> libri = new HashMap<>();

    public InMemoriaLibroRepository() {
    // Costruttore vuoto - la HashMap viene inizializzata alla dichiarazione
    }

    @Override
    public void inserisciLibro(Libro libro) {
        libri.put(libro.getIsbn(), libro);
    }

    @Override
    public void modificaLibro(Libro libro) {
        libri.put(libro.getIsbn(), libro);
    }

    @Override
    public void eliminaLibro(String isbn) {
        libri.remove(isbn);
    }

    @Override
    public List<Libro> cercaTramiteIsbn(String isbn) {
        Libro libro = libri.get(isbn);
        if (libro == null) {
            return Collections.emptyList();  
        }
        return Collections.singletonList(libro); 
    }

    /**
     *
     * @param titolo
     * @return
     */
    @Override
    public List<Libro> cercaTramiteTitolo(String titolo) {
        return libri.values().stream()
                .filter(l -> l.getTitolo().equalsIgnoreCase(titolo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> cercaTramiteAutore(String autore) {
        return libri.values().stream()
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> listaOrdinataPerTitolo() {
        return libri.values().stream()
                .sorted(Comparator.comparing(Libro::getTitolo))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Libro> listaCompleta() {
        return new ArrayList<Libro>(libri.values());
    }

    @Override
    public void svuota() {
        libri.clear();
    }

}

