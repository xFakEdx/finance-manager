package de.homebrewed.financemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import lombok.Builder;

@Builder
public record FinancialTransaction(Long id, UUID transactionName, FinancialTransactionType transactionType, BigDecimal amount,
                                   Currency currency, LocalDateTime transactionDate, FinancialTransactionCategory category,
                                   Long accountId,
                                   Boolean cleared) {
    
    }

