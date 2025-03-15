package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.TransactionCategory;
import de.homebrewed.financemanager.external.persistance.entity.TransactionCategoryEntity;

public final class TransactionCategoryMapper {

  private TransactionCategoryMapper() {
    // Utility Klasse, keine Instanzierung
  }

  public static TransactionCategory toDomain(TransactionCategoryEntity entity) {
    if (entity == null) {
      return null;
    }
    return new TransactionCategory(entity.getId(), entity.getName());
  }
}
