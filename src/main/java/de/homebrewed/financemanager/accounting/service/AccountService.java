package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.external.acl.AccountRepositoryAcl;
import de.homebrewed.financemanager.external.acl.FinancialTransactionRepositoryAcl;
import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

  private final AccountRepositoryAcl accountRepositoryService;
  private final FinancialTransactionRepositoryAcl financialTransactionRepositoryService;

  @Transactional
  public Account createAccount(CreateAccountCommand request) {

    Account account =
        Account.builder()
            .accountName(request.accountName())
            .balance(request.initialBalance() != null ? request.initialBalance() : BigDecimal.ZERO)
            .build();

    Account savedAccount = accountRepositoryService.save(account);
    log.info("Account created: {}", savedAccount.accountName());
    return savedAccount;
  }

  @Transactional
  public boolean applyTransaction(Long transactionId) {
    FinancialTransaction financialTransaction =
        financialTransactionRepositoryService.findById(transactionId);

    Account account = accountRepositoryService.findById(financialTransaction.accountId());

    final BigDecimal amount = financialTransaction.amount();
    BigDecimal newBalance =
        switch (financialTransaction.transactionType()) {
          case DEPOSIT, REFUND, TRANSFER -> account.balance().add(amount);
          case WITHDRAWAL, PAYMENT -> account.balance().subtract(amount);
        };

    accountRepositoryService.updateBalance(account.id(), newBalance);
    return true;
  }
}
