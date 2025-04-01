package de.homebrewed.financemanager.external.persistance.service;

import de.homebrewed.financemanager.external.acl.FinancialTransactionRepositoryAcl;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.external.mapper.FinancialTransactionMapper;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.repository.AccountRepository;
import de.homebrewed.financemanager.external.persistance.repository.FinancialTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinancialTransactionRepositoryService implements FinancialTransactionRepositoryAcl {

  private final FinancialTransactionRepository financialTransactionRepository;
  private final AccountRepository accountRepository;

  @Override
  public FinancialTransaction findById(Long id) {

    return financialTransactionRepository
        .findById(id)
        .map(FinancialTransactionMapper::toDomain)
        .orElseThrow(
            () -> new EntityNotFoundException("FinancialTransaction with ID " + id + " not found"));
  }

  @Override
  public FinancialTransaction save(FinancialTransaction financialTransaction) {
    AccountEntity accountEntity =
        accountRepository
            .findById(financialTransaction.accountId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Account with ID " + financialTransaction.accountId() + " not found"));
    return FinancialTransactionMapper.toDomain(
        financialTransactionRepository.save(
            FinancialTransactionMapper.toEntity(financialTransaction, accountEntity)));
  }
  
  @Override
  public void updateCleared(Long transactionId, Boolean cleared) {
    financialTransactionRepository.updateCleared(transactionId, cleared);
  }
  
  
}
