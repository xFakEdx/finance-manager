package de.homebrewed.financemanager.external.in.rest;

import de.homebrewed.financemanager.external.in.rest.service.AccountEventService;
import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

  private final AccountEventService accountEventService;

  @PostMapping
  public ResponseEntity<String> createAccount(
      @RequestBody @Valid CreateAccountCommand createAccountCommand) {
    String uuid =
        accountEventService.forwardTransactionalAccountCreationEvent(createAccountCommand);
    return new ResponseEntity<>(uuid, HttpStatus.ACCEPTED);
  }
}
