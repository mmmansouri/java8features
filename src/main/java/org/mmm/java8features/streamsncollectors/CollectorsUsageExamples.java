package org.mmm.java8features.streamsncollectors;

import domain.Account;
import domain.DevTestDataFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CollectorsUsageExamples {

  public static void main(String[] args) {
    System.out.println("---------_ _ _ _ - COLLECTORS USAGE EXAMPLE -  _ _ _ _--------");

    simpleToListExample();

  }

  public static void simpleToListExample() {

    System.out.println("***** - Simple toList Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    accountList.stream().filter(a -> a.getBalance() > 130).collect(Collectors.toList())
        .forEach(System.out::println);
  }
}
