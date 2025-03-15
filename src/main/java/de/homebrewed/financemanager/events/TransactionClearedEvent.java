package de.homebrewed.financemanager.events;

import org.springframework.modulith.events.Externalized;

import java.math.BigDecimal;

@Externalized("transaction-cleared-event::#{#this.transactionId()}")
public record TransactionClearedEvent(Long transactionId, boolean cleared, BigDecimal newBalance) {}
