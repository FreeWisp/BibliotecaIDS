📚 Sistema Gestione Biblioteca
Sistema software per la gestione di una biblioteca universitaria sviluppato in Java con interfaccia grafica JavaFX.

📋 Indice
Descrizione
Funzionalità
Tecnologie Utilizzate
Prerequisiti
Installazione
Utilizzo
Architettura
Documentazione
Autori
Licenza
📖 Descrizione
Sistema di gestione biblioteca che permette di:

Gestire il catalogo dei libri
Registrare e gestire gli utenti
Gestire prestiti e restituzioni
Verificare disponibilità delle copie
Controllare prestiti in ritardo
Il sistema è stato progettato seguendo i principi di architettura a livelli (Repository Pattern e Service Layer) per garantire modularità e manutenibilità del codice.

✨ Funzionalità
📚 Gestione Libri
Aggiunta di nuovi libri al catalogo
Modifica informazioni libri esistenti
Eliminazione libri
Ricerca libri per titolo, autore o ISBN
Visualizzazione lista libri ordinata alfabeticamente
👥 Gestione Utenti
Registrazione nuovi utenti
Modifica dati utente
Attivazione/Disattivazione utenti
Ricerca utenti per cognome o matricola
Controllo unicità matricola
📖 Gestione Prestiti
Registrazione nuovo prestito
Registrazione restituzione
Visualizzazione prestiti attivi
Visualizzazione prestiti in ritardo
Controllo disponibilità copie
Limite massimo 3 prestiti per utente
Durata prestito personalizzabile (giorni)
🛠️ Tecnologie Utilizzate
Java 17 - Linguaggio di programmazione
JavaFX 21.0.1 - Framework per interfaccia grafica
Maven - Build automation e gestione dipendenze
Gson 2.10.1 - Libreria per gestione JSON
Doxygen - Generazione documentazione
Git/GitHub - Controllo versione
📦 Prerequisiti
JDK 17 o superiore
Maven 3.6+
💻 Utilizzo
Avvio dell'applicazione
Al primo avvio, si apre la dashboard principale con tre sezioni:

Gestione Libri - Click sul bottone blu per gestire il catalogo
Gestione Utenti - Click sul bottone viola per gestire gli utenti
Gestione Prestiti - Click sul bottone rosso per gestire prestiti
Esempi d'uso
Aggiungere un libro
Apri "Gestione Libri"
Compila il form a destra:
Titolo: "Il Signore degli Anelli"
Autore: "J.R.R. Tolkien"
ISBN: "978-8845292613"
Anno: 1954
Copie: 3
Click su "Salva"
Registrare un prestito
Apri "Gestione Prestiti"
Seleziona un libro dal menu a tendina
Seleziona un utente
Inserisci la durata in giorni (es. 14)
Click su "Registra Prestito"
Registrare una restituzione
Apri "Gestione Prestiti"
Seleziona un prestito attivo dalla tabella
Click su "Registra Restituzione"
Struttura del progetto
gestionebiblioteca
├── Consegne/
│   ├── diagrammiSequenza/
│   ├── diagrammaClassi.puml
│   ├── Documento di specifica dei requisiti software.pdf
│   └── Documentazione diagrammi.pdf
│
├── biblioteca/
│   └── biblioteca.json
│
├── docs/
│   └── html/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── it/unisa/biblioteca/
│   │   │       ├── app/
│   │   │       │   └── Main.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── MainController.java
│   │   │       │   ├── LibriController.java
│   │   │       │   ├── UtentiController.java
│   │   │       │   └── PrestitiController.java
│   │   │       │   └── UtentiConPrestitiController.java
│   │   │       ├── model/
│   │   │       │   ├── Biblioteca.java
│   │   │       │   ├── Libro.java
│   │   │       │   ├── Utente.java
│   │   │       │   └── Prestito.java
│   │   │       │   └── UtenteConPrestiti.java   
│   │   │       ├── repository/
│   │   │       │   ├── LibroRepository.java
│   │   │       │   ├── UtenteRepository.java
│   │   │       │   ├── PrestitoRepository.java
│   │   │       │   └── inmemoria/
│   │   │       │       ├── InMemoriaLibroRepository.java
│   │   │       │       ├── InMemoriaUtenteRepository.java
│   │   │       │       └── InMemoriaPrestitoRepository.java
│   │   │       │
│   │   │       └── servizi/
│   │   │           ├── ServizioGestioneLibri.java
│   │   │           ├── ServizioGestioneUtenti.java
│   │   │           ├── ServizioPrestiti.java
│   │   │           └── ServizioArchivio.java
│   │   │
│   │   └── resources/
│   │       └── fxml/
│   │           ├── MainView.fxml
│   │           ├── LibriView.fxml
│   │           ├── UtentiView.fxml
│   │           └── PrestitiView.fxml
│   │
│   └── test/
│       └── java/
│           └── it/unisa/biblioteca/
│               ├── controller/
│               │   ├── LibriControllerTest.java
│               │   ├── PrestitiControllerTest.java
│               │   ├── UtentiControllerTest.java
│               │   └── UtentiConPrestitiControllerTest.java
│               ├── model/
│               │   ├── BibliotecaTest.java
│               │   ├── LibroTest.java
│               │   ├── PrestitoTest.java
│               │   ├── UtenteTest.java
│               │   └── UtenteConPrestitiTest.java  
│               └── repository/
│                   └── inmemoria/
│                       ├── InMemoriaLibroRepositoryTest.java
│                       ├── InMemoriaUtenteRepositoryTest.java
│                       └── InMemoriaPrestitoRepositoryTest.java
│
├── target/
│   ├── classes/
│   ├── test-classes/
│   ├── generated-sources/
│   ├── generated-test-sources/
│   ├── surefire-reports/
│   └── maven-status/
│
├── README.md
├── Doxyfile
├── pom.xml
├── .gitignore
├── nbactions.xml
└── nb-configuration.xml
