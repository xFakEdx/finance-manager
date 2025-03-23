package de.homebrewed.financemanager.shared.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateAccountCommand(
    @NotBlank @Size(max = 255) String accountName, BigDecimal initialBalance) {}
