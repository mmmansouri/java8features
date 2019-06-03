package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class Account implements Comparable {

  int balance;

  @NonNull
  String accountOwnerName;

  List<Transaction> operations = new ArrayList<>();

  public Account(int initialBalance) {

    this.balance = initialBalance;
  }


  public Account depositTen() {
    System.out.println("--> depositTen method of object " + this);
    this.deposit(10);

    return this;
  }

  public Account deposit(String value) {
    this.deposit(Integer.valueOf(value));
    return this;
  }

  public void deposit(int value) {
    addDepositTransaction(value);
  }


  public void withdrawal(String value) {
    this.withdrawal(Integer.valueOf(value));
  }

  public void withdrawal(int value) {
    addWithdrawalTransaction(value);
  }

  private void addWithdrawalTransaction(int amount) {
    Transaction transaction = Transaction.builder().transactionType(TransactionType.WITHDRAWAL)
        .amount(amount).date(new Date()).balance(this.balance - amount).build();
    this.operations.add(transaction);
    this.balance = transaction.getBalance();
  }

  private void addDepositTransaction(int amount) {
    Transaction transaction = Transaction.builder().transactionType(TransactionType.DEPOSIT)
        .amount(amount).date(new Date()).balance(this.balance + amount).build();
    this.operations.add(transaction);
    this.balance = transaction.getBalance();
  }

  public void OwnerNameToUpperCase() {

    if (accountOwnerName != null && !accountOwnerName.isEmpty()) {
      this.accountOwnerName = accountOwnerName.toUpperCase();
    }
  }

  @Override
  public boolean equals(Object object) {

    return this.equalsBalance(object) && this.equalsOwner(object);

  }

  private boolean equalsBalance(Object object) {

    if (object == null) {
      return false;
    }

    if (object.getClass() != this.getClass()) {
      return false;
    }

    Account account = (Account) object;

    if (!(account.getBalance() == this.getBalance())) {
      return false;
    }

    return true;

  }


  private boolean equalsOwner(Object object) {

    if (object == null) {
      return false;
    }

    if (object.getClass() != this.getClass()) {
      return false;
    }

    Account account = (Account) object;

    if (!account.getAccountOwnerName().equals(this.getAccountOwnerName())) {
      return false;
    }

    return true;

  }


  @Override
  public int hashCode() {

    return Objects.hash(this.getAccountOwnerName(), this.getBalance());
  }

  @Override
  public int compareTo(Object o) {
    if (o == null) {
      throw new NullPointerException("Object is null for comparing");
    }

    Account account = (Account) o;

    if (account.getBalance() > this.getBalance()) {
      return -1;
    }

    if (account.getBalance() < this.getBalance()) {
      return 1;
    }

    return 0;
  }
}
