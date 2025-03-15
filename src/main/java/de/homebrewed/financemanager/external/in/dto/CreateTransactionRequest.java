package de.homebrewed.financemanager.external.in.dto;

import de.homebrewed.financemanager.domain.FinancialTransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionRequest(
    @NotNull FinancialTransactionType financialTransactionType,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotBlank @Size(min = 3, max = 3) String currency,
    @NotNull LocalDateTime transactionDate,
    Long categoryId,
    @NotNull Long accountId,
    Boolean cleared) {}
