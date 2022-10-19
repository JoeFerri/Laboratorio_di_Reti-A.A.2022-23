/**
 * Copyright (C) 2022 Giuseppe Ferri
 * 
 * Assignment_4 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Assignment_4 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */
package assignment_4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import utils.FlagSingleArgumentConsumer;
import utils.Utils;


/**
 * Il programma prende una lista di path di file di testo separate da spazi,
 * e conta le occorrenze delle lettere in ogni file.
 * <p>
 * Il formato di codifica dei file viene assunto essere UTF-8
 * a meno del passaggio di uno specifico parametro di codifica.
 * <p>
 * Alla fine dell'elaborazione crea un file "report.txt"
 * con il formato:
 * <pre>{@code
<a>,<numero di occorrenze>\n
<b>,<numero di occorrenze>\n
              ...         \n
<z>,<numero di occorrenze>
 * }</pre>
 * Nel caso ci sia un errore in uno o più file, verrà comunque
 * generato il file "report.txt" che conterrà soltanto il valore -1
 * per tutte le lettere.
 * <p>
 * Il programma accetta anche i seguenti parametri extra:
 * <pre>{@code
--debug                 // Per abilitare la stampa verbosa di debugging
                        // e il numero di occorrenze per ogni singolo file
--test-all-text-txt     // Sono ignorati gli argomenti passati e
                        // vengono elaborati tutti i file contenuti nella cartella assignment_4/data
--normalize             // I caratteri vengono normalizzati.
                        // Dopo la normalizzazione 'a' == 'A' == 'à' == 'á'
                        // Senza la normalizzazione (default) 'a' == 'A', ma 'à' viene ignorato

// i seguenti parametri impostano la codifica dei file:
--iso-8859-1
--us-ascii
--utf-16
 * }</pre>
 * IMPLEMENTAZIONE:
 * <p>
 * Viene creato un pool di {@link CharsReader}, ognuno dei quali (Thread)
 * si occupa di elaborare un singolo file e di aggiornare il report totale.
 * La classe {@link FilesReport} continene sia il report totale che
 * i rispettivi singoli report dei file.
 * <p>
 * AVVIO:
 * <pre>{@code
$ cd <<path>>/bin
$ java assignment_4.Assignment <path_1> ... <path_n> [--debug] [--test-all-text-txt] [--normalize] [...]
 * }</pre>
 * ESEMPI:
 * <pre>{@code
$ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_1.txt
$ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_1.txt --utf-16 --normalize
$ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_1.txt --debug
$ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_1.txt --normalize --debug
$ java assignment_4.Assignment --test-all-text-txt
 * }</pre>
 */
public class Assignment {

  public static void main(String[] args) throws IOException {
    
    // ---------------------------------------------------------------
    // - inizializzazione parametri e flags
    // ---------------------------------------------------------------
    boolean[] arguments = new boolean[Flags.size()];
    arguments[Flags.DEBUG.index]              = false;
    arguments[Flags.TEST_ALL_TEXT_TXT.index]  = false;
    arguments[Flags.NORMALIZE.index]          = false;
    arguments[Flags.ISO_8859_1.index]         = false;
    arguments[Flags.US_ASCII.index]           = false;
    arguments[Flags.UTF_16.index]             = false;

    args = Utils.flagsSingleArgumentsParseAndSet(
      args,
      new FlagSingleArgumentConsumer("--debug",             () -> {arguments[Flags.DEBUG.index] = true;}),
      new FlagSingleArgumentConsumer("--test-all-text-txt", () -> {arguments[Flags.TEST_ALL_TEXT_TXT.index] = true;}),
      new FlagSingleArgumentConsumer("--normalize",         () -> {arguments[Flags.NORMALIZE.index] = true;}),
      new FlagSingleArgumentConsumer("--iso-8859-1",        () -> {arguments[Flags.ISO_8859_1.index] = true;}),
      new FlagSingleArgumentConsumer("--us-ascii",          () -> {arguments[Flags.US_ASCII.index] = true;}),
      new FlagSingleArgumentConsumer("--utf-16",            () -> {arguments[Flags.UTF_16.index] = true;})
    );
    Utils.preferences.putBoolean("DEBUG",arguments[Flags.DEBUG.index]);
    Utils.init("Assignment");
    // ---------------------------------------------------------------


    String[] fileNames = null;
    FilesReport filesReport = null;
    List<Future<Boolean>> futures = null;
    String pathOutput = "report.txt";

    fileNames = args;

    // parametri sulla codifica dei file
    Charset charset = StandardCharsets.UTF_8;
    if (arguments[Flags.ISO_8859_1.index])
      charset = StandardCharsets.ISO_8859_1;
    else if (arguments[Flags.US_ASCII.index])
      charset = StandardCharsets.US_ASCII;
    else if (arguments[Flags.UTF_16.index])
      charset = StandardCharsets.UTF_16;

    // ----------------------- test
    if (arguments[Flags.TEST_ALL_TEXT_TXT.index]) {
      fileNames = new String[25];
      for (int i = 1; i <= 25; i++)
        fileNames[i-1] = "./../data/text_" + i + ".txt";
    }
    // ----------------------------

    if (fileNames.length == 0) {
      System.err.printf("\nNessun percorso file passato come argomento!\n");
      System.err.printf("$ java assignment_4.Assignment <path_1> <path_2> ... <path_n> [param_1] ... [param_m]\n\n");
      System.err.printf("params:\n");
      System.err.printf("--debug ; --test-all-text-txt ; --normalize ; --iso-8859-1 | --us-ascii | --utf-16\n\n");      
    }
    else {
      futures = new ArrayList<>(fileNames.length);
      filesReport = new FilesReport(arguments,fileNames);

      ExecutorService service = Executors.newFixedThreadPool(fileNames.length);
      for (String fileName : fileNames)
        futures.add(service.submit(new CharsReader(fileName,charset,filesReport)));

      for(Future<?> future : futures)
        try {
          future.get();
        }
        catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }

      Utils.serviceShutdown(service);
      
      filesReport.toFile(pathOutput);

      Utils.printflushDebug("\n%s\n",filesReport);
    }
  }
}
