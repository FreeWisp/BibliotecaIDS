/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cupos
 */
package it.unisa.biblioteca.servizi;

import com.google.gson.*;
import it.unisa.biblioteca.model.*;
import it.unisa.biblioteca.repository.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.nio.charset.StandardCharsets; //per mario linux :)

public class ServizioArchivio {

    private final LibroRepository libroRepo;
    private final UtenteRepository utenteRepo;
    private final PrestitoRepository prestitoRepo;

    public ServizioArchivio(Biblioteca biblioteca) {
        this.libroRepo = biblioteca.getLibroRepository();
        this.utenteRepo = biblioteca.getUtenteRepository();
        this.prestitoRepo = biblioteca.getPrestitoRepository();
    }

    /**
     * Adapter per serializzare e deserializzare LocalDate
     */
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Salva l'intero archivio su file JSON
     */
    public void salva(String file) throws IOException {
        ArchivioData data = new ArchivioData(
                libroRepo.listaCompleta(),
                utenteRepo.listaCompleta(),
                prestitoRepo.listaCompleta()
        );

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            gson().toJson(data, writer);
        }
    }

    /**
     * Carica l'intero archivio da file JSON
     */
    public ArchivioData carica(String file) throws IOException {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return gson().fromJson(reader, ArchivioData.class);
        }
    }

    /**
     * Aggiorna le repository della biblioteca con i dati caricati
     */
    public void aggiornaBiblioteca(ArchivioData dati) {
        libroRepo.svuota();
        utenteRepo.svuota();
        prestitoRepo.svuota();

        for (Libro l : dati.libri) libroRepo.inserisciLibro(l);
        for (Utente u : dati.utenti) utenteRepo.inserisciUtente(u);
        for (Prestito p : dati.prestiti) prestitoRepo.inserisciPrestito(p);
    }

    /**
     * Wrapper dei dati dell'archivio
     */
    public static class ArchivioData {
        public List<Libro> libri;
        public List<Utente> utenti;
        public List<Prestito> prestiti;

        public ArchivioData(List<Libro> libri, List<Utente> utenti, List<Prestito> prestiti) {
            this.libri = libri;
            this.utenti = utenti;
            this.prestiti = prestiti;
        }
    }
}

