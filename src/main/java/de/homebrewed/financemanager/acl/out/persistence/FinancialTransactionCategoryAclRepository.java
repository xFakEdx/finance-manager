package de.homebrewed.financemanager.acl.out.persistence;

import de.homebrewed.financemanager.domain.FinancialTransactionCategory;

public interface FinancialTransactionCategoryAclRepository {
    FinancialTransactionCategory findById(Long id);
}
