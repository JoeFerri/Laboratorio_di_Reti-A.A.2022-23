/**
 * @author Giuseppe Ferri
 */
package assignment_6.controller;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

import assignment_6.model.Client;
import assignment_6.model.IdResponse;
import assignment_6.model.KeyText;
import assignment_6.model.LoginRecord;
import assignment_6.model.Texts;
import assignment_6.model.UserRecord;
import assignment_6.view.dialogs.Dialogs;
import assignment_6.view.frames.StartFrame;
import static com.gf.utils.Utils.printflushDebug;


/**
 * Una istanza di StartController si occupa di mettere in connessione
 * lo strato grafico della finestra di partenza, le azioni dell'utente e il ClientController;
 * <p>
 * le azioni riguardano la creazione o ricarica di un profilo
 * attraverso il meccanismo del login.
 */
public class StartController {
  
  private final StartFrame startFrame;
  private ClientController clientController = null;
  private final int port;

  
  public StartController (StartFrame frame, int port) {
    this.startFrame = frame;
    this.port = port;
    printflushDebug("StartController creato.\n");
  }


  public void setClientController (ClientController clientController) {
    this.clientController = clientController;
  }

  
  public void setListeners () {

    printflushDebug("StartController.setListeners()...\n");

    // Login Button
    this.startFrame.getLoginButton().addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e) {
        int option = Dialogs.showLoginDialog (StartController.this.startFrame,Texts.get(KeyText.LOGIN_MESSAGE),Texts.get(KeyText.LOGIN_TITLE),null);

        LoginRecord loginRecord = null;
        UserRecord userRecord = null;

        // NEW LOGIN
        if (option == Dialogs.NEW) {
          printflushDebug("LoginButton.listener -> option: NEW\n");
          loginRecord = Dialogs.showNewLoginDialog(StartController.this.startFrame,Texts.get(KeyText.LOGIN_NEW_TITLE),null);
          if (loginRecord != null) {
            try {
              // deve essere inizializzato dopo
              userRecord = Client.saveLogin(loginRecord,StartController.this.port);
            }
            catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            assert userRecord != null : "Errore server creazione record!";
            printflushDebug("LoginButton.listener -> userRecord: <%s>\n",userRecord);
            if (userRecord.idResponse == IdResponse.LOGIN_EXISTS.index) {
              printflushDebug("LoginButton.listener -> idResponse: LOGIN_EXISTS-%d\n",IdResponse.LOGIN_EXISTS.index);
              Dialogs.showErrorDialog(StartController.this.startFrame,Texts.get(KeyText.LOGIN_ERROR_EXISTING),Texts.get(KeyText.ERROR),null);
            }

            // inizializzazione dei dati utente
            else {
              // scelta eroe
              printflushDebug("LoginButton.listener -> idResponse: OK-%d\n",IdResponse.OK.index);
              String path = null;
              while (path  == null || path.length() == 0) {
                path = Dialogs.showSelectHeroDialog(StartController.this.startFrame,"Scegli il tuo eroe!",null);
              }
              userRecord = UserRecord.changeImagePath(userRecord,path);
              printflushDebug("LoginButton.listener -> userRecord: <%s>\n",userRecord);
              userRecord = Client.initRecord(loginRecord,userRecord,StartController.this.port);
              printflushDebug("LoginButton.listener -> userRecord: <%s>\n",userRecord);
              if (userRecord.idResponse == IdResponse.ERROR.index) {
                printflushDebug("LoginButton.listener -> idResponse: ERROR-%d\n",IdResponse.ERROR.index);
                Dialogs.showErrorDialog(StartController.this.startFrame,Texts.get(KeyText.RECORD_INIT_ERROR),Texts.get(KeyText.ERROR),null);
              }
            }
          }
          else
            printflushDebug("LoginButton.listener -> loginRecord: null\n");
        }

        // LOAD LOGIN
        else if (option == Dialogs.LOAD) {
          printflushDebug("LoginButton.listener -> option: LOAD\n");
          loginRecord = Dialogs.showNewLoginDialog(StartController.this.startFrame,Texts.get(KeyText.LOGIN_LOAD_TITLE),null);
          printflushDebug("LoginButton.listener -> loginRecord: <%s>\n",loginRecord != null ? loginRecord : "null");
          if (loginRecord != null) {
            userRecord = Client.loadLogin(loginRecord,StartController.this.port);
            printflushDebug("LoginButton.listener -> userRecord: <%s>\n",userRecord);
            assert userRecord != null : "Errore server creazione record!";
            if (userRecord.idResponse == IdResponse.LOGIN_NOT_FOUND.index) {
              printflushDebug("LoginButton.listener -> idResponse: LOGIN_NOT_FOUND-%d\n",IdResponse.LOGIN_NOT_FOUND.index);
              Dialogs.showErrorDialog(StartController.this.startFrame,Texts.get(KeyText.LOGIN_ERROR),Texts.get(KeyText.ERROR),null);
            }
            else {
              userRecord = Client.initRecord(loginRecord,userRecord,StartController.this.port);
              printflushDebug("LoginButton.listener -> userRecord: <%s>\n",userRecord);
              if (userRecord.idResponse == IdResponse.ERROR.index) {
                printflushDebug("LoginButton.listener -> idResponse: ERROR-%d\n",IdResponse.ERROR.index);
                Dialogs.showErrorDialog(StartController.this.startFrame,Texts.get(KeyText.RECORD_INIT_ERROR),Texts.get(KeyText.ERROR),null);
              }
            }
          }
        }
        else
          printflushDebug("LoginButton.listener -> loginRecord: null\n");

        // START MATCH
        if (userRecord != null && userRecord.idResponse == IdResponse.OK.index) {
          printflushDebug("LoginButton.listener -> idResponse: OK-%d\n",IdResponse.OK.index);

          StartController.this.clientController.newSession(loginRecord,userRecord);
          StartController.this.startFrame.setVisible(false);
        }

      }
    });

    // --------------------------------------------------------------

    // Exit Buttons
    StartController.this.startFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    StartController.this.startFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        printflushDebug("startFrame:WindowAdapter.windowClosing() -> invio al server idResponse: EXIT-%d\n",IdResponse.EXIT.index);

        try (Socket socket = new Socket("localhost",StartController.this.port);
              Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
        
        // chiedo al server di fermarsi autonomamente
        out.println(IdResponse.EXIT.index + ":");
        } catch (IOException eio) {
          // TODO Auto-generated catch block
          eio.printStackTrace();
        }

        //! @deprecate
        // StartController.this.server.destroy();

        // uscita del programma principale
        System.exit(0);
      }
    });
  }

  public void setStartFrameVisible(boolean b) {
    this.startFrame.setVisible(b);
  }

}
