package domain;

import java.util.List;

public interface AccountService {

  List<Account> findAttachedAccounts(Account account);
}
