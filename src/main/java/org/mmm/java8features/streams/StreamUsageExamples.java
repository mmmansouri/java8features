package org.mmm.java8features.streams;

import domain.Account;
import domain.DevTestDataFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUsageExamples {


  public static void main(String[] args) {

    simpleUsageOfStream();
    mapAndFlatMapUsage();
    reduceSimpleExample();
    streamCreationFromStreamFactory();
    peekUsageAntiPattenExample();
    parraleAndSequentialExample();
    forEachOrderedParraleAndSequentialExample();
    concatStringListInOneString();
    reduceToMaxElement();
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
    List<List<Account>> mappedList = accountFilteredLists.stream()
        .map(l -> l)
        .collect(Collectors.toList());

    mappedList.forEach(System.out::println);

    System.out.println("--- Flat Mapped  account list with only balance ---");

    List<Account> flatMappedList = accountFilteredLists.stream()
        .flatMap(l -> l.stream())
        .collect(Collectors.toList());

    flatMappedList.forEach(System.out::println);

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

    StringBuilder concatString = stringList.stream()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    System.out.println(concatString);
  }

}
