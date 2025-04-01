package de.homebrewed.financemanager.external.acl;

import de.homebrewed.financemanager.domain.FinancialTransaction;

public interface FinancialTransactionRepositoryAcl {
  FinancialTransaction findById(Long transactionId);

  FinancialTransaction save(FinancialTransaction financialTransaction);

  void updateCleared(Long transactionId, Boolean cleared);
  
}
