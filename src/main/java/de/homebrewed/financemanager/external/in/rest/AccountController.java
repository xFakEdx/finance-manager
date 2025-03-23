package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.events.AccountCreationEvent;
import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

  private final ApplicationEventPublisher publisher;

  @PostMapping
  public ResponseEntity<Account> createAccount(
      @RequestBody @Valid CreateAccountCommand createAccountCommand) {
    publisher.publishEvent(
        new AccountCreationEvent(UUID.randomUUID().toString(), createAccountCommand));
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
