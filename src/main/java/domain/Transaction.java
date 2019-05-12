package domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Transaction {

    int amount;
    TransactionType transactionType;
    Date date;
    int balance;

}
