package domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Account {

     int balance=0;

     List<Transaction> operations=new ArrayList<>();


     public Account depositTen(){
          this.deposit(10);

          return this;
     }
     public Account deposit(String value){
                  this.deposit(Integer.valueOf(value));
                  return this;
     }
     public void deposit(int value){
          addDepositTransaction(value);
     }


     public void withdrawal(String value){
          this.withdrawal(Integer.valueOf(value));
     }

     public void withdrawal(int value){
          addWithdrawalTransaction(value);
     }

     private void addWithdrawalTransaction(int amount) {
          Transaction transaction=Transaction.builder().transactionType(TransactionType.WITHDRAWAL).amount(amount).date(new Date()).balance(this.balance-amount).build();
          this.operations.add(transaction);
          this.balance=transaction.getBalance();
     }

     private void addDepositTransaction(int amount){
          Transaction transaction=Transaction.builder().transactionType(TransactionType.DEPOSIT).amount(amount).date(new Date()).balance(this.balance+amount).build();
          this.operations.add(transaction);
          this.balance=transaction.getBalance();
     }
}
