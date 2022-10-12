/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;


/**
 * Astrae l'idea di software.
 */
public class Software {

  private static final String database = "Geogebra|uno dei software più usati per la geometria~WxMaxima|strumento CAS (Computer Algebra System)~Kig|altro software geometrico~KmPlot|programma per disegnare grafici cartesiani, polari, eccetera...~Pari/GP|console per lo studio della teoria dei numeri~Celestia|visualizzatore 3d dell'intero universo conosciuto~Stellarium|planetario 3d~Gnuplot|software per generare grafici in diversi formati~Octave|applicazione software per l'analisi numerica (compatibile con il linguaggio MatLab)~LyX|editor grafico per il LaTeX~g3data|software per estrarre dati dalle immagini di grafici~Aptana|studio IDE professionale per lo sviluppo di applicazioni Web 2.0~Geany|editor di testo per GTK+~MonoDevelop|IDE realizzato principalmente per C# ed altri linguaggi .NET~NetBeans|IDE per Java~build-essential|set di librerie fondamentali per la compilazione~Apache|combinazione per eseguire un server web in locale~MySQL|combinazione per eseguire un server web in locale~PHP|combinazione per eseguire un server web in locale~Filezilla|client FTP~unixODBC|interfaccia per ODBC~GNOME MDB|visualizzatore di file Access~Samba|protocollo di comunicazione compatibile con SMB~CVS|sistemi di controllo versione~Subversion|sistemi di controllo versione~Git|sistemi di controllo versione~Bazaar|sistemi di controllo versione~VirtualBox|software di virtualizzazione~Dia|programma per disegnare diagrammi~Lazarus|IDE per il Pascal~OpenOffice.org Base|applicazione della suite OpenOffice.org dedicata ai database~Angry IP Scanner|programma che scansiona la rete~Code::Blocks|IDE per C++~Zenmap|strumento grafico di interfaccia a Nmap, un avanzatissimo scanner di rete~DOSBox|emulatore di DOS~GraphThing|applicazione per lo studio della teoria dei grafi~Minicom|programma di comunicazione seriale~TOra|interfaccia per l'amministrazione di database~phpMyAdmin|interfaccia web ai database~OpenSSH|server di shell sicura remota";
  private static final Map<String,Software> map = new HashMap<String,Software>();
  private static final List<String> keys = new ArrayList<String>();
  
  static {
    String[] softwares = database.split("~");
    String[] tokens;
    for (String sf : softwares) {
      tokens = sf.split("\\|");
      assert tokens.length == 2 : "Errore nella stringa del database softwares!"; //!
      map.put(tokens[0], new Software(tokens[0],tokens[1]));
      keys.add(tokens[0]);
    }
  }
  
  private final String nome;
  private final String descrizione;
  
  
  private Software (String nome, String descrizione) {
    this.nome = nome;
    this.descrizione = descrizione;
  }
  
  /**
   * @return un software a caso
   */
  public static Software getRndm () {
    return map.get(keys.get(Utils.getRndm(0,keys.size()-1)));
  }
  
  public static Software getByName (String nome) {
    return map.get(nome);
  }
  
  
  public String getDescrizione () {
    return this.descrizione;
  }
  
  @Override
  public String toString () {
    return this.nome;
  }
}
