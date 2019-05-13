package domain;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DevTestDataFactory {


    public static Account createAccountWithTransactions(){

        return getAccountWithTransactions();
    }


    public  Account createAccountTransactions(){

        return getAccountWithTransactions();
    }

    public  static Account getAccountEmpty(){

        return getAccount();


    }

    private static Account getAccount() {
        Account account=new Account();
        account.setBalance(100);
        return  account;
    }

    private static Account getAccountWithTransactions() {
        Account account=new Account();
        account.setBalance(100);
        List<Transaction> operations=new ArrayList<>();
        Transaction transaction1=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(10).date(new Date()).balance(110).build();
        operations.add(transaction1);
        Transaction transaction2=Transaction.builder().transactionType(TransactionType.WITHDRAWAL).amount(10).date(new Date()).balance(100).build();
        operations.add(transaction2);
        Transaction transaction3=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(10).date(new Date()).balance(110).build();
        operations.add(transaction3);
        Transaction transaction4=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(10).date(new Date()).balance(120).build();
        operations.add(transaction4);
        Transaction transaction5=Transaction.builder().transactionType(TransactionType.WITHDRAWAL).amount(10).date(new Date()).balance(110).build();
        operations.add(transaction5);

        account.setBalance(transaction5.getBalance());
        account.setOperations(operations);

        return  account;
    }


}
