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
import it.unisa.biblioteca.model.Prestito;
import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.*;
import java.util.stream.Collectors;



import java.time.LocalDate;
import java.util.List;

public class ServizioPrestiti{

    private final PrestitoRepository prestitoReposit;
    private final UtenteRepository utenteReposit;
    private final LibroRepository libroReposit;

    public ServizioPrestiti(PrestitoRepository prestitoReposit,
                           UtenteRepository utenteReposit,
                           LibroRepository libroReposit) {
        this.prestitoReposit = prestitoReposit;
        this.utenteReposit = utenteReposit;
        this.libroReposit = libroReposit;
    }

    
    
    public boolean registraPrestito(String matricola, String isbn, LocalDate dataRestituzione) {
    // Cerca utente - il metodo restituisce una Lista
    List<Utente> utentiTrovati = utenteReposit.cercaPerMatricola(matricola);
    if (utentiTrovati == null || utentiTrovati.isEmpty()) {
        return false;
    }
    Utente u = utentiTrovati.get(0);  // Prende il primo risultato
    
    // Cerca libro - il metodo restituisce una Lista
    List<Libro> libriTrovati = libroReposit.cercaTramiteIsbn(isbn);
    if (libriTrovati == null || libriTrovati.isEmpty()) {
        return false;
    }
    Libro l = libriTrovati.get(0);  // Prende il primo risultato
    
    // Verifica disponibilità copie
    if (l.getNumCopie() <= 0) {
        return false;
    }
    
    // Verifica limite prestiti utente
    if (prestitoReposit.contoPrestitiAttiviUtente(matricola) >= 3) {
        return false;
    }
    
    // Registra prestito
    Prestito p = new Prestito(matricola, isbn, dataRestituzione);
    prestitoReposit.inserisciPrestito(p);
    
    // Decrementa copie disponibili
    l.setNumCopie(l.getNumCopie() - 1);
    libroReposit.modificaLibro(l);
    
    return true;
}
    
    public PrestitoRepository getRepository() {
    return prestitoReposit;
    }


    public boolean registraRestituzione(Prestito p) {
        // Cerca libro
        List<Libro> libriTrovati = libroReposit.cercaTramiteIsbn(p.getIsbnLibro());
        if (libriTrovati == null || libriTrovati.isEmpty()) {
            return false;
        }
        Libro l = libriTrovati.get(0);

        // ✅ RIMUOVI il prestito dalla lista invece di modificarlo
        prestitoReposit.eliminaPrestito(p);

        // Incrementa le copie disponibili
        l.setNumCopie(l.getNumCopie() + 1);
        libroReposit.modificaLibro(l);

        return true;
    }

    public List<Prestito> prestitiAttiviOrdinati() {
        return prestitoReposit.cercaPrestitiAttiviOrdinatiPerDataRestituzione();
    }

    public List<Prestito> prestitiInRitardo() {
        LocalDate oggi = LocalDate.now();
        return prestitoReposit.cercaPrestitiAttivi().stream()
                .filter(p -> p.getDataRestituzione().isBefore(oggi))
                 .collect(Collectors.toList());
    }
}
