/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gems;

/**
 *
 * @author mario
 */
public class Utente {
    private String nome;
    private String cognome;
    private String matricola;
    private String mail;
    private boolean attivo;

    public Utente(String nome, String cognome, String matricola, String mail) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.mail = mail;
        this.attivo = true;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getMatricola() { return matricola; }
    public void setMatricola(String matricola) { this.matricola = matricola; }
    
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    
    public boolean getAttivo() { return attivo;}
    public void attiva() {this.attivo = true;}
    public void disattiva() {this.attivo = false;}
    @Override
    public String toString() {
        return cognome + " " + nome + " - " + matricola + " - " + mail;
    }
}