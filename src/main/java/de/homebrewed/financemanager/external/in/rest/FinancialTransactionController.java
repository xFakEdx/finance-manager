package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.external.in.rest.service.FinancialTransactionEventService;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final FinancialTransactionEventService financialTransactionEventService;

  @PostMapping
  public ResponseEntity<String> createTransaction(
      @RequestBody @Valid CreateTransactionCommand createTransactionCommand) {
    String uuid =
        financialTransactionEventService.forwardTransactionalTransactionCreationEvent(
            createTransactionCommand);
    return new ResponseEntity<>(uuid, HttpStatus.ACCEPTED);
  }
}
