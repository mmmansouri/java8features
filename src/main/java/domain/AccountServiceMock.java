package domain;

import java.util.List;

public class AccountServiceMock implements AccountService {


  public List<Account> findAttachedAccounts(Account account) {
    return DevTestDataFactory.getAccountsWithTransactions(100);
  }
}
