package de.homebrewed.financemanager.acl.out.persistence;

import de.homebrewed.financemanager.domain.Account;

import java.math.BigDecimal;

public interface AccountRepositoryAcl {
    Account save(Account account);
    Account findById(Long id);
    Account updateBalance(Account account, BigDecimal balance);
}
