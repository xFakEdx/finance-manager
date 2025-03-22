package de.homebrewed.financemanager.accounting.service;

import de.homebrewed.financemanager.acl.out.persistence.FinancialTransactionCategoryAclRepository;
import de.homebrewed.financemanager.domain.FinancialTransactionCategory;
import org.springframework.stereotype.Service;

@Service
public class FinancialTransactionCategoryRepositoryService implements FinancialTransactionCategoryAclRepository {
    @Override
    public FinancialTransactionCategory findById(Long id) {
        return null;
    }
}
