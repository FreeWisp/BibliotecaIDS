/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gems;

/**
 *
 * @author mario
 */
import java.time.LocalDate;

public class Prestito {
    private Libro libro;
    private Utente utente;
    private LocalDate dataPrestito;
    private LocalDate dataPrevistaRestituzione;
    private LocalDate dataRestituzione; // null = non restituito

    public Prestito(Libro libro, Utente utente, LocalDate dataPrestito, LocalDate dataPrevista) {
        this.libro = libro;
        this.utente = utente;
        this.dataPrestito = dataPrestito;
        this.dataPrevistaRestituzione = dataPrevista;
    }

    public boolean isAttivo() {
        return dataRestituzione == null;
    }

    public boolean isInRitardo() {
        return isAttivo() && LocalDate.now().isAfter(dataPrevistaRestituzione);
    }

    public void registraRestituzione() {
        this.dataRestituzione = LocalDate.now();
    }

    public Libro getLibro() { return libro; }
    public Utente getUtente() { return utente; }
    public LocalDate getDataPrevistaRestituzione() { return dataPrevistaRestituzione; }

    @Override
    public String toString() {
        return libro.getTitolo() + " -> " + utente.getCognome() +
               " (rest. prevista: " + dataPrevistaRestituzione + ")" +
               (isInRitardo() ? " **IN RITARDO**" : "");
    }
}