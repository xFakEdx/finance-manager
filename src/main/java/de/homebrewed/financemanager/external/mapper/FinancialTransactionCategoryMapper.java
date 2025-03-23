package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.FinancialTransactionCategory;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionCategoryEntity;

public final class FinancialTransactionCategoryMapper {

  public static FinancialTransactionCategory toDomain(FinancialTransactionCategoryEntity entity) {
    if (entity == null) {
      return null;
    }
    return new FinancialTransactionCategory(entity.getId(), entity.getName());
  }

  public static FinancialTransactionCategoryEntity toEntity(FinancialTransactionCategory domain) {
    if (domain == null) {
      return null;
    }
    return new FinancialTransactionCategoryEntity(domain.id(), domain.name());
  }
}
