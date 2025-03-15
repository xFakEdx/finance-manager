package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;

public class FinancialTransactionMapper {
  public static FinancialTransaction toDomain(FinancialTransactionEntity entity) {
    return new FinancialTransaction(
        entity.getId(),
        entity.getTransactionName(),
        entity.getTransactionType(),
        entity.getAmount(),
        entity.getCurrency(),
        entity.getTransactionDate(),
        TransactionCategoryMapper.toDomain(entity.getCategory()),
        entity.getAccount().getId(),
        entity.getCleared());
  }
}
