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

import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.UtenteRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoriaUtenteRepository implements UtenteRepository {

    private final Map<String, Utente> utenti = new HashMap<>();

    @Override
    public void inserisciUtente(Utente utente) {
        utenti.put(utente.getMatricola(), utente);
    }

    @Override
    public void modificaUtente(Utente utente) {
        utenti.put(utente.getMatricola(), utente);
    }

   @Override
    public void eliminaUtente(String matricola) {
        utenti.remove(matricola);
    }

   
    @Override
    public List<Utente> cercaPerMatricola(String matricola) {
        Utente utente = utenti.get(matricola);
        if (utente != null) {
            return List.of(utente);  // Ritorna una lista con 1 elemento
        }
        return List.of();  // Ritorna lista vuota se non trovato
    }

    @Override
    public List<Utente> cercaPerCognome(String cognome) {
        return utenti.values().stream()
                .filter(u -> u.getCognome().equalsIgnoreCase(cognome))
                .collect(Collectors.toList());
    }

    @Override
    public List<Utente> listaOrdinataPerCognomeNome() {
        return utenti.values().stream()
                .sorted(Comparator
                        .comparing(Utente::getCognome)
                        .thenComparing(Utente::getNome))
                .collect(Collectors.toList());
    }
}
