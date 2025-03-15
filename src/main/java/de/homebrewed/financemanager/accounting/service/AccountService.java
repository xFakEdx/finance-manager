package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.accounting.repository.AccountRepository;
import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.external.in.dto.CreateAccountRequest;
import de.homebrewed.financemanager.external.mapper.AccountMapper;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Transactional
  public Account createAccount(CreateAccountRequest request) {

    AccountEntity entity = new AccountEntity();
    entity.setAccountName(request.accountName());
    entity.setBalance(
        request.initialBalance() != null ? request.initialBalance() : BigDecimal.ZERO);

    AccountEntity savedEntity = accountRepository.save(entity);

    return AccountMapper.toDomain(savedEntity);
  }

  @Transactional
  public boolean applyTransaction(FinancialTransactionEntity transaction) {
    AccountEntity account =
        accountRepository
            .findById(transaction.getAccount().getId())
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    BigDecimal newBalance =
        switch (transaction.getTransactionType()) {
          case DEPOSIT, REFUND, TRANSFER -> account.getBalance().add(transaction.getAmount());
          case WITHDRAWAL, PAYMENT -> account.getBalance().subtract(transaction.getAmount());
        };

    if (newBalanceIsInvalid(newBalance)) {
      return false;
    }

    account.setBalance(newBalance);
    accountRepository.save(account);

    return true;
  }

  private boolean newBalanceIsInvalid(BigDecimal newBalance) {
    return newBalance.compareTo(BigDecimal.ZERO) < 0;
  }
}
