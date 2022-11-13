/**
 * @author Giuseppe Ferri
 */
package assignment_6.controller;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import assignment_6.model.Client;
import assignment_6.model.LoginRecord;
import assignment_6.model.UserRecord;
import assignment_6.view.dialogs.Dialogs;
import assignment_6.view.frames.ClientFrame;
import static com.gf.utils.Utils.printflushDebug;


/**
 * Una istanza di ClientController si occupa di mettere in connessione
 * lo strato grafico della finestra di gioco, le azioni dell'utente e il server;
 * <p>
 * le azioni riguardano il combattimento, la bevuta pozione, e l'abbandono.
 */
public class ClientController {
  
  private StartController startController = null;
  private final ClientFrame clientFrame;
  private LoginRecord loginRecord = null;
  private final int port;
  private boolean windowClosingSetted = false;

  
  public ClientController (ClientFrame clientFrame, int port) {
    this.clientFrame = clientFrame;
    this.port = port;
    printflushDebug("ClientController creato.\n");
  }

  public void setStartController (StartController startController) {
    this.startController = startController;
  }


  private <E> void closing(E e) {
    int option = Dialogs.showYesNoDialog (ClientController.this.clientFrame,"Vuoi davvero abbandonare?","",null);
    if (option == JOptionPane.YES_OPTION) {
      UserRecord userRecord = ClientController.this.clientFrame.getUserRecord();
      LoginRecord loginRecord = ClientController.this.loginRecord;
      if (userRecord != null) {
        printflushDebug("ClientController.closing() -> userRecord: <%s>\n",userRecord);
        userRecord = Client.abandons(loginRecord,userRecord,ClientController.this.port);
        ClientController.this.clientFrame.set(userRecord);
        Dialogs.showInfoDialog(ClientController.this.clientFrame,"Hai perso!","",null);
        ClientController.this.clientFrame.setVisible(false);
      }

      ClientController.this.startController.setStartFrameVisible(true);
    }
  }


  public void setListeners () {

    printflushDebug("ClientController.setListeners()...\n");


    if (this.clientFrame.getFightButton() != null) {
      // combattimento
      this.clientFrame.getFightButton().addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
          printflushDebug("ClientController.listener -> FightButton\n");
          UserRecord userRecord = ClientController.this.clientFrame.getUserRecord();
          LoginRecord loginRecord = ClientController.this.loginRecord;
          if (userRecord != null) {
            userRecord = Client.fight(loginRecord,userRecord,ClientController.this.port);
            printflushDebug("ClientController.listener -> FightButton -> userRecord: <%s>\n",userRecord);
            ClientController.this.clientFrame.set(userRecord);
            ClientController.this.setListeners();
            if (userRecord.player.getHealth() == 0) {
              if (userRecord.monster.getHealth() == 0) {
                // pareggio
                printflushDebug("ClientController.listener -> FightButton -> PAREGGIO\n");
                int option = Dialogs.showYesNoDialog (ClientController.this.clientFrame,"PAREGGIO!\nNuova Partita?","",null);
                if (option == JOptionPane.YES_OPTION) {
                  userRecord = Client.initRecord(loginRecord,userRecord,ClientController.this.port);
                  printflushDebug("ClientController.listener -> FightButton -> initRecord: <%s>\n",userRecord);
                  ClientController.this.clientFrame.set(userRecord);
                  ClientController.this.setListeners();
                } else {
                  ClientController.this.clientFrame.setVisible(false);
                  ClientController.this.startController.setStartFrameVisible(true);
                }
              }
              else { // hai perso
                printflushDebug("ClientController.listener -> FightButton -> SCONFITTA\n");
                Dialogs.showInfoDialog(ClientController.this.clientFrame,"HAI PERSO!","",null);
                ClientController.this.clientFrame.setVisible(false);
                ClientController.this.startController.setStartFrameVisible(true);
              }
            }
            else if (userRecord.monster.getHealth() == 0) {
              // hai vinto
              printflushDebug("ClientController.listener -> FightButton -> VITTORIA\n");
              int option = Dialogs.showYesNoDialog (ClientController.this.clientFrame,"HAI VINTO!\nNuova Partita?","",null);
              if (option == JOptionPane.YES_OPTION) {
                userRecord = Client.initRecord(loginRecord,userRecord,ClientController.this.port);
                printflushDebug("ClientController.listener -> FightButton -> initRecord: <%s>\n",userRecord);
                ClientController.this.clientFrame.set(userRecord);
                ClientController.this.setListeners();
              } else {
                userRecord = UserRecord.changeHealthPlayer(userRecord,0);
                userRecord = Client.initRecord(loginRecord,userRecord,ClientController.this.port);
                printflushDebug("ClientController.listener -> FightButton -> initRecord: <%s>\n",userRecord);
                ClientController.this.clientFrame.setVisible(false);
                ClientController.this.startController.setStartFrameVisible(true);
              }
            }
          }
        }
      });
    }

    if (this.clientFrame.getDrinkPotionButton() != null) {
      // bevi pozione
      this.clientFrame.getDrinkPotionButton().addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
          printflushDebug("ClientController.listener -> DrinkPotionButton\n");
          UserRecord userRecord = ClientController.this.clientFrame.getUserRecord();
          LoginRecord loginRecord = ClientController.this.loginRecord;
          if (userRecord != null) {
            userRecord = Client.drinkPotion(loginRecord,userRecord,ClientController.this.port);
            printflushDebug("ClientController.listener -> DrinkPotionButton -> userRecord: <%s>\n",userRecord);
            ClientController.this.clientFrame.set(userRecord);
            ClientController.this.setListeners();
          }
        }
      });
    }

    if (this.clientFrame.getAbandonsButton() != null) {
      // abbandona
      this.clientFrame.getAbandonsButton().addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
          printflushDebug("ClientController.listener -> AbandonsButton\n");
          ClientController.this.closing(e);
        }
      });
    }

    // --------------------------------------------------------------

    if (!this.windowClosingSetted) {
      this.windowClosingSetted = true;
      // Exit Buttons
      ClientController.this.clientFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      ClientController.this.clientFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          ClientController.this.closing(e);
        }
      });
    }
  }

  /**
   * Avvia una nuova sessione di gioco a partire dal login
   * di un utente e da uno stato di gioco.
   */
  public void newSession (LoginRecord loginRecord, UserRecord userRecord) {
    this.loginRecord = loginRecord;
    this.clientFrame.set(userRecord);
    this.setListeners();
    this.clientFrame.setVisible(true);
  }

}
