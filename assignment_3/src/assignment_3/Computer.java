/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.HashSet;
import java.util.Set;

import utils.Utils;


/**
 * Astrae l'idea di computer.
 * Può installare più software diversi tra loro
 * ed ha un id univoco (l'unicità è a carico di chi istanzia).
 */
public class Computer {

  private final Set<Software> softwares = new HashSet<Software>();
  private final int id;
  
  
  public Computer (int id) {
    this.id = id;
  }
  
  
  public boolean addSoftware (Software software) {
    return this.softwares.add(software);
  }

  public boolean addSoftware (String software) {
    return this.softwares.add(Software.getByName(software));
  }
  
  public boolean hasSoftware (Software software) {
    return this.softwares.stream()
        .filter(sfwr -> software.equals(sfwr))
        .findFirst()
        .isPresent();
  }
  
  public boolean hasSoftware (String software) {
    return this.hasSoftware(Software.getByName(software));
  }
  
  public int getId () {
    return this.id;
  }

  /**
   * @param software un software
   * @return l'output generato dall'esecuzione del software
   */
  public String getEseguiOutput (Software software) {
    String output = "";
    if (this.hasSoftware(software)) {
      output = software + /*"(" + software.getDescrizione() + ")" +*/ " eseguito su " + this;
    }
    else
      output = software + /*"(" + software.getDescrizione() + ")" +*/ " non è installato su " + this;
    return output;
  }
  
  /**
   * @param software il nome di un software
   * @return l'output generato dall'esecuzione del software
   */
  public String getEseguiOutput (String software) {
    return this.getEseguiOutput(Software.getByName(software));
  }
  
  /**
   * Esegue il software sul computer.
   * @param software un software
   * @return true se il software è stato eseguito
   */
  public boolean esegui (Software software) {
    boolean esecuzione = false;
    if (esecuzione = this.hasSoftware(software))
      Utils.tprintflush(getEseguiOutput(software));
    return esecuzione;
  }
  
  /**
   * Esegue il software sul computer.
   * @param software il nome di un software
   * @return true se il software è stato eseguito
   */
  public boolean esegui (String software) {
    return this.esegui(Software.getByName(software));
  }
  
  @Override
  public String toString () {
    return "Computer-" + this.getId();
  }
}
