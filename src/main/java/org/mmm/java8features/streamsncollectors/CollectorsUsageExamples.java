package org.mmm.java8features.streamsncollectors;

import domain.Account;
import domain.DevTestDataFactory;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectorsUsageExamples {

  public static void main(String[] args) {
    System.out.println("---------_ _ _ _ - COLLECTORS USAGE EXAMPLE -  _ _ _ _--------");

    simpleToListExample();
    simpleToSetExample();
    simpleToCollectionExample();

  }

  public static void simpleToListExample() {

    System.out.println("***** - Simple toList Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    accountList.stream().filter(a -> a.getBalance() > 130).collect(Collectors.toList())
        .forEach(System.out::println);
  }

  public static void simpleToSetExample() {

    System.out.println("***** - Simple toSet Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    Set<Account> accountSet = accountList.stream().filter(a -> a.getBalance() > 130)
        .collect(Collectors.toSet());
    accountSet.forEach(System.out::println);
  }


  public static void simpleToCollectionExample() {

    System.out.println("***** - Simple toCollection Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    Set<Account> accountSet = accountList.stream().filter(a -> a.getBalance() > 130)
        .collect(Collectors.toCollection(TreeSet::new));
    accountSet.forEach(System.out::println);
  }


}
