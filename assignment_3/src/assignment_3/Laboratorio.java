/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import utils.Utils;


/**
 * Astrae l'idea di laboratorio.
 * Possiede idMax computer numerati (ids) da ID_MIN a idMax.
 * Provvede ad associare (disassociare) gli utenti ai computer e
 * installare i software sui computer.
 * Su tutti i computer del laboratorio sono installati
 * il software Geany, usato dagli studenti,
 * e il software Samba, usato dai professori.
 */
public class Laboratorio {

  private static final int ID_MIN = 1;
  private static final int ID_MAX = 20;
  private final int idMax;
  private final List<Computer> computers = new ArrayList<Computer>();
  // mappa associazioni computerID-utente
  private final Map<Integer,Utente> map = new HashMap<Integer,Utente>();
  
  
  public Laboratorio () {
    this(ID_MAX);
  }
  
  public Laboratorio (int idMax) {
    this.idMax = idMax;
    for (int i = ID_MIN; i <= idMax; i++)
      this.computers.add(new Computer(i));
    
      this.installaSoftwareSuTutti(Software.getByName("Geany")); // per gli studenti
      this.installaSoftwareSuTutti(Software.getByName("Samba")); // per i professori
    
    Utils.tprintflush("%s creato.\n",this);
  }
  

  /**
   * @return le associazini computerID-utente del momento
   */
  public synchronized Set<Entry<Integer, Utente>> getAssociazioni () {
    return this.map.entrySet();
  }

  /**
   * @return il numero totale di computer del laboratorio
   */
  public int getComputersLength () {
    return this.computers.size();
  }

  /**
   * @param utente un utente
   * @return true se all'utente è associato un computer,
   *         false altrimenti
   */
  public synchronized boolean isAssociatoUtenteAComputer (Utente utente) {
    return this.map.containsValue(utente);
  }
  
  /**
   * Associa un Utente ad un computer libero.
   * @param utente utilizzatore da associare al computer
   * @return 0 se tutti i computer sono occupati,
   *         l'id del computer associato altrimenti
   */
  public synchronized int associaUtenteAComputer (Utente utente) {
    Utils.tprintflushDebug("associa %s a un computer.\n",utente);
    if (this.map.size() == this.computers.size())
      return 0;
    for (int id = ID_MIN; id <= this.idMax; id++)
      if (this.map.get(id) == null) {
        this.map.put(id,utente);
        return id;
      }
    return 0;
  }

  /**
   * Associa un Utente al computer specificato dall'id
   * se quest'ultimo è libero.
   * @param utente utilizzatore da associare al computer
   * @param id identificatore del computer
   * @return 0 se il computer è occupato,
   *         l'id del computer associato altrimenti
   */
  public synchronized int associaUtenteAComputer (Utente utente, int id) {
    Utils.tprintflushDebug("associa %s a computer con id %d.\n",utente,id);
    if (this.map.size() == this.computers.size() || map.get(id) != null)
      return 0;
    this.map.put(id,utente);
    return id;
  }
  
  /**
   * Rimuove l'associazione relativa all'utente con i computer.
   * @param utente utilizzatore associato ai computer
   * @return la lista di id dei computer disassociati
   */
  public synchronized int[] disassociaUtenteDaComputer (Utente utente) {
    Utils.tprintflushDebug("disassocia %s dai computer.\n",utente);
    Utils.tprintflushDebug("Mappa associazioni: %s\n",this.map);
    List<Integer> ids = new ArrayList<Integer>();
    for (int id : Utils.keys(this.map,utente).mapToInt(Integer::intValue).toArray()) {
      this.map.remove(id);
      ids.add(id);
    }
    assert !this.map.containsValue(utente) : "Disassociazione computers per " + utente + " fallita!"; //!
    Utils.tprintflushDebug("%s -> ids computer disassociati: %s\n",utente,ids);

    return ids.stream().mapToInt(Integer::intValue).toArray();
  }
  
  /**
   * @param id identificatore computer
   * @return computer[id] o null
   */
  public Computer getComputer (int id) {
    return this.computers
            .stream()
            .filter(computer -> computer.getId() == id)
            .findFirst()
            .orElse(null);
  }
  
  /**
   * @return gli id ordinati dei computer del laboratorio
   */
  public int[] getComputersIds () {
    int[] ids = this.computers.stream().mapToInt(Computer::getId).toArray();
    Arrays.sort(ids);
    return ids;
  }
  
  /**
   * @param software il software da installare
   * @return l'id del computer su cui è stato installato il software
   */
  public int installaSoftware (Software software) {
    for (Computer computer : this.computers) {
      if (computer.hasSoftware(software)) {
        Utils.tprintflushDebug("%s già installato su %s.\n",software,computer);
        return computer.getId();
      }
    }
    
    int index = Utils.getRndm(0,computers.size()-1);
    computers.get(index).addSoftware(software);
    Utils.tprintflushDebug("%s installato su %s con id %d.\n",
      software,computers.get(index),computers.get(index).getId());
    return computers.get(index).getId();
  }
  
  public void installaSoftwareSuTutti (Software software) {
    this.computers.stream().forEach(computer -> computer.addSoftware(software));
  }
  
  @Override
  public String toString () {
    return "Laboratorio";
  }
}
