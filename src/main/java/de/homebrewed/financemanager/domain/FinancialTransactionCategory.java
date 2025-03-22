package de.homebrewed.financemanager.domain;

import lombok.Builder;

@Builder
public record FinancialTransactionCategory(Long id, String name) {}
