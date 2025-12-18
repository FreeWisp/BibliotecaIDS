/**
 * @file ServizioArchivio.java
 * @brief Servizio per la persistenza su file dell'archivio della biblioteca
 * @author Marion
 * @date 2025-12-15
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
import java.nio.charset.StandardCharsets;

/**
 * @class ServizioArchivio
 * @brief Servizio che gestisce il salvataggio e caricamento dei dati su file
 * 
 * Questa classe implementa la logica per la persistenza dell'intero archivio
 * della biblioteca su file JSON. Si occupa della serializzazione e
 * deserializzazione di libri, utenti e prestiti, gestendo anche la
 * conversione delle date LocalDate in formato JSON.
 */
public class ServizioArchivio {

    /**
     * @brief Repository per la gestione dei libri
     */
    private final LibroRepository libroRepo;
    
    /**
     * @brief Repository per la gestione degli utenti
     */
    private final UtenteRepository utenteRepo;
    
    /**
     * @brief Repository per la gestione dei prestiti
     */
    private final PrestitoRepository prestitoRepo;

    /**
     * @brief Costruttore del servizio archivio
     * 
     * Inizializza il servizio estraendo i repository dalla biblioteca fornita.
     * Questo servizio coordina il salvataggio e caricamento di tutti i dati
     * della biblioteca.
     * 
     * @param biblioteca L'istanza della biblioteca da cui estrarre i repository
     */
    public ServizioArchivio(Biblioteca biblioteca) {
        this.libroRepo = biblioteca.getLibroRepository();
        this.utenteRepo = biblioteca.getUtenteRepository();
        this.prestitoRepo = biblioteca.getPrestitoRepository();
    }

    /**
     * @class LocalDateAdapter
     * @brief Adapter per la serializzazione/deserializzazione di LocalDate
     * 
     * Classe interna che implementa la conversione tra LocalDate e JSON,
     * necessaria perché Gson non gestisce nativamente il tipo LocalDate
     * di Java 8+. Utilizza il formato ISO standard (yyyy-MM-dd).
     */
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        /**
         * @brief Formatter per le date in formato ISO (yyyy-MM-dd)
         */
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        /**
         * @brief Serializza una LocalDate in formato JSON
         * 
         * Converte un oggetto LocalDate in una stringa JSON utilizzando
         * il formato ISO standard.
         * 
         * @param date La data da serializzare
         * @param typeOfSrc Il tipo dell'oggetto sorgente
         * @param context Il contesto di serializzazione Gson
         * @return Un JsonElement contenente la data formattata
         */
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        /**
         * @brief Deserializza una stringa JSON in LocalDate
         * 
         * Converte una stringa JSON in formato ISO in un oggetto LocalDate.
         * 
         * @param json L'elemento JSON da deserializzare
         * @param typeOfT Il tipo dell'oggetto target
         * @param context Il contesto di deserializzazione Gson
         * @return Un oggetto LocalDate
         * @throws JsonParseException Se la stringa non è una data valida
         */
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    /**
     * @brief Crea un'istanza di Gson configurata per il progetto
     * 
     * Configura Gson con l'adapter per LocalDate e il pretty printing
     * per rendere il file JSON più leggibile.
     * 
     * @return Un'istanza di Gson configurata
     */
    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * @brief Salva l'intero archivio della biblioteca su file JSON
     * 
     * Serializza tutti i dati della biblioteca (libri, utenti, prestiti)
     * in un singolo file JSON. Utilizza UTF-8 come encoding per garantire
     * la corretta gestione dei caratteri speciali.
     * 
     * @param file Il percorso del file in cui salvare l'archivio
     * @throws IOException Se si verifica un errore durante la scrittura del file
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
     * @brief Carica l'intero archivio della biblioteca da file JSON
     * 
     * Deserializza i dati dal file JSON in oggetti Java.
     * Utilizza UTF-8 come encoding per garantire la corretta
     * lettura dei caratteri speciali.
     * 
     * @param file Il percorso del file da cui caricare l'archivio
     * @return Un oggetto ArchivioData contenente tutti i dati caricati
     * @throws IOException Se si verifica un errore durante la lettura del file
     */
    public ArchivioData carica(String file) throws IOException {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return gson().fromJson(reader, ArchivioData.class);
        }
    }

    /**
     * @brief Aggiorna i repository della biblioteca con i dati caricati
     * 
     * Svuota tutti i repository esistenti e li riempie con i dati
     * caricati dal file. Questa operazione sostituisce completamente
     * i dati in memoria con quelli del file.
     * 
     * @param dati L'oggetto ArchivioData contenente i dati da caricare nei repository
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
     * @class ArchivioData
     * @brief Classe wrapper per i dati dell'archivio
     * 
     * Classe interna statica che raggruppa tutti i dati della biblioteca
     * (libri, utenti, prestiti) in un'unica struttura facilmente
     * serializzabile in JSON.
     */
    public static class ArchivioData {
        /**
         * @brief Lista di tutti i libri della biblioteca
         */
        public List<Libro> libri;
        
        /**
         * @brief Lista di tutti gli utenti della biblioteca
         */
        public List<Utente> utenti;
        
        /**
         * @brief Lista di tutti i prestiti attivi della biblioteca
         */
        public List<Prestito> prestiti;

        /**
         * @brief Costruttore della classe ArchivioData
         * 
         * Crea un nuovo oggetto ArchivioData con le liste fornite.
         * 
         * @param libri Lista dei libri da includere nell'archivio
         * @param utenti Lista degli utenti da includere nell'archivio
         * @param prestiti Lista dei prestiti da includere nell'archivio
         */
        public ArchivioData(List<Libro> libri, List<Utente> utenti, List<Prestito> prestiti) {
            this.libri = libri;
            this.utenti = utenti;
            this.prestiti = prestiti;
        }
    }
}