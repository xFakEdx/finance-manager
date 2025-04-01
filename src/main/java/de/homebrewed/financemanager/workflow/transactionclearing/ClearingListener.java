package de.homebrewed.financemanager.workflow.transactionclearing;

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
import org.springframework.scheduling.annotation.Async;
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

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionProcessed(TransactionProcessedEvent event) {
        financialTransactionRepositoryService.updateCleared(event.transactionId(), event.success());
    FinancialTransaction updatedTransaction =
            financialTransactionRepositoryService.findById(event.transactionId());
    Account accountAfterSaving = accountRepositoryService.findById(updatedTransaction.accountId());
    Boolean clearedAfterUpdate = updatedTransaction.cleared();
    log.info("Transaction {} clearing status set to {}", event.transactionId(), clearedAfterUpdate);

    publisher.publishEvent(
        new TransactionClearedEvent(
            updatedTransaction.id(), clearedAfterUpdate, accountAfterSaving.balance()));
  }
}
