package de.homebrewed.financemanager.accountcreation;

import de.homebrewed.financemanager.accounting.service.AccountService;
import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.events.AccountCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountCreationListener {

  private final AccountService accountService;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleAccountCreation(AccountCreationEvent event) {

    Account account = accountService.createAccount(event.createAccountCommand());
    log.info("Account successfully created with account name {}", account.accountName());
  }
}
