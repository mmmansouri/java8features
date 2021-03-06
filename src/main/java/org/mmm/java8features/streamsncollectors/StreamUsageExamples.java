package org.mmm.java8features.streamsncollectors;

import domain.Account;
import domain.AccountService;
import domain.AccountServiceMock;
import domain.DevTestDataFactory;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUsageExamples {


  public static void main(String[] args) {

    System.out.println("---------_ _ _ _ - STREAM USAGE EXAMPLE -  _ _ _ _--------");
    simpleUsageOfStream();
    mapAndFlatMapUsage();
    reduceSimpleExample();
    streamCreationFromStreamFactory();
    peekUsageAntiPattenExample();
    parraleAndSequentialExample();
    forEachOrderedParraleAndSequentialExample();
    concatStringListInOneString();
    reduceToMaxElement();
    distinctExample();
    simpleInstanceMatching();
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

    AccountService accountService = new AccountServiceMock();
    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    System.out.println("Finding attached accounts to each account and adding them to the list :");
    System.out.println("-> Using map : ");
    List<List<Account>> accountLists = accountList.stream()
        .map(accountService::findAttachedAccounts).collect(Collectors.toList());
    accountLists.forEach(System.out::println);

    System.out.println("Using flatMap");

    List<Account> accountListFlatted = accountList.stream()
        .flatMap((a -> accountService.findAttachedAccounts(a).stream()))
        .collect(Collectors.toList());
    accountListFlatted.forEach(System.out::println);

  }

  private static void reduceToMaxElement() {

    //Grouping all accounts in one with summing the balances
    System.out.println("***** - reduce To Max Element - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    //HOW IT WORKS : Account::getBalance --> p->p.getBalance(); ---> int getBalance(account a){return a.getBalance}
    Comparator<Account> accountComparator = Comparator.comparingInt(Account::getBalance);
    BinaryOperator<Account> accountBinaryOperator = BinaryOperator.maxBy(accountComparator);

    accountList.stream().reduce(accountBinaryOperator).ifPresent(System.out::println);

  }


  private static void reduceSimpleExample() {

    //Grouping all accounts in one with summing the balances
    System.out.println("***** - reduceSimpleExample - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    System.out.println("---- Before reducing ---");
    accountList.forEach(System.out::println);

    BinaryOperator<Account> binaryOperator = (account1, account2) -> {
      Account result = DevTestDataFactory.getAccountEmpty(0);
      System.out
          .println("In BinaryOperator account object reference " + System.identityHashCode(result));
      result.setBalance(account1.getBalance() + account2.getBalance());
      result.setOperations(
          Stream.concat(account1.getOperations().stream(), account2.getOperations().stream())
              .collect(Collectors.toList()));
      return result;
    };

    System.out
        .println(
            "---- Reducing in SEQUENTIAL: Grouping all accounts in one with summing the balances---");

    System.out.println("RESULT --> " + accountList.stream().sequential().peek(p ->
        System.out.println(Thread.currentThread().getName() + " " + p))
        .reduce(new Account(0), binaryOperator));

    System.out
        .println(
            "---- Reducing in PARALLEL: Grouping all accounts in one with summing the balances---");

    System.out.println("RESULT --> " + accountList.stream().parallel().peek(p ->
        System.out.println(Thread.currentThread().getName() + " " + p))
        .reduce(new Account(0), binaryOperator, binaryOperator));
  }

  private static void streamCreationFromStreamFactory() {
    System.out.println("***** - Stream Creation From Stream Factory - ****");

    System.out.println(" -- String Stream Creation -- ");
    Stream stringStream = Stream.of("One", "Two", "Three");
    stringStream.forEach(System.out::println);

    IntStream integerStream = IntStream.of(1, 2, 3, 4);
    System.out.println("-- integer Stream Creation --");
    integerStream.forEach(System.out::println);
  }


  private static void peekUsageAntiPattenExample() {

    System.out.println("***** - peek Anti-Pattern Usage Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    //PEEK should NOT be used this way because this behaviour is non-deterministic due to the
    // possibility of certain peek() calls being omitted due to internal optimization
    accountList.stream().peek(a -> a.setAccountOwnerName(a.getAccountOwnerName().toUpperCase()))
        .forEach(System.out::println);

    List<Account> accountList2 = DevTestDataFactory.getAccountsWithTransactions(100);
    //Should be done this way
    accountList2.stream()
        .forEach(Account::OwnerNameToUpperCase);

    System.out.println(" ---> To Upper Case : ");
    accountList2.forEach(System.out::println);

  }

  private static void parraleAndSequentialExample() {

    System.out.println("***** - Parallel And Sequential Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    accountList.stream().parallel().map(Account::getAccountOwnerName)
        .peek(p ->
            System.out.println(Thread.currentThread().getName() + " " + p))
        .collect(Collectors.toList());

    accountList.stream().sequential().map(Account::getAccountOwnerName)
        .peek(p ->
            System.out.println(Thread.currentThread().getName() + " " + p))
        .collect(Collectors.toList());

  }


  private static void forEachOrderedParraleAndSequentialExample() {

    System.out.println("***** - ForEach Parallel And Sequential Example - ****");

    List<Account> accountList = DevTestDataFactory.getAccountsWithTransactions(100);

    Consumer<Integer> balancePrinter = (i -> System.out
        .println(Thread.currentThread().getName() + " account balance : " + i));

    System.out.println("-- List Initial Order --- ");
    for (Account account : accountList) {
      balancePrinter.accept(account.getBalance());
    }

    System.out.println(" --- forEach Sequential NOT sorted ->> Not SORTED but Order Kept --- ");

    accountList.stream().sequential().map(Account::getBalance)
        .forEach(balancePrinter);

    System.out.println(" --- forEach Sequential sorted ->> SORTED  --- ");

    accountList.stream().sequential().map(Account::getBalance).sorted()
        .forEach(balancePrinter);

    System.out
        .println(" --- forEachOrdered Sequential NOT sorted ->> NOT SORTED but Order Kept --- ");

    accountList.stream().sequential().map(Account::getBalance)
        .forEachOrdered(balancePrinter);

    System.out.println(" --- forEachOrdered Sequential sorted ->> SORTED  --- ");

    accountList.stream().sequential().map(Account::getBalance).sorted()
        .forEachOrdered(balancePrinter);

    System.out.println(" --- forEach Parallel NOT sorted ->> NOT SORTED &  Order NOT Kept--- ");
    accountList.stream().parallel().map(Account::getBalance)
        .forEach(balancePrinter);

    System.out.println(" --- forEach Parallel sorted ->> NOT SORTED &  Order NOT Kept --- ");
    accountList.stream().parallel().map(Account::getBalance).sorted()
        .forEach(balancePrinter);

    System.out
        .println(" --- forEachOrdered Parallel NOT sorted ->> NOT SORTED &  Order Kept ! --- ");
    accountList.stream().parallel().map(Account::getBalance)
        .forEachOrdered(balancePrinter);

    System.out.println(
        " --- forEachOrdered Parallel sorted ->> SORTED &  Order Kept but no Parallel execution !--- ");
    accountList.stream().parallel().map(Account::getBalance).sorted()
        .forEachOrdered(balancePrinter);

  }


  private static void concatStringListInOneString() {

    System.out.println("***** - Concat String List In One String - ****");

    List<String> stringList = Arrays.asList("Momo", "-Man", "-Sou");

    System.out.println("--> Using StringBuilder");
    StringBuilder concatString = stringList.stream()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    System.out.println(concatString);

    System.out.println("--> Using StringJoiner");
    System.out.println(stringList.stream()
        .collect(() -> new StringJoiner(""), StringJoiner::add, StringJoiner::merge).toString());

  }


  private static void distinctExample() {

    System.out.println("***** - Distinct Example - ****");

    System.out.println("--> Duplicates list");
    List<Account> accounts = DevTestDataFactory.getAccountsWithTransactions(100);

    accounts.addAll(DevTestDataFactory.getAccountsWithTransactions(100));

    accounts.forEach(System.out::println);

    System.out.println("--> Distinct List");
    accounts.stream().distinct().forEach(System.out::println);


  }

  public static void simpleInstanceMatching() {

    System.out.println("***** - Simple Instance Matching - ****");

    List<Account> accounts = DevTestDataFactory.getAccountsWithTransactions(100);

    if (accounts.stream().anyMatch(Account.class::isInstance)) {
      System.out.println("--> It Match !");
    }
  }

}
