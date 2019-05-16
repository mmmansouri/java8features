package org.mmm.java8features.streams;

import domain.Account;
import domain.DevTestDataFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamUsageExamples {


  public static void main(String[] args) {

    simpleUsageOfStream();
    mapAndFlatMapUsage();
    reduceExample();
  }


  private static void simpleUsageOfStream() {

    System.out.println("***** - simpleUsageOfStream - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    List<Account> accountResultList = accountList.stream()
        .filter(account -> account.getBalance() > 100).collect(Collectors.toList());

    accountResultList.forEach(System.out::println);
  }

  private static void mapAndFlatMapUsage() {

    System.out.println("*** - mapAndFlatMapUsage - ***");

    List<Account> accountList1 = DevTestDataFactory.getAccountsWithTransactions(100);
    List<Account> accountList2 = DevTestDataFactory.getAccountsWithTransactions(400);
    List<Account> accountList3 = DevTestDataFactory.getAccountsWithTransactions(200);
    List<Account> accountList4 = DevTestDataFactory.getAccountsWithTransactions(300);

    List<List<Account>> accountLists = new ArrayList<>();

    accountLists.add(accountList1);
    accountLists.add(accountList2);
    accountLists.add(accountList3);
    accountLists.add(accountList4);

    List<List<Account>> accountFilteredLists = accountLists.stream()
        .filter(a -> a.stream().mapToInt(Account::getBalance).sum() > 1500)
        .collect(Collectors.toList());

    System.out.println("--- Filtered account list balance>1500 ---");

    accountFilteredLists.forEach(System.out::println);

    System.out.println("--- Mapped  account list with only balance ---");
    List<List<Integer>> mappedList = accountFilteredLists.stream()
        .map(l -> l.stream().map(a -> a.getBalance()).collect(Collectors.toList()))
        .collect(Collectors.toList());

    mappedList.forEach(System.out::println);

    System.out.println("--- Flat Mapped  account list with only balance ---");

    List<Integer> flatMappedList = accountFilteredLists.stream()
        .flatMap(l -> l.stream().map(Account::getBalance))
        .collect(Collectors.toList());

    flatMappedList.forEach(System.out::println);

  }

  private static void reduceExample() {

    //Grouping all accounts in one with summing the balances
    System.out.println("***** - reduceExample - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    System.out.println("---- Before reducing ---");
    accountList.forEach(System.out::println);

    Account accountResult = accountList.stream()
        .reduce(new Account(0), (result, account) -> {
              result
                  .setBalance(result.getBalance() + account.getBalance());
              return result;
            }
        );

    System.out
        .println("---- After reducing : Grouping all accounts in one with summing the balances---");
    System.out.println(accountResult);
  }

}
