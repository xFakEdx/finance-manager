package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.events.FinancialTransactionCreationEvent;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinancialTransactionController {

  private final ApplicationEventPublisher eventPublisher;

  @PostMapping
  public ResponseEntity<FinancialTransaction> createTransaction(
      @RequestBody @Valid CreateTransactionCommand createTransactionCommand) {
    eventPublisher.publishEvent(
        new FinancialTransactionCreationEvent(
            UUID.randomUUID().toString(), createTransactionCommand));
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
