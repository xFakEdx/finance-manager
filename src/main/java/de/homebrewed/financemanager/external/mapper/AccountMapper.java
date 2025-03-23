package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;

public class AccountMapper {

  public static Account toDomain(AccountEntity entity) {
    if (entity == null) {
      return null;
    }
    return Account.builder()
        .id(entity.getId())
        .accountName(entity.getAccountName())
        .balance(entity.getBalance())
        .lastChanged(entity.getLastChanged())
        .createdAt(entity.getCreatedAt())
        .build();
  }

  public static AccountEntity toEntity(Account domain) {
    if (domain == null) {
      return null;
    }
    return AccountEntity.builder()
        .id(domain.id())
        .accountName(domain.accountName())
        .balance(domain.balance())
        .build();
  }
}
