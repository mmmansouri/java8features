package org.mmm.java8features.lamdas;

import domain.Account;
import domain.DevTestDataFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MethodReferenceExamples {


    @FunctionalInterface
    interface DataFactory {

        Account createAccount();
    }

    public static void main(String[]args){

        referenceToInstanceMethodOfObject();

        referenceToStaticMethodOfClass();

        referenceToInstanceMethodOfArbitraryObject();

        referenceToAConstructor();

    }


    private static void referenceToInstanceMethodOfObject(){

        DevTestDataFactory devTestDataFactory=new DevTestDataFactory();
        DataFactory dataFactory = devTestDataFactory::createAccountTransactions;
        Account account= dataFactory.createAccount();
        System.out.println("-----  referenceToInstanceMethodOfObject -----");
        account.getOperations().forEach(System.out::println);
    }


    private static void referenceToStaticMethodOfClass(){
        DataFactory dataFactory = DevTestDataFactory::createAccountWithTransactions;
        Account account= dataFactory.createAccount();

        System.out.println("-----  referenceToStaticMethodOfClass -----");
        account.getOperations().forEach(System.out::println);
    }

    private  static void referenceToInstanceMethodOfArbitraryObject(){

        System.out.println("-----  referenceToInstanceMethodOfArbitraryObject  -----");

        DataFactory dataFactory = DevTestDataFactory::getAccountEmpty;
        Account account1= dataFactory.createAccount();
        Account account2= dataFactory.createAccount();
        Account account3= dataFactory.createAccount();

        List<Account> accountList=new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);


        //Reference To Instance Method Of Arbitrary Object
        accountList.forEach(Account::depositTen);

        accountList.forEach(account -> account.getOperations().forEach(transaction -> {System.out.println("________");System.out.println(transaction);}));
    }



    private static void referenceToAConstructor(){

        DataFactory dataFactory =Account::new;

        Account account=dataFactory.createAccount();

        account.setBalance(100);

        System.out.println("-----  referenceToAConstructor -----");


    }
}
