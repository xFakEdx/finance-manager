package de.homebrewed.financemanager.transactionclearing;

import de.homebrewed.financemanager.accounting.repository.FinancialTransactionRepository;
import de.homebrewed.financemanager.events.TransactionClearedEvent;
import de.homebrewed.financemanager.events.TransactionProcessedEvent;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearingListener {

  private final FinancialTransactionRepository transactionRepository;
  private final ApplicationEventPublisher publisher;
  
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionProcessed(TransactionProcessedEvent event) {
    FinancialTransactionEntity transaction =
        transactionRepository.findById(event.transactionId()).orElseThrow();
    boolean cleared = event.success();
    transaction.setCleared(cleared);
    FinancialTransactionEntity savedTransaction = transactionRepository.save(transaction);
    AccountEntity accountAfterSaving = transaction.getAccount();
    log.info(
        "Transaction {} clearing status set to {}",
        event.transactionId(),
        savedTransaction.getCleared());

    publisher.publishEvent(
        new TransactionClearedEvent(
            savedTransaction.getId(), cleared, accountAfterSaving.getBalance()));
  }
}
