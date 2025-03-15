package de.homebrewed.financemanager.transactionprocessing;

import de.homebrewed.financemanager.accounting.repository.FinancialTransactionRepository;
import de.homebrewed.financemanager.accounting.service.AccountService;
import de.homebrewed.financemanager.events.TransactionProcessedEvent;
import de.homebrewed.financemanager.events.TransactionValidatedEvent;
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
public class ProcessingListener {
    
    private final AccountService accountService;
    private final FinancialTransactionRepository transactionRepository;
    private final ApplicationEventPublisher publisher;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionValidated(TransactionValidatedEvent event) {
        if (!event.isValid()) return;
        
        FinancialTransactionEntity transaction = transactionRepository.findById(event.transactionId())
                .orElseThrow();
        
        boolean success = accountService.applyTransaction(transaction);
        
        if (!success) {
            log.error("Transaction processing failed for Transaction {}", event.transactionId());
        } else {
            log.info("Transaction processing succeeded for Transaction {}", event.transactionId());
        }
        
        publisher.publishEvent(new TransactionProcessedEvent(event.transactionId(), success));
    }
    
}
