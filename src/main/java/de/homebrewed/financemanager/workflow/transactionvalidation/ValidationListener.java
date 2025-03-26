package de.homebrewed.financemanager.workflow.transactionvalidation;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.domain.FinancialTransactionType;
import de.homebrewed.financemanager.events.TransactionCreatedEvent;
import de.homebrewed.financemanager.events.TransactionValidatedEvent;
import de.homebrewed.financemanager.external.acl.AccountRepositoryAcl;
import de.homebrewed.financemanager.external.acl.FinancialTransactionRepositoryAcl;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ValidationListener {

  private final FinancialTransactionRepositoryAcl financialTransactionRepositoryService;
  private final AccountRepositoryAcl accountRepositoryService;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionCreated(TransactionCreatedEvent event) {
    FinancialTransaction financialTransaction =
        financialTransactionRepositoryService.findById(event.transactionId());
    publisher.publishEvent(
        new TransactionValidatedEvent(
            event.transactionId(), isTransactionValid(financialTransaction)));
  }

  boolean isTransactionValid(FinancialTransaction financialTransaction) {
    if (financialTransaction == null) {
      log.warn("Transaction cannot be null");
      return false;
    }
    Long accountId = financialTransaction.accountId();
    Long financialTransactionEntityId = financialTransaction.id();
    if (accountId == null) {
      log.warn(
          "Transaction validation: Transaction with ID {} has no account ID",
          financialTransactionEntityId);
      return false;
    }
    Account account = accountRepositoryService.findById(accountId);
    if (account == null) {
      log.warn(
          "Transaction validation: No account with ID {} found for Transaction {}",
          accountId,
          financialTransactionEntityId);
      return false;
    }
    FinancialTransactionType financialTransactionType = financialTransaction.transactionType();
    BigDecimal newBalance = account.balance().subtract(financialTransaction.amount());
    if ((financialTransactionType == FinancialTransactionType.WITHDRAWAL
            || financialTransactionType == FinancialTransactionType.PAYMENT)
        && newBalance.compareTo(BigDecimal.ZERO) < 0) {
      log.warn(
          "Transaction validation: After {} of amount {} from transaction {}, acount {} new balance < 0 ({} {})",
          financialTransactionType,
          financialTransaction.amount(),
          financialTransactionEntityId,
          account.accountName(),
          newBalance,
          financialTransaction.currency().getCurrencyCode());

      return false;
    }

    return true;
  }
}
