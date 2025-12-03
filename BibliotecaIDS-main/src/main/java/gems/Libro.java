/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gems;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class Libro {

    private String titolo;
    private String autore;
    private String isbn;
    private int anno;

    public Libro(String titolo, String autore, String isbn, int anno) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.anno = anno;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getAnno(){
        return anno;
    }
    public void setAnno(int anno){
        this.anno = anno;
    }
    

    @Override
    public String toString() {
        return titolo + " - " + autore + " - " + anno + " (" + isbn + ")";
    }
}
