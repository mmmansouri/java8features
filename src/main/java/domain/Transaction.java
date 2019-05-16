package domain;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {

  int amount;
  TransactionType transactionType;
  Date date;
  int balance;

}
