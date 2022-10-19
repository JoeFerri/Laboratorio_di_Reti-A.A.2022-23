/**
 * Copyright (C) 2022 Giuseppe Ferri
 * 
 * Assignment_3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Assignment_3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */
package assignment_3;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import utils.FlagSingleArgumentConsumer;
import utils.Utils;


/**
 * Simulazione dell'uso di un laboratorio di una università.
 * Viene creato un laboratorio contenente 20 computer.
 * Ogni utente può accedere ad un computer facendo richiesta di accesso al tutor.
 * Gli utenti si dividono per ruolo e priorità.
 * Gli studenti (priorità 0) necessitano di 1 computer qualsiasi.
 * I tesisti (priorità 1) necessitano del computer specifico sul quale è installato il proprio software.
 * I professori (priorità 2) necessitano di tutti i computer.
 * 
 * Il programma istanzia il laboratorio e il tutor, quindi istanzia
 * una lista di utenti il cui numero è parametrizzato dagli argomenti passati al programma:
 * 
 * $ java assignment_3.Assignment <studenti_size> <tesisti_size> <professori_size>
 * 
 * Gli argomenti sono opzionali ma devono rispettare l'ordine posizionale.
 * Il programma accetta anche parametri extra:
 * 
 * --table            per stampare a video le code degli utenti attivi o in attesa
 * --debug            per abilitare la stampa verbosa di debugging
 * --stdout-to-file   per ridirigere lo stdout su file (Assignment.stdout.log)
 * --stderr-to-file   per ridirigere lo stderr su file (Assignment.stderr.log)
 * 
 * Ogni utente può accedere più volte ai computer (cicli), ma ogni accesso
 * viene elaborato come richiesta indipendente, e messa in coda alle altre.
 * Uno schedulatore si occupa di attivare a intervalli casuali di tempo
 * gli utenti utilizzando una struttura dati SchedulazioneUtente.
 * Ogni schedulazione attiva il rispettivo utente.
 * L'utente fa una richiesta di accesso al tutor (bloccante), il quale
 * fornisce un id computer appena possibile (in base ai vincoli dell'utente
 * e alla posizione nella coda delle richieste).
 * L'utente usa il computer[id], quindi richiede al tutor di rilasciare
 * l'accesso al computer, poi termina, oppure viene rischedulato per un altro ciclo.
 * 
 * AVVIO:
 * $ cd <<path>>/build
 * $ java assignment_3.Assignment
 * 
 * esempi:
 * $ java -ea assignment_3.Assignment                               assert abilitati
 * $ java assignment_3.Assignment 1 2 3                             1 studente, 2 tesisti, 3 professori
 * $ java assignment_3.Assignment 1 0 0 --table --debug             stampe debug e tabelle su stdout
 * $ java assignment_3.Assignment --debug 1 --stderr-to-file 2 3    1 studente, 2 tesisti, 3 professori, stampe debug e ridirezione stderr su file
 */
public class Assignment { 

  // uso ripetuto dei computer da parte degli utenti
  public static final int CICLI_MIN = 1;
  public static final int CICLI_MAX = 3;
  
  public static final int STUDENTI_SIZE   = 15;
  public static final int TESISTI_SIZE    = 6;
  public static final int PROFESSORI_SIZE = 2;

  // periodi di attesa tra la schedulazione delle richieste di accesso
  public static final long INITIAL_DELAY_MIN   = 500;
  public static final long INITIAL_DELAY_MAX   = 1000;
  public static final long PERIODIC_DELAY_MIN  = 1000;
  public static final long PERIODIC_DELAY_MAX  = 2000;

  // flags per gli argomenti passati al programma
  static enum Flags {
    TABELLE_PRINT(0),
    DEBUG(1),
    STDOUT_TO_FILE(2),
    STDERR_TO_FILE(3);

    final int index;
    Flags (int index) {
      this.index = index;
    }

    public static int size () {
      return 4;
    }
  }

