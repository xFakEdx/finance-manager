package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.domain.FinancialTransactionCategory;
import de.homebrewed.financemanager.domain.FinancialTransactionType;
import de.homebrewed.financemanager.events.TransactionCreatedEvent;
import de.homebrewed.financemanager.shared.commands.CreateTransactionCommand;
import jakarta.transaction.Transactional;
import java.util.Currency;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialTransactionService {

  private final FinancialTransactionepositoryService financialTransactionRepositoryService;
  private final AccountRepositoryService accountRepositoryService;
  private final FinancialTransactionCategoryRepositoryService
      financialTransactionCategoryRepositoryService;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public FinancialTransaction createTransaction(CreateTransactionCommand request) {
    log.info("Creating transaction for Account ID {}", request.accountId());
    Account account = accountRepositoryService.findById(request.accountId());

    FinancialTransactionCategory financialTransactionCategory = null;
    if (request.categoryId() != null) {
      financialTransactionCategory =
          financialTransactionCategoryRepositoryService.findById(request.categoryId());
    }

    FinancialTransaction financialTransaction =
        FinancialTransaction.builder()
            .transactionName(UUID.randomUUID())
            .transactionType(FinancialTransactionType.valueOf(request.financialTransactionType()))
            .amount(request.amount())
            .currency(Currency.getInstance(request.currency()))
            .transactionDate(request.transactionDate())
            .category(financialTransactionCategory)
            .cleared(request.cleared() != null ? request.cleared() : false)
            .accountId(account.getId())
            .build();

    FinancialTransaction savedFinacialTransaction =
        financialTransactionRepositoryService.save(financialTransaction);

    log.info(
        "Transaction {} successfully created for Account {}",
        savedFinacialTransaction.id(),
        account.getId());

    // <-- START ApplicationEvent hier!
    publisher.publishEvent(new TransactionCreatedEvent(savedFinacialTransaction.id()));

    return savedFinacialTransaction;
  }
}
