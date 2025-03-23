package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.FinancialTransaction;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;

public class FinancialTransactionMapper {

  public static FinancialTransaction toDomain(FinancialTransactionEntity entity) {
    if (entity == null) {
      return null;
    }
    return FinancialTransaction.builder()
        .id(entity.getId())
        .transactionName(entity.getTransactionName())
        .transactionType(entity.getTransactionType())
        .amount(entity.getAmount())
        .currency(entity.getCurrency())
        .transactionDate(entity.getTransactionDate())
        .category(FinancialTransactionCategoryMapper.toDomain(entity.getCategory()))
        .accountId(entity.getAccount().getId())
        .cleared(entity.getCleared())
        .build();
  }

  public static FinancialTransactionEntity toEntity(
      FinancialTransaction domain, AccountEntity accountEntity) {
    if (domain == null || accountEntity == null) {
      return null;
    }
    return FinancialTransactionEntity.builder()
        .id(domain.id())
        .transactionName(domain.transactionName())
        .transactionType(domain.transactionType())
        .amount(domain.amount())
        .currency(domain.currency().getCurrencyCode())
        .transactionDate(domain.transactionDate())
        .category(FinancialTransactionCategoryMapper.toEntity(domain.category()))
        .account(accountEntity)
        .cleared(domain.cleared())
        .build();
  }
}
