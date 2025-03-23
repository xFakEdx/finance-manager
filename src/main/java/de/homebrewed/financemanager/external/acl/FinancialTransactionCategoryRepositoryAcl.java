package de.homebrewed.financemanager.external.acl;

import de.homebrewed.financemanager.domain.FinancialTransactionCategory;

public interface FinancialTransactionCategoryRepositoryAcl {
  FinancialTransactionCategory findById(Long id);
}
