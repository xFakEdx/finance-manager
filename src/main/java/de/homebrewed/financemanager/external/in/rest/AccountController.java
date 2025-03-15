package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.accounting.service.AccountService;
import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.external.in.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<Account> createAccount(@RequestBody @Valid CreateAccountRequest request) {
    Account account = accountService.createAccount(request);
    return new ResponseEntity<>(account, HttpStatus.CREATED);
  }
}
