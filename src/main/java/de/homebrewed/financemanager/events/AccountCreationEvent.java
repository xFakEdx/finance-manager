package de.homebrewed.financemanager.events;


import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
import org.springframework.modulith.events.Externalized;

@Externalized("account-creation-event::#{#this.creationAccountUuid()}")
public record AccountCreationEvent(String creationAccountUuid, CreateAccountCommand createAccountCommand) {}
