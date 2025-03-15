package de.homebrewed.financemanager.events;

import org.springframework.modulith.events.Externalized;

@Externalized("transaction-validated-event::#{#this.transactionId()}")
public record TransactionValidatedEvent(Long transactionId, boolean isValid) {}