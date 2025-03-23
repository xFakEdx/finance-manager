package de.homebrewed.financemanager.transactionclearing;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.events.TransactionClearedEvent;
import de.homebrewed.financemanager.events.TransactionProcessedEvent;
import de.homebrewed.financemanager.external.acl.AccountRepositoryAcl;
import de.homebrewed.financemanager.external.acl.FinancialTransactionRepositoryAcl;
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
public class ClearingListener {

  private final FinancialTransactionRepositoryAcl financialTransactionRepositoryService;
  private final AccountRepositoryAcl accountRepositoryService;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionProcessed(TransactionProcessedEvent event) {
    FinancialTransaction transaction =
        financialTransactionRepositoryService.findById(event.transactionId());

    FinancialTransaction updatedTransaction =
        financialTransactionRepositoryService.updateCleared(transaction.id(), event.success());
    Account accountAfterSaving = accountRepositoryService.findById(updatedTransaction.accountId());
    Boolean clearedAfterUpdate = updatedTransaction.cleared();
    log.info("Transaction {} clearing status set to {}", event.transactionId(), clearedAfterUpdate);

    publisher.publishEvent(
        new TransactionClearedEvent(
            updatedTransaction.id(), clearedAfterUpdate, accountAfterSaving.balance()));
  }
}
