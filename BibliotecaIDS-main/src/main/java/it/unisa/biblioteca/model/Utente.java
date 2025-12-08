/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.biblioteca.model;

/**
 *
 * @author mario
 */
public class Utente {
    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private boolean attivo;

    public Utente(String nome, String cognome, String matricola, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.attivo = attivo;
    }

    public String getNome(){
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getCognome() { 
        return cognome; 
    }
    public void setCognome(String cognome) {
        this.cognome = cognome; 
    }

    public String getMatricola() { 
        return matricola; 
    }
    public void setMatricola(String matricola) { 
        this.matricola = matricola; 
    }
    
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public boolean isAttivo() {
        return attivo;
    }
    
    public void setAttivo(boolean attivo) {
        this.attivo = attivo; 
    }
    
    @Override
    public String toString() {
        return   "Cognome:" + cognome + "/n " + "Nome:" + nome + " /n" + "Matricola:" + matricola + "/n" + "Email universitaria " + email;
    }
}