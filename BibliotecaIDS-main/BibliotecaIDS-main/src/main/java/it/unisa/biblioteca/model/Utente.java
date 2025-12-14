/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * @file Utente.java
 * @brief Classe che rappresenta un utente della biblioteca
 * @author Mario
 * @date 2025-12-07
 */

package it.unisa.biblioteca.model;
import java.util.Objects;

/**
 * @class Utente
 * @brief Rappresenta un utente registrato nella biblioteca
 * 
 * Contiene le informazioni anagrafiche e di contatto dell'utente,
 * oltre allo stato di attivazione del profilo.
 */
public class Utente {
   
    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private boolean attivo;

    /**
     * @brief Costruisce un nuovo utente con i parametri specificati
     * 
     * @param nome Il nome dell'utente
     * @param cognome Il cognome dell'utente
     * @param matricola La matricola univoca dell'utente
     * @param email L'indirizzo email dell'utente
     */
    public Utente(String nome, String cognome, String matricola, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.attivo = true;
    }

    /**
     * @brief Restituisce il nome dell'utente
     * @return Il nome come stringa
     */
    public String getNome() {
        return nome; 
    }
    
    /**
     * @brief Imposta il nome dell'utente
     * @param nome Il nuovo nome
     */
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    /**
     * @brief Restituisce il cognome dell'utente
     * @return Il cognome come stringa
     */
    public String getCognome() { 
        return cognome; 
    }
    
    /**
     * @brief Imposta il cognome dell'utente
     * @param cognome Il nuovo cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome; 
    }

    /**
     * @brief Restituisce la matricola dell'utente
     * @return La matricola univoca come stringa
     */
    public String getMatricola() { 
        return matricola; 
    }
    
    /**
     * @brief Imposta la matricola dell'utente
     * @param matricola La nuova matricola
     */
    public void setMatricola(String matricola) { 
        this.matricola = matricola; 
    }
    
    /**
     * @brief Restituisce l'email dell'utente
     * @return L'indirizzo email come stringa
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * @brief Imposta l'email dell'utente
     * @param email Il nuovo indirizzo email
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * @brief Verifica se l'utente è attivo
     * @return true se l'utente è attivo, false altrimenti
     */
    public boolean isAttivo() {
        return attivo;
    }
    
    /**
     * @brief Imposta lo stato di attivazione dell'utente
     * @param attivo true per attivare, false per disattivare
     */
    public void setAttivo(boolean attivo) {
        this.attivo = attivo; 
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale dell'utente
     * @return Stringa contenente le informazioni dell'utente
     */
    @Override
    public String toString() {
        return "Cognome:" + cognome + "\n " + 
               "Nome:" + nome + " \n" + 
               "Matricola:" + matricola + "\n" + 
               "Email universitaria " + email;
    }
    // metodo per far funzionare la ricerca
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(matricola, utente.matricola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricola);
    }
}