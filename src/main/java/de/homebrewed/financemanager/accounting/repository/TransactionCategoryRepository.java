package de.homebrewed.financemanager.accounting.repository;

import de.homebrewed.financemanager.external.persistance.entity.TransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategoryEntity, Long> {}
