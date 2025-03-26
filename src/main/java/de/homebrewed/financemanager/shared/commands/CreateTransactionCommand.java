package de.homebrewed.financemanager.shared.commands;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CreateTransactionCommand(
    @NotNull String financialTransactionType,
    @NotNull @DecimalMin("0.01") BigDecimal amount,
    @NotBlank @Size(min = 3, max = 3) String currency,
    @NotNull LocalDateTime transactionDate,
    Long categoryId,
    @NotNull Long accountId,
    Boolean cleared) {}
