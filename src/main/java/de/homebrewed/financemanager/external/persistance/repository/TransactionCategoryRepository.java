package de.homebrewed.financemanager.external.persistance.repository;

import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<FinancialTransactionCategoryEntity, Long> {}
