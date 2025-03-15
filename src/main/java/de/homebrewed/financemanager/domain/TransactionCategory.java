package de.homebrewed.financemanager.domain;

import lombok.Builder;

@Builder
public record TransactionCategory(Long id, String name) {}
