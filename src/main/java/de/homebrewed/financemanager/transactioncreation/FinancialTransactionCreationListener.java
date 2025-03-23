package de.homebrewed.financemanager.transactioncreation;

import de.homebrewed.financemanager.accounting.service.FinancialTransactionService;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.events.FinancialTransactionCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinancialTransactionCreationListener {

  private final FinancialTransactionService financialTransactionService;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleFinancialTransactionCreation(FinancialTransactionCreationEvent event) {
    FinancialTransaction financialTransaction =
        financialTransactionService.createTransaction(event.createTransactionCommand());
    log.info("Transaction successfully created with ID {}", financialTransaction.id());
  }
}
