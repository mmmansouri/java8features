package org.mmm.synchronize;

public class SyncDilema {


  public static void main(String[] args) throws InterruptedException {

    ListeTab listeTab = new ListeTab();
    Runnable runnable1 = () -> {
      for (int i = 0; i < 100; i++) {
        listeTab.ajoute("run1");
      }
    };
    Runnable runnable2 = () -> {
      for (int i = 0; i < 100; i++) {
        listeTab.ajoute("run2");
      }
    };

    Thread thread1 = new Thread(runnable1);
    Thread thread2 = new Thread(runnable2);

    thread1.start();
    thread2.start();

    listeTab.printTable();

    Runnable runnable3 = () -> {
      for (int i = 0; i < 100; i++) {
        listeTab.ajouteSync("run3");
      }
    };
    Runnable runnable4 = () -> {
      for (int i = 0; i < 100; i++) {
        listeTab.ajouteSync("run4");
      }
    };

    Thread thread3 = new Thread(runnable3);
    Thread thread4 = new Thread(runnable4);

    thread3.start();
    thread4.start();

    listeTab.printTableSync();
  }
}
