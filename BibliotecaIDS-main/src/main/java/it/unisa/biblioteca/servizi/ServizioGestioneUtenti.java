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

import it.unisa.biblioteca.model.Utente;
import it.unisa.biblioteca.repository.UtenteRepository;

import java.util.List;

public class ServizioGestioneUtenti {

    private final UtenteRepository reposit;

    public ServizioGestioneUtenti(UtenteRepository reposit) {
        this.reposit = reposit;
    }

    public void aggiungi(Utente utente) {
        reposit.inserisciUtente(utente);
    }

    public void modifica(Utente utente) {
        
        reposit.modificaUtente(utente);
    }

    public void elimina(String matricola) {
        reposit.eliminaUtente(matricola);
    }
    
    public UtenteRepository getRepository() {
    return reposit;
}


    public List<Utente> cercaPerMatricola(String matricola) {
        return reposit.cercaPerMatricola(matricola);
    }

    public List<Utente> cercaPerCognome(String cognome) {
        return reposit.cercaPerCognome(cognome);
    }

    public List<Utente> listaOrdinata() {
        return reposit.listaOrdinataPerCognomeNome();
    }
}
