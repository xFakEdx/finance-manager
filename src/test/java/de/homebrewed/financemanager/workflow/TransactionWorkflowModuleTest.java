package de.homebrewed.financemanager.workflow;

import static org.assertj.core.api.Assertions.assertThat;

import de.homebrewed.financemanager.events.AccountCreationEvent;
import de.homebrewed.financemanager.events.FinancialTransactionCreationEvent;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import de.homebrewed.financemanager.external.persistance.repository.AccountRepository;
import de.homebrewed.financemanager.external.persistance.repository.FinancialTransactionRepository;
import de.homebrewed.financemanager.shared.commands.CreateAccountCommand;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@AutoConfigureWebMvc
@RequiredArgsConstructor
class TransactionWorkflowModuleTest {

  private final AccountRepository accountRepository;
  private final FinancialTransactionRepository financialTransactionRepository;

  @Test
  void createAccount(Scenario scenario) {
    // 1st Creating an account (usually via REST)
    var uuidAccount = UUID.randomUUID().toString();
    BigDecimal initialBalance = new BigDecimal("10000");
    CreateAccountCommand createAccountCommand =
        new CreateAccountCommand("test checking account", initialBalance);
    scenario
        .publish(() -> new AccountCreationEvent(uuidAccount, createAccountCommand))
        .andWaitForStateChange(accountRepository::findAll)
        .andVerify(
            result -> {
              assertThat(result).hasSize(1);
              AccountEntity actualAccountEntity = result.getFirst();
              assertThat(actualAccountEntity.getBalance()).isEqualTo(initialBalance);
              assertThat(actualAccountEntity.getId()).isNotNull();
            });

    // 2nd Creating a transaction to change account (subtract or add) balance
    var uuidTransaction = UUID.randomUUID().toString();
    BigDecimal amountToPay = new BigDecimal("100");
    CreateTransactionCommand createTransactionCommand =
        new CreateTransactionCommand(
            "PAYMENT", amountToPay, "USD", LocalDateTime.now(), 1L, 1L, Boolean.FALSE);
    scenario
        .publish(
            () -> new FinancialTransactionCreationEvent(uuidTransaction, createTransactionCommand))
        .andWaitForStateChange(financialTransactionRepository::findAll)
        .andVerify(
            result -> {
              assertThat(result).hasSize(1);
              FinancialTransactionEntity financialTransactionEntity = result.getFirst();
              assertThat(financialTransactionEntity.getId()).isNotNull();
              assertThat(financialTransactionEntity.getCleared()).isTrue();
            });

    // If transaction was cleared successfully, then the ammount of balance has changed in the created account
    assertThat(accountRepository.findAll().getFirst().getBalance())
        .isEqualTo(initialBalance.subtract(amountToPay));
  }
}
