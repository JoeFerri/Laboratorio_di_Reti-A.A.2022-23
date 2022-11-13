/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.dialogs;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import assignment_6.model.Images;
import assignment_6.model.InfoLabel;
import assignment_6.model.KeyText;
import assignment_6.model.LoginRecord;
import assignment_6.model.Texts;
import assignment_6.view.panels.ImageLabelPanel;
import external.RequestFocusListener;
import static com.gf.utils.Utils.printflushDebug;


/**
 * Classe di utilità per la creazione di pop-up di informazione.
 */
public final class Dialogs {
  
  public static final int NEW   = JOptionPane.YES_OPTION;
  public static final int LOAD  = JOptionPane.NO_OPTION;

  private Dialogs () {
    ;
  }


  
  public static void showErrorDialog  ( Component parentComponent,
                                        Object message,
                                        String title,
                                        Icon icon) {

    printflushDebug("Dialogs.showErrorDialog()...\n");
    JOptionPane.showMessageDialog(
      parentComponent,
      message,
      title,
      JOptionPane.ERROR_MESSAGE,
      icon
    );
  }

  public static void showInfoDialog  ( Component parentComponent,
                                        Object message,
                                        String title,
                                        Icon icon) {

    printflushDebug("Dialogs.showInfoDialog()...\n");
    JOptionPane.showMessageDialog(
      parentComponent,
      message,
      title,
      JOptionPane.INFORMATION_MESSAGE,
      icon
    );
  }

  public static int showLoginDialog ( Component parentComponent,
                                      Object message,
                                      String title,
                                      Icon icon) {

    printflushDebug("Dialogs.showLoginDialog()...\n");
    Object[] options = {Texts.get(KeyText.LOGIN_NEW),Texts.get(KeyText.LOGIN_LOAD)};
    return  JOptionPane.showOptionDialog(
              parentComponent,
              message,
              title,
              JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              icon,
              options,
              options[1]
            );
  }

  public static LoginRecord showNewLoginDialog  ( Component parentComponent,
                                                  String title,
                                                  Icon icon) {

    printflushDebug("Dialogs.showNewLoginDialog()...\n");
    JTextField username = new JTextField();
    username.addAncestorListener( new RequestFocusListener() );
    JTextField password = new JPasswordField();
    Object[] message = {
      Texts.get(KeyText.LOGIN_USERNAME), username,
      Texts.get(KeyText.LOGIN_PASSWORD), password
    };
    
    int option = JOptionPane.showConfirmDialog(parentComponent,message,title,JOptionPane.OK_CANCEL_OPTION);
    LoginRecord login = option == JOptionPane.OK_OPTION &&
                          (!username.getText().equals("")) &&
                          (!password.getText().equals(""))
                            ? new LoginRecord(username.getText(),password.getText()) : null;
    printflushDebug("Dialogs.showNewLoginDialog() -> login = <%s>\n",login != null ? login : "null");
    return login;
  }

  public static String showSelectHeroDialog ( Component parentComponent,
                                              String title,
                                              Icon icon) {

    printflushDebug("Dialogs.showSelectHeroDialog()...\n");
    String[] paths = new String[]{""};
    InfoLabel[] oldInfoLabels = new InfoLabel[]{null};
    MouseAdapter listener = new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        InfoLabel infoLabel = ((InfoLabel)e.getComponent());
        paths[0] = "" + infoLabel.getInfo();
        Border border = BorderFactory.createLineBorder(Color.RED, 3);
        infoLabel.setBorder(border);
        if (oldInfoLabels[0] != null)
          oldInfoLabels[0].setBorder(null);
        oldInfoLabels[0] = infoLabel;
        printflushDebug("Dialogs.showSelectHeroDialog().listener -> path: %s\n",paths[0]);
      }
    };

    JPanel panel = new JPanel();
    JScrollPane jScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JPanel jpanelProductos = new JPanel();

    panel.setMinimumSize(new java.awt.Dimension(530, 620));
    panel.setName("");
    panel.setPreferredSize(new java.awt.Dimension(530, 620));
    panel.setLayout(null);

    jpanelProductos.setPreferredSize(new java.awt.Dimension(530, 720));
    jpanelProductos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 2));
    jScrollPane.setViewportView(jpanelProductos);

    panel.add(jScrollPane);
    jScrollPane.setBounds(0, 0, 530, 620);

    for (String path : Images.getPathsByType("hero")) {
      ImageIcon img = Images.getByPath(path);
      ImageLabelPanel imgLabel = ImageLabelPanel.getScaledWidth(img,path,100);
      imgLabel.getLabel().addMouseListener(listener);
      jpanelProductos.add(imgLabel);
    }

    JOptionPane.showMessageDialog(null,panel,title,JOptionPane.INFORMATION_MESSAGE,icon);
    printflushDebug("path: %s\n",paths[0]);
    return paths[0];
  }

  public static int showYesNoDialog ( Component parentComponent,
                                      Object message,
                                      String title,
                                      Icon icon) {

    printflushDebug("Dialogs.showYesNoDialog()...\n");
    return  JOptionPane.showOptionDialog(
              parentComponent,
              message,
              title,
              JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              icon,
              null,
              null
            );
  }

}
