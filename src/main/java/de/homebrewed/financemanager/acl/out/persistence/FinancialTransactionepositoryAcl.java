package de.homebrewed.financemanager.acl.out.persistence;

import de.homebrewed.financemanager.domain.FinancialTransaction;

public interface FinancialTransactionepositoryAcl {
    FinancialTransaction findById(Long transactionId);
    
    FinancialTransaction save(FinancialTransaction financialTransaction);
}
