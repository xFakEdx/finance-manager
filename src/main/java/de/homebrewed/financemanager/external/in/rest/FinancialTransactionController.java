package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.accounting.service.FinancialTransactionService;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class FinancialTransactionController {

  private final FinancialTransactionService financialTransactionService;

  public FinancialTransactionController(FinancialTransactionService financialTransactionService) {
    this.financialTransactionService = financialTransactionService;
  }

  @PostMapping
  public ResponseEntity<FinancialTransaction> createTransaction(
      @RequestBody @Valid CreateTransactionCommand request) {
    FinancialTransaction financialTransaction =
        financialTransactionService.createTransaction(request);
    return new ResponseEntity<>(financialTransaction, HttpStatus.CREATED);
  }
}
