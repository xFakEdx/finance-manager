package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.acl.out.persistence.FinancialTransactionepositoryAcl;
import de.homebrewed.financemanager.domain.FinancialTransaction;
import org.springframework.stereotype.Service;

@Service
public class FinancialTransactionepositoryService implements FinancialTransactionepositoryAcl {
    
    @Override
    public FinancialTransaction findById(Long transactionId) {
        return null;
    }
    
    @Override
    public FinancialTransaction save(FinancialTransaction financialTransaction) {
        return null;
    }
}
