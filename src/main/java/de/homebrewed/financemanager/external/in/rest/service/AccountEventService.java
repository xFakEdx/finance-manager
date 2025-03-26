package de.homebrewed.financemanager.external.in.rest.service;

import de.homebrewed.financemanager.events.AccountCreationEvent;
import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventService {

  private final ApplicationEventPublisher publisher;

  @Transactional
  public String forwardTransactionalAccountCreationEvent(CreateAccountCommand createAccountCommand) {
    String creationAccountUuid = UUID.randomUUID().toString();
    publisher.publishEvent(new AccountCreationEvent(creationAccountUuid, createAccountCommand));
    return creationAccountUuid;
  }
}
