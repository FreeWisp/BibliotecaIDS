/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.biblioteca.model;

/**
 *
 * @author mario
 */

import java.time.LocalDate;


public class Prestito {
    private String matricolaUtente;
    private String isbnLibro;
    private LocalDate dataRestituzione;

    public Prestito(String matricolaUtente, String isbnLibro, LocalDate dataRestituzione) {
        this.matricolaUtente = matricolaUtente;
        this.isbnLibro = isbnLibro;
        this.dataRestituzione = dataRestituzione;
    }

    public String getMatricolaUtente() { 
        return matricolaUtente; 
    }
    
    public void setMatricolaUtente(String matricolaUtente){
        this.matricolaUtente=matricolaUtente;
        
    }
    public String getIsbnLibro() {
        return isbnLibro; 
    }
    
    public void setIsbnLibro(String isbnLibro){
        this.isbnLibro=isbnLibro;
        
    }
    
    public LocalDate getDataRestituzione() { 
        return dataRestituzione; 
    }
    
    public void setDataRestituzione(LocalDate dataRestituzione){
        this.dataRestituzione=dataRestituzione;
        
    }
    
    @Override
    public String toString() {
        return  "Matricola: " + matricolaUtente + "/n " + "Isbn del libro: " + isbnLibro + "/n" + "Data di restituzione: " + dataRestituzione ;
    }

    public void setDataResituzione(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}