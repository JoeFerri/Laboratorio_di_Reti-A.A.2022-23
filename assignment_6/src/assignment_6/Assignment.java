/**
 * @author Giuseppe Ferri
 */
package assignment_6;


import java.io.IOException;

import com.gf.utils.FlagSingleArgumentConsumer;
import com.gf.utils.Utils;
import static com.gf.utils.Utils.printflushDebug;

import assignment_6.controller.ClientController;
import assignment_6.controller.StartController;
import assignment_6.model.Flags;
import assignment_6.view.frames.ClientFrame;
import assignment_6.view.frames.StartFrame;


/**
 * Dungeon adventures
 * <p>
 * Il programma è suddiviso in due sotto-processi, un client e un server;
 * <p>
 * il client è rappresentato dal processo principale, si occupa di avviare il server,
 * inizializzare e gestire la finestra grafica ed interagire con il giocatore;
 * <p>
 * il server è un processo avviato internamente dal processo principale, si occupa
 * di interagire con le richieste del client, di simulare le fasi del gioco
 * (combattimento, bevuta pozione, vittoria, pareggio, sconfitta, abbandono),
 * e di gestire il database;
 * <p>
 * il database ha due stati, uno virtuale rappresentato dallo stato della memoria ram
 * durante il gioco, ovvero dallo stato delle istanze Map e List, ed uno fisico
 * rappresentato dai file database.db e logins.db memorizzati su disco;
 * <p>
 * i dati memorizzati su disco del database sono criptati;
 * <p>
 * Il programma accetta anche i seguenti parametri extra:
 * <pre>{@code
--debug                 // Per abilitare la stampa verbosa di debugging
-ea                     // Per abilitare le asserzioni nel processo client
--assert                // Per abilitare le asserzioni nel processo server
 * }</pre>
 * Esecuzione:
 * <pre>{@code
# Windows:
$ java [-ea] -cp ".;./../lib/gf.jar" assignment_6.Assignment [--debug] [--assert] [<porta>:int from 3000 to 4000]
# Unix:
$ java [-ea] -cp ".:./../lib/gf.jar" assignment_6.Assignment [--debug] [--assert] [<porta>:int from 3000 to 4000]
 * }</pre>
 */
public class Assignment {

  /**
   * porta di ascolto di default
   */
  public static final int DEFAULT_DOOR = 3456; // range: 3000 <-> 4000
  
  public static void main(String[] args) throws IOException, InterruptedException {

    printflushDebug("Assignment.main() -> avvio...\n");

    // ---------------------------------------------------------------
    // - inizializzazione parametri e flags
    // ---------------------------------------------------------------
    boolean[] arguments = new boolean[Flags.size()];
    arguments[Flags.DEBUG.index] = false;
    arguments[Flags.ASSERT.index] = false;

    args  = Utils.flagsSingleArgumentsParseAndSet (args,
                                                  new FlagSingleArgumentConsumer("--debug", () -> {arguments[Flags.DEBUG.index]  = true;}),
                                                  new FlagSingleArgumentConsumer("--assert",() -> {arguments[Flags.ASSERT.index] = true;})
            );
    Utils.preferences.putBoolean("DEBUG",arguments[Flags.DEBUG.index]);
    Utils.init("Assignment");
    // ---------------------------------------------------------------


    
    if (args.length == 0) {
      System.err.print("\nNon è stato passato il numero di porta!\n");
      System.err.printf("Verrà utilizzata la porta di default %d\n\n",DEFAULT_DOOR);
      System.err.print("Windows:\n");
      System.err.printf("$ java [-ea] -cp \".;./../lib/gf.jar\" assignment_6.Assignment [--debug] [--assert] [<porta>:int from %d to %d]\n",
        Server.PORT_MIN,Server.PORT_MAX);
      System.err.print("Unix:\n");
      System.err.printf("$ java [-ea] -cp \".:./../lib/gf.jar\" assignment_6.Assignment [--debug] [--assert] [<porta>:int from %d to %d]\n",
        Server.PORT_MIN,Server.PORT_MAX);
      args = new String[] {"" + DEFAULT_DOOR};
    }

    int port = -1;
    
    try { port = Integer.parseInt(args[0]); }
    catch (NumberFormatException e) {
      System.err.print("Deve essere passato come argomento un valore di porta valido!\n");
      System.err.printf("%s non è un intero valido!\n",args[0]);
      System.exit(-1);
    }

    String debugParam = arguments[Flags.DEBUG.index] == true ? " --debug" : "";
    String assertParam = arguments[Flags.ASSERT.index] == true ? " -ea" : "";
    String winCommand  = "java" + assertParam + " -cp \".;./../lib/gf.jar\" assignment_6.Server " + port + debugParam;
    String unixCommand = "java" + assertParam + " -cp \".:./../lib/gf.jar\" assignment_6.Server " + port + debugParam;
    String command = null;
    Process server = null;
    Runtime rt = Runtime.getRuntime();

    if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
      command = winCommand;
      server = rt.exec(command);
    }
    else {
      command = unixCommand;
      String[] cmd = { "/bin/sh", "-c", command};
      server = rt.exec(cmd);
    }

    printflushDebug("main() di Dungeon adventures avviato\n");
    printflushDebug("Assignment.main() -> pid server: %s\n",server.pid());
    StartFrame startFrame = new StartFrame();
    ClientFrame clientFrame = new ClientFrame();
    clientFrame.setVisible(false);
    
    ClientController clientController = new ClientController(clientFrame, port);
    StartController startController = new StartController(startFrame,port);
    clientController.setStartController(startController);
    startController.setClientController(clientController);
    clientController.setListeners();
    startController.setListeners();

    startFrame.setVisible(true);

    // il server termina autonomamente
    //! server.destroy();
  }
  
}