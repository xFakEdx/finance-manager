package de.homebrewed.financemanager.events;


import org.springframework.modulith.events.Externalized;

@Externalized("transaction-created-event::#{#this.transactionId()}")
public record TransactionCreatedEvent(Long transactionId) {}
