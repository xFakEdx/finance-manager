package de.homebrewed.financemanager.transactionvalidation;

import de.homebrewed.financemanager.external.persistance.repository.AccountRepository;
import de.homebrewed.financemanager.external.persistance.repository.FinancialTransactionRepository;
import de.homebrewed.financemanager.events.TransactionCreatedEvent;
import de.homebrewed.financemanager.events.TransactionValidatedEvent;
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
public class ValidationListener {

  private final FinancialTransactionRepository financialTransactionRepository;
  private final AccountRepository accountRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionCreated(TransactionCreatedEvent event) {
    financialTransactionRepository
        .findById(event.transactionId())
        .ifPresentOrElse(
            financialTransaction -> {
              Long accountId = financialTransaction.getAccount().getId();
              if (accountRepository.existsById(accountId)) {
                log.info(
                    "Transaction {} validation successful: Account {} exists",
                    event.transactionId(),
                    accountId);
                publisher.publishEvent(new TransactionValidatedEvent(event.transactionId(), true));
              } else {
                log.error(
                    "Validation failed: Account {} for Transaction {} not found",
                    accountId,
                    event.transactionId());
                publisher.publishEvent(new TransactionValidatedEvent(event.transactionId(), false));
              }
            },
            () -> {
              log.error("Transaction {} does not exist for validation", event.transactionId());
              publisher.publishEvent(new TransactionValidatedEvent(event.transactionId(), false));
            });
  }
}
