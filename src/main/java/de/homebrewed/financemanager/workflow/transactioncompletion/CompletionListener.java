package de.homebrewed.financemanager.workflow.transactioncompletion;

import de.homebrewed.financemanager.events.TransactionClearedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;

@Slf4j
@Component
public class CompletionListener {
    private static final BigDecimal WARN_THRESHOLD = BigDecimal.valueOf(100);
    private final KafkaTemplate<String, TransactionNotification> kafkaTemplate;
    
    public CompletionListener(KafkaTemplate<String, TransactionNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionCleared(TransactionClearedEvent event) {
        if (event.newBalance().compareTo(WARN_THRESHOLD) < 0) {
            log.warn("Low Balance Warning: Account has only {}", event.newBalance());
        }else {
            log.info("Transaction {} cleared successfully. New balance: {}", event.transactionId(), event.newBalance());
        }
        
        kafkaTemplate.send("transaction-topic",
                new TransactionNotification(event.transactionId(), event.cleared(), event.newBalance()));
        log.info("Kafka notification sent for Transaction {}", event.transactionId());
    }
}
