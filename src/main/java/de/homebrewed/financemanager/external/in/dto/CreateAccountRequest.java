package de.homebrewed.financemanager.external.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateAccountRequest(
    @NotBlank @Size(max = 255) String accountName,
    BigDecimal initialBalance
) {}
