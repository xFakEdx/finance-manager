package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.FinancialTransactionCategory;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionCategoryEntity;

public final class TransactionCategoryMapper {

  private TransactionCategoryMapper() {
    // Utility Klasse, keine Instanzierung
  }

  public static FinancialTransactionCategory toDomain(FinancialTransactionCategoryEntity entity) {
    if (entity == null) {
      return null;
    }
    return new FinancialTransactionCategory(entity.getId(), entity.getName());
  }
}
