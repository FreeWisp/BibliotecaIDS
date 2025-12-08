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

        Utente u = (Utente) utenteReposit.cercaPerMatricola(matricola);
        Libro l = (Libro)libroReposit.cercaTramiteIsbn(isbn);

        if (u == null || l == null)
            return false;

        if (l.getNumCopie() <= 0)
            return false;

        if (prestitoReposit.contoPrestitiAttiviUtente(matricola) >= 3)
            return false;

        Prestito p = new Prestito(matricola, isbn, dataRestituzione);
        prestitoReposit.inserisciPrestito(p);

        l.setNumCopie(l.getNumCopie() - 1);
        libroReposit.modificaLibro(l);

        return true;
    }

   public boolean registraRestituzione(Prestito p) {
        Libro l;
        l = (Libro) libroReposit.cercaTramiteIsbn(p.getIsbnLibro());
        if (l == null) 
            return false;

        p.setDataResituzione(true);
        prestitoReposit.modificaPrestito(p);

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
