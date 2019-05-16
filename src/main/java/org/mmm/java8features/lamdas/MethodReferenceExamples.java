package org.mmm.java8features.lamdas;

import domain.Account;
import domain.DevTestDataFactory;
import java.util.ArrayList;
import java.util.List;

public class MethodReferenceExamples {


  public static void main(String[] args) {

    referenceToInstanceMethodOfObject();

    referenceToStaticMethodOfClass();

    referenceToInstanceMethodOfArbitraryObject();

    referenceToAConstructor();

  }

  private static void referenceToInstanceMethodOfObject() {

    DevTestDataFactory devTestDataFactory = new DevTestDataFactory();
    DataFactory dataFactory = devTestDataFactory::createAccountTransactions;
    Account account = dataFactory.createAccount(100);
    System.out.println("-----  referenceToInstanceMethodOfObject -----");
    account.getOperations().forEach(System.out::println);
  }

  private static void referenceToStaticMethodOfClass() {
    DataFactory dataFactory = DevTestDataFactory::createAccountWithTransactions;
    Account account = dataFactory.createAccount(100);

    System.out.println("-----  referenceToStaticMethodOfClass -----");
    account.getOperations().forEach(System.out::println);
  }

  private static void referenceToInstanceMethodOfArbitraryObject() {

    System.out.println("-----  referenceToInstanceMethodOfArbitraryObject  -----");

    DataFactory dataFactory = DevTestDataFactory::getAccountEmpty;
    Account account1 = dataFactory.createAccount(100);
    Account account2 = dataFactory.createAccount(110);
    Account account3 = dataFactory.createAccount(90);

    List<Account> accountList = new ArrayList<>();
    accountList.add(account1);
    accountList.add(account2);
    accountList.add(account3);

    //Reference To Instance Method Of Arbitrary Object
    // !CAUTION! : the balance of the transaction will have the value of the arbitrary object chosen
    accountList.forEach(Account::depositTen);

    accountList.forEach(account -> account.getOperations().forEach(transaction -> {
      System.out.println("________");
      System.out.println(transaction);
    }));
  }

  private static void referenceToAConstructor() {

    DataFactory dataFactory = Account::new;

    Account account = dataFactory.createAccount(100);

    account.setBalance(100);

    System.out.println("-----  referenceToAConstructor -----");


  }


  @FunctionalInterface
  interface DataFactory {

    Account createAccount(int initialBalance);
  }
}
