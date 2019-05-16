package domain;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DevTestDataFactory {


    public static Account createAccountWithTransactions(int initialBalance){

        return getAccountWithTransactions(initialBalance);
    }


    public  Account createAccountTransactions(int initialBalance){

        return getAccountWithTransactions(initialBalance);
    }

    public  static Account getAccountEmpty(int initialBalance){

        return getAccount(initialBalance);


    }

    private static Account getAccount(int initialBalance) {
        Account account=new Account(initialBalance);
        account.setBalance(100);
        return  account;
    }


    public static List<Account> getAccountsWithTransactions(int initialBalance) {

        List<Account> accountList=new ArrayList<>();

        accountList.add(getAccountWithTransactions(initialBalance));
        accountList.add(getAccountWithTransactions(initialBalance+10));
        accountList.add(getAccountWithTransactions(initialBalance+100));
        accountList.add(getAccountWithTransactions(initialBalance+60));
        accountList.add(getAccountWithTransactions(initialBalance+30));


        return  accountList;
    }



    private static Account getAccountWithTransactions(int initialBalance) {
        Account account=new Account(100);
        account.setBalance(initialBalance);
        List<Transaction> operations=new ArrayList<>();
        Transaction transaction1=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(10).date(new Date()).balance(account.getBalance()+10).build();
        operations.add(transaction1);
        Transaction transaction2=Transaction.builder().transactionType(TransactionType.WITHDRAWAL).amount(20).date(new Date()).balance(account.getBalance()-20).build();
        operations.add(transaction2);
        Transaction transaction3=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(11).date(new Date()).balance(account.getBalance()+11).build();
        operations.add(transaction3);
        Transaction transaction4=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(18).date(new Date()).balance(account.getBalance()+18).build();
        operations.add(transaction4);
        Transaction transaction5=Transaction.builder().transactionType(TransactionType.WITHDRAWAL).amount(12).date(new Date()).balance(account.getBalance()-12).build();
        operations.add(transaction5);

        account.setBalance(transaction5.getBalance());
        account.setOperations(operations);

        return  account;
    }

}
