package de.homebrewed.financemanager.events;

import org.springframework.modulith.events.Externalized;

@Externalized("transaction-processed-event::#{#this.transactionId()}")
public record TransactionProcessedEvent(Long transactionId, boolean success) {}
