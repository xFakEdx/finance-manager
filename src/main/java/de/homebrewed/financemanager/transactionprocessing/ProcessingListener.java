package de.homebrewed.financemanager.transactionprocessing;

import de.homebrewed.financemanager.accounting.service.AccountService;
import de.homebrewed.financemanager.events.TransactionProcessedEvent;
import de.homebrewed.financemanager.events.TransactionValidatedEvent;
import de.homebrewed.financemanager.external.persistance.repository.FinancialTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class ProcessingListener {

  private final AccountService accountService;
  private final FinancialTransactionRepository transactionRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionValidated(TransactionValidatedEvent event) {
    if (!event.isValid()) return;

    Long txId = event.transactionId();
    transactionRepository
        .findById(txId)
        .orElseThrow(() -> new EntityNotFoundException("No Transaction found with id " + txId));

    boolean success = accountService.applyTransaction(txId);

    if (!success) {
      log.error("Transaction processing failed for Transaction {}", txId);
    } else {
      log.info("Transaction processing succeeded for Transaction {}", txId);
    }

    publisher.publishEvent(new TransactionProcessedEvent(txId, success));
  }
}
