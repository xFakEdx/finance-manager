package de.homebrewed.financemanager.external.acl;

import de.homebrewed.financemanager.domain.Account;
import java.math.BigDecimal;

public interface AccountRepositoryAcl {
  Account save(Account account);

  Account findById(Long id);

  Account updateBalance(Long id, BigDecimal newBalance);
}
