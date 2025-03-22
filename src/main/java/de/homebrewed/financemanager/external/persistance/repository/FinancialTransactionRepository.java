package de.homebrewed.financemanager.external.persistance.repository;

import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository
    extends JpaRepository<FinancialTransactionEntity, Long> {}
