package de.homebrewed.financemanager.events;

import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import org.springframework.modulith.events.Externalized;

@Externalized(
    "financial-transaction-creation-event::#{#this.transactionUuid()}")
public record FinancialTransactionCreationEvent(
    String transactionUuid, CreateTransactionCommand createTransactionCommand) {}
