package de.homebrewed.financemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Account(
    Long id,
    String accountName,
    BigDecimal balance,
    LocalDateTime lastChanged,
    LocalDateTime createdAt) {}
