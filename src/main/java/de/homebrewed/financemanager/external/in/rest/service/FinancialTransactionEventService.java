package de.homebrewed.financemanager.external.in.rest.service;

import de.homebrewed.financemanager.events.FinancialTransactionCreationEvent;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialTransactionEventService {

  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public String forwardTransactionalTransactionCreationEvent(
      CreateTransactionCommand createTransactionCommand) {
    String uuid = UUID.randomUUID().toString();
    eventPublisher.publishEvent(
        new FinancialTransactionCreationEvent(uuid, createTransactionCommand));
    return uuid;
  }
}
