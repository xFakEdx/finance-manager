package de.homebrewed.financemanager.external.persistance.service;

import de.homebrewed.financemanager.external.acl.FinancialTransactionCategoryRepositoryAcl;
import de.homebrewed.financemanager.domain.FinancialTransactionCategory;
import de.homebrewed.financemanager.external.mapper.FinancialTransactionCategoryMapper;
import de.homebrewed.financemanager.external.persistance.repository.FinancialTransactionCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinancialTransactionCategoryRepositoryAclService
    implements FinancialTransactionCategoryRepositoryAcl {

  private final FinancialTransactionCategoryRepository financialTransactionCategoryRepository;

  @Override
  public FinancialTransactionCategory findById(Long id) {
    return financialTransactionCategoryRepository
        .findById(id)
        .map(FinancialTransactionCategoryMapper::toDomain)
        .orElseThrow(
            () -> new EntityNotFoundException("FinancialCategory with " + id + "not found."));
  }
}
