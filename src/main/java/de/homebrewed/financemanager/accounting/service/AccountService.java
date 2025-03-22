package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.shared.commands.CreateAccountCoomand;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

  private final AccountRepositoryService accountRepositoryService;
  private final FinancialTransactionepositoryService financialTransactionRepositoryService;

  @Transactional
  public Account createAccount(CreateAccountCoomand request) {

    Account account =
        Account.builder()
            .accountName(request.accountName())
            .balance(request.initialBalance() != null ? request.initialBalance() : BigDecimal.ZERO)
            .build();

    Account savedAccount = accountRepositoryService.save(account);
    log.info("Account created: {}", savedAccount.getAccountName());
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
          case DEPOSIT, REFUND, TRANSFER -> account.getBalance().add(amount);
          case WITHDRAWAL, PAYMENT -> account.getBalance().subtract(amount);
        };

    if (newBalanceIsInvalid(newBalance)) {
      return false;
    }

    accountRepositoryService.updateBalance(account, newBalance);
    return true;
  }

  private boolean newBalanceIsInvalid(BigDecimal newBalance) {
    return newBalance.compareTo(BigDecimal.ZERO) < 0;
  }
}
