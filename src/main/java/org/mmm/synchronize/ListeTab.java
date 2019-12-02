package org.mmm.synchronize;

import java.util.Arrays;

public class ListeTab {

  private String[] tab = new String[200];
  private String[] tabSync = new String[200];
  private int index = 0;
  private int indexSync = 0;

  public void ajoute(String s) {
    tab[index] = s;
    index++;
  }

  public synchronized void ajouteSync(String s) {
    tabSync[indexSync] = s;
    indexSync++;
  }


  public String[] getTab() {
    return tab;
  }

  public void printTable() {

    System.out.println("---- Start.");
    Arrays.stream(tab).forEach(System.out::print);
    System.out.println("---- End.");
  }

  public void printTableSync() {

    System.out.println("---- Start.");
    Arrays.stream(tabSync).forEach(System.out::print);
    System.out.println("---- End.");
  }
}
