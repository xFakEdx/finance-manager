package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.acl.out.persistence.AccountRepositoryAcl;
import de.homebrewed.financemanager.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountRepositoryService implements AccountRepositoryAcl {
    
    @Override
    public Account save(Account account) {
        return null;
    }
    
    @Override
    public Account findById(Long id) {
        return null;
    }
    
    @Override
    public Account updateBalance(Account account, BigDecimal balance) {
        return null;
    }
}
