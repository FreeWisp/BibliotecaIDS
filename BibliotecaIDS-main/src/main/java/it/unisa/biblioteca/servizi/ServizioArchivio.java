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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unisa.biblioteca.model.*;
import it.unisa.biblioteca.repository.*;

import java.io.FileWriter;
import java.io.FileReader;
import java.util.List;

public class ServizioArchivio {

    private LibroRepository libroRepo;
    private UtenteRepository utenteRepo;
    private PrestitoRepository prestitoRepo;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ServizioArchivio(LibroRepository l, UtenteRepository u, PrestitoRepository p) {
        this.libroRepo = l;
        this.utenteRepo = u;
        this.prestitoRepo = p;
    }

    public void salva(String file) throws Exception {
        ArchivioData data = new ArchivioData(
                libroRepo. listaOrdinataPerTitolo(),
                utenteRepo.listaOrdinataPerCognomeNome(),
                prestitoRepo.cercaPrestitiAttivi()
        );

        try (FileWriter w = new FileWriter(file)) {
            gson.toJson(data, w);
        }
    }

    public ArchivioData carica(String file) throws Exception {
        try (FileReader r = new FileReader(file)) {
            return gson.fromJson(r, ArchivioData.class);
        }
    }

    public static class ArchivioData {
        public List<Libro> libri;
        public List<Utente> utenti;
        public List<Prestito> prestiti;

        public ArchivioData(List<Libro> l, List<Utente> u, List<Prestito> p) {
            this.libri = l;
            this.utenti = u;
            this.prestiti = p;
        }
    }
}

