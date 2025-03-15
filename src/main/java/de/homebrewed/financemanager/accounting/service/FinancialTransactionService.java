package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.accounting.repository.AccountRepository;
import de.homebrewed.financemanager.accounting.repository.FinancialTransactionRepository;
import de.homebrewed.financemanager.accounting.repository.TransactionCategoryRepository;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.events.TransactionCreatedEvent;
import de.homebrewed.financemanager.external.in.dto.CreateTransactionRequest;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import de.homebrewed.financemanager.external.mapper.FinancialTransactionMapper;
import de.homebrewed.financemanager.external.persistance.entity.TransactionCategoryEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialTransactionService {
    
    private final FinancialTransactionRepository financialTransactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final ApplicationEventPublisher publisher;
    
    @Transactional
    public FinancialTransaction createTransaction(CreateTransactionRequest request) {
        log.info("Creating transaction for Account ID {}", request.accountId());
        AccountEntity accountEntity = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        
        TransactionCategoryEntity transactionCategoryEntity = null;
        if (request.categoryId() != null) {
            transactionCategoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        }

    FinancialTransactionEntity transactionEntity = new FinancialTransactionEntity();
        transactionEntity.setTransactionName(UUID.randomUUID());
        transactionEntity.setTransactionType(request.financialTransactionType());
        transactionEntity.setAmount(request.amount());
        transactionEntity.setCurrency(Currency.getInstance(request.currency()));
        transactionEntity.setTransactionDate(request.transactionDate());
        transactionEntity.setCategory(transactionCategoryEntity);
        transactionEntity.setCleared(request.cleared() != null ? request.cleared() : false);
        transactionEntity.setAccount(accountEntity);
        
        FinancialTransactionEntity savedEntity = financialTransactionRepository.save(transactionEntity);
        
        log.info("Transaction {} successfully created for Account {}",
                savedEntity.getId(), request.accountId());
        
        // <-- START ApplicationEvent hier!
        publisher.publishEvent(new TransactionCreatedEvent(savedEntity.getId()));
        
        return FinancialTransactionMapper.toDomain(savedEntity);
    }
}
