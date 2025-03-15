package de.homebrewed.financemanager.transactioncompletion;

import java.math.BigDecimal;

public record TransactionNotification(Long transactionId, boolean cleared, BigDecimal balance) {}