  public static void main(String[] args) throws IOException {
    
    // ---------------------------------------------------------------
    // - inizializzazione parametri e flags
    // ---------------------------------------------------------------
    boolean[] arguments = new boolean[Flags.size()];
    arguments[Flags.TABELLE_PRINT.index]  = false;
    arguments[Flags.DEBUG.index]          = false;
    arguments[Flags.STDOUT_TO_FILE.index] = false;
    arguments[Flags.STDERR_TO_FILE.index] = false;

    args = Utils.flagsSingleArgumentsParseAndSet(
      args,
      new FlagSingleArgumentConsumer("--table", () -> {arguments[Flags.TABELLE_PRINT.index] = true;}),
      new FlagSingleArgumentConsumer("--debug", () -> {arguments[Flags.DEBUG.index] = true;}),
      new FlagSingleArgumentConsumer("--stdout-to-file", () -> {arguments[Flags.STDOUT_TO_FILE.index] = true;}),
      new FlagSingleArgumentConsumer("--stderr-to-file", () -> {arguments[Flags.STDERR_TO_FILE.index] = true;})
    );
    Utils.preferences.putBoolean("DEBUG",arguments[Flags.DEBUG.index]);
    Utils.preferences.putBoolean("STDOUT_TO_FILE",arguments[Flags.STDOUT_TO_FILE.index]);
    Utils.preferences.putBoolean("STDERR_TO_FILE",arguments[Flags.STDERR_TO_FILE.index]);
    Utils.init("Assignment");
    // ---------------------------------------------------------------

    // numero utenti
    int studentiSize    = args.length > 0 ? Integer.parseInt(args[0]) : STUDENTI_SIZE;
    int tesistiSize     = args.length > 1 ? Integer.parseInt(args[1]) : TESISTI_SIZE;
    int professoriSize  = args.length > 2 ? Integer.parseInt(args[2]) : PROFESSORI_SIZE;
    int utentiSize      = studentiSize + tesistiSize + professoriSize;

    Laboratorio lab = new Laboratorio();
    Tutor tutor = new Tutor(lab,arguments[Flags.TABELLE_PRINT.index]);

    BlockingQueue<Utente> utentiTerminati = new ArrayBlockingQueue<Utente>(utentiSize != 0 ? utentiSize : 1);
    Map<Utente,ScheduledFuture<?>> utentiSchedulati = new HashMap<Utente,ScheduledFuture<?>>(utentiSize);
    ScheduledExecutorService schedulerUtenti = Executors.newScheduledThreadPool(utentiSize);

    // istanziazione utenti
    List<Tesista> tesisti = new ArrayList<Tesista>(tesistiSize);
    List<Utente> utenti = new ArrayList<Utente>(utentiSize);
    utenti.addAll(Studente.factory(studentiSize,CICLI_MIN,CICLI_MAX));
    tesisti.addAll(Tesista.factory(tesistiSize,CICLI_MIN,CICLI_MAX));
    utenti.addAll(tesisti);
    utenti.addAll(Professore.factory(professoriSize,CICLI_MIN,CICLI_MAX));
    // gli utenti arrivano disordinatamente
    Collections.shuffle(utenti);
    
    utenti.stream()
      .forEach(utente -> utente.setTutor(tutor));
    
    tesisti.stream()
      .forEach(tesista -> tesista.setComputerId(lab.installaSoftware(tesista.getSoftware())));
    
    // inizializzazione schedulatore e lista schedulazioni (Futures)
    for (Utente utente : utenti) { 
      utentiSchedulati.put(
        utente,
        schedulerUtenti
          .scheduleWithFixedDelay(new SchedulazioneUtente(utente,utentiTerminati),
                                    Utils.getRndm(INITIAL_DELAY_MIN,INITIAL_DELAY_MAX),
                                    Utils.getRndm(PERIODIC_DELAY_MIN,PERIODIC_DELAY_MAX),
                                      TimeUnit.MILLISECONDS)
      );
    }
    
    // Il Thread main rimuove una alla volta le schedulazioni terminate 
    while (utentiSchedulati.size() > 0) {
      Utils.tprintflushDebug("Elaborazione schedulazioni.\n");
      Utils.tprintflushDebug("\n\nUtenti schedulati: %s\n\n",utentiSchedulati);
      try {
        Utente utenteTerminato = utentiTerminati.take();
        assert utenteTerminato != null : "Errore nella rimozione dell'utente terminato (ritornato null)!"; //!
        Utils.tprintflushDebug("%s rimosso dalla coda dei terminati.\n",utenteTerminato);
        ScheduledFuture<?> future = utentiSchedulati.remove(utenteTerminato);
        assert future != null : utenteTerminato + " non è presente nella mappa!"; //!
        future.cancel(true);
      } catch (InterruptedException e) {
        Utils.printflushCurrThread(e);
      }
    }
    // chiusura dello schedulatore e terminazione del programma
    assert utentiSchedulati.size() == 0 : "Non sono state terminate tutte le schedulazioni!"; //!
    Utils.tprintflushDebug("Elaborazione schedulazioni terminata.\n");
    schedulerUtenti.shutdown();
    Utils.tprintflushDebug("schedulerUtenti in attesa di terminazione...\n");
    try {
      schedulerUtenti.awaitTermination(2,TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Utils.printflushCurrThread(e);
    }
    Utils.tprintflushDebug("schedulerUtenti terminazione forzata...\n");
    schedulerUtenti.shutdownNow();

    Utils.tprintflushDebug("Assignment_3 terminato.\n");
    
    Utils.printflush("\n\n");
  }

}
