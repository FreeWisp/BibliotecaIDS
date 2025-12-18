/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maio
 */
package it.unisa.biblioteca.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @class UtenteConPrestiti
 * @brief Rappresenta un utente con la lista dei suoi prestiti attivi
 * 
 * Classe di supporto per visualizzare in modo compatto
 * tutti i prestiti di un utente in un'unica cella della lista.
 */
public class UtenteConPrestiti {
    
    /**
     * @class DettaglioPrestito
     * @brief Informazioni di un singolo prestito per la visualizzazione
     */
    public static class DettaglioPrestito {
        private String titoloLibro;
        private String autoreLibro;
        private LocalDate scadenza;
        private boolean inRitardo;
        
        public DettaglioPrestito(String titolo, String autore, LocalDate scadenza, boolean inRitardo) {
            this.titoloLibro = titolo;
            this.autoreLibro = autore;
            this.scadenza = scadenza;
            this.inRitardo = inRitardo;
        }
        
        public String getTitoloLibro() { return titoloLibro; }
        public String getAutoreLibro() { return autoreLibro; }
        public LocalDate getScadenza() { return scadenza; }
        public boolean isInRitardo() { return inRitardo; }
    }
    
    private String matricola;
    private String nomeCompleto;
    private int numeroPrestiti;
    private boolean haPrestitiInRitardo;
    private boolean alLimite; // 3 prestiti
    private List<DettaglioPrestito> prestiti;
    
    /**
     * @brief Costruttore
     * @param matricola Matricola dell'utente
     * @param nomeCompleto Nome completo (cognome + nome)
     */
    public UtenteConPrestiti(String matricola, String nomeCompleto) {
        this.matricola = matricola;
        this.nomeCompleto = nomeCompleto;
        this.prestiti = new ArrayList<>();
        this.numeroPrestiti = 0;
        this.haPrestitiInRitardo = false;
        this.alLimite = false;
    }
    
    /**
     * @brief Aggiunge un prestito alla lista
     * @param titolo Titolo del libro
     * @param autore Autore del libro
     * @param scadenza Data di scadenza
     * @param inRitardo Se il prestito è in ritardo
     */
    public void aggiungiPrestito(String titolo, String autore, LocalDate scadenza, boolean inRitardo) {
        prestiti.add(new DettaglioPrestito(titolo, autore, scadenza, inRitardo));
        numeroPrestiti = prestiti.size();
        if (inRitardo) {
            haPrestitiInRitardo = true;
        }
        if (numeroPrestiti >= 3) {
            alLimite = true;
        }
    }
    
    // Getters
    public String getMatricola() { return matricola; }
    public String getNomeCompleto() { return nomeCompleto; }
    public int getNumeroPrestiti() { return numeroPrestiti; }
    public boolean haPrestitiInRitardo() { return haPrestitiInRitardo; }
    public boolean isAlLimite() { return alLimite; }
    public List<DettaglioPrestito> getPrestiti() { return prestiti; }
}