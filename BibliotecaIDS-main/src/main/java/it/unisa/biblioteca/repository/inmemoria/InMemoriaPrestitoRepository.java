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

import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.repository.PrestitoRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author cupos
 */
public class InMemoriaPrestitoRepository implements PrestitoRepository {

    private final List<Prestito> prestiti = new ArrayList<>();

    @Override
    public void inserisciPrestito(Prestito prestito) {
        prestiti.add(prestito);
    }

   @Override
    public void eliminaPrestito(Prestito prestito) {
        prestiti.remove(prestito);
    }

    @Override
    public List<Prestito> cercaPrestitiAttivi() {
        return new ArrayList<>(prestiti);
    }

    @Override
    public List<Prestito> cercaPrestitiPerMatricolaUtente(String matricola) {
        return prestiti.stream()
                .filter(p -> p.getMatricolaUtente().equals(matricola))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestito> cercaPrestitiPerIsbnLibro(String isbn) {
        return prestiti.stream()
                .filter(p -> p.getIsbnLibro().equals(isbn))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestito> cercaPrestitiAttiviOrdinatiPerDataRestituzione() {
        return prestiti.stream()
                .sorted(Comparator.comparing(Prestito::getDataRestituzione))
                .collect(Collectors.toList());
    }

    @Override
    public int contoPrestitiAttiviUtente(String matricola) {
        return (int) prestiti.stream()
                .filter(p -> p.getMatricolaUtente().equals(matricola))
                .count();
    }

    @Override
    public void modificaPrestito(Prestito prestito) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
