package de.homebrewed.financemanager.transactionprocessing;

import de.homebrewed.financemanager.accounting.service.AccountService;
import de.homebrewed.financemanager.events.TransactionProcessedEvent;
import de.homebrewed.financemanager.events.TransactionValidatedEvent;
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
public class ProcessingListener {

  private final AccountService accountService;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionValidated(TransactionValidatedEvent event) {
    if (!event.isValid()) return;

    Long transactionId = event.transactionId();
    boolean success = accountService.applyTransaction(transactionId);

    if (!success) {
      log.error("Transaction processing failed for Transaction {}", transactionId);
    } else {
      log.info("Transaction processing succeeded for Transaction {}", transactionId);
    }

    publisher.publishEvent(new TransactionProcessedEvent(transactionId, success));
  }
}
