# 📚 Sistema Gestione Biblioteca

Sistema software per la gestione di una biblioteca universitaria sviluppato in Java con interfaccia grafica JavaFX.

## 📋 Indice

- [Descrizione](#descrizione)
- [Funzionalità](#funzionalità)
- [Tecnologie Utilizzate](#tecnologie-utilizzate)
- [Prerequisiti](#prerequisiti)
- [Installazione](#installazione)
- [Utilizzo](#utilizzo)
- [Architettura](#architettura)
- [Documentazione](#documentazione)
- [Autori](#autori)
- [Licenza](#licenza)

## 📖 Descrizione

Sistema di gestione biblioteca che permette di:
- Gestire il catalogo dei libri
- Registrare e gestire gli utenti
- Gestire prestiti e restituzioni
- Verificare disponibilità delle copie
- Controllare prestiti in ritardo

Il sistema è stato progettato seguendo i principi di architettura a livelli (Repository Pattern e Service Layer) per garantire modularità e manutenibilità del codice.

## ✨ Funzionalità

### 📚 Gestione Libri
- Aggiunta di nuovi libri al catalogo
- Modifica informazioni libri esistenti
- Eliminazione libri
- Ricerca libri per titolo, autore o ISBN
- Visualizzazione lista libri ordinata alfabeticamente

### 👥 Gestione Utenti
- Registrazione nuovi utenti
- Modifica dati utente
- Attivazione/Disattivazione utenti
- Ricerca utenti per cognome o matricola
- Controllo unicità matricola

### 📖 Gestione Prestiti
- Registrazione nuovo prestito
- Registrazione restituzione
- Visualizzazione prestiti attivi
- Visualizzazione prestiti in ritardo
- Controllo disponibilità copie
- Limite massimo 3 prestiti per utente
- Durata prestito personalizzabile (giorni)

## 🛠️ Tecnologie Utilizzate

- **Java 17** - Linguaggio di programmazione
- **JavaFX 21.0.1** - Framework per interfaccia grafica
- **Maven** - Build automation e gestione dipendenze
- **Gson 2.10.1** - Libreria per gestione JSON
- **Doxygen** - Generazione documentazione
- **Git/GitHub** - Controllo versione

## 📦 Prerequisiti

- **JDK 17** o superiore
- **Maven 3.6+**

## 💻 Utilizzo

### Avvio dell'applicazione

Al primo avvio, si apre la dashboard principale con tre sezioni:

1. **Gestione Libri** - Click sul bottone blu per gestire il catalogo
2. **Gestione Utenti** - Click sul bottone viola per gestire gli utenti
3. **Gestione Prestiti** - Click sul bottone rosso per gestire prestiti

### Esempi d'uso

#### Aggiungere un libro

1. Apri "Gestione Libri"
2. Compila il form a destra:
   - Titolo: "Il Signore degli Anelli"
   - Autore: "J.R.R. Tolkien"
   - ISBN: "978-8845292613"
   - Anno: 1954
   - Copie: 3
3. Click su "Salva"

#### Registrare un prestito

1. Apri "Gestione Prestiti"
2. Seleziona un libro dal menu a tendina
3. Seleziona un utente
4. Inserisci la durata in giorni (es. 14)
5. Click su "Registra Prestito"

#### Registrare una restituzione

1. Apri "Gestione Prestiti"
2. Seleziona un prestito attivo dalla tabella
3. Click su "Registra Restituzione"


### Struttura del progetto
```
src/main/java/it/unisa/biblioteca/
├── app/
│   └── Main.java                    # Entry point applicazione
├── controller/
│   ├── MainController.java          # Controller dashboard
│   ├── LibriController.java         # Controller gestione libri
│   ├── UtentiController.java        # Controller gestione utenti
│   └── PrestitiController.java      # Controller gestione prestiti
├── model/
│   ├── Libro.java                   # Entità Libro
│   ├── Utente.java                  # Entità Utente
│   ├── Prestito.java                # Entità Prestito
│   └── Biblioteca.java              # Container principale
├── repository/
│   ├── LibroRepository.java         # Interfaccia repository libri
│   ├── UtenteRepository.java        # Interfaccia repository utenti
│   ├── PrestitoRepository.java      # Interfaccia repository prestiti
│   └── inmemoria/
│       ├── InMemoriaLibroRepository.java
│       ├── InMemoriaUtenteRepository.java
│       └── InMemoriaPrestitoRepository.java
└── servizi/
    ├── ServizioGestioneLibri.java   # Logica business libri
    ├── ServizioGestioneUtenti.java  # Logica business utenti
    ├── ServizioPrestiti.java        # Logica business prestiti
    └── ServizioArchivio.java        # Salvataggio/caricamento dati

src/main/resources/fxml/
├── MainView.fxml                    # Vista dashboard
├── LibriView.fxml                   # Vista gestione libri
├── UtentiView.fxml                  # Vista gestione utenti
└── PrestitiView.fxml                # Vista gestione prestiti
