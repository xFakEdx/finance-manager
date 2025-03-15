package de.homebrewed.financemanager.external.mapper;

import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;

public class AccountMapper {

  public static Account toDomain(AccountEntity entity) {
    return new Account(
        entity.getId(),
        entity.getAccountName(),
        entity.getBalance(),
        entity.getLastChanged(),
        entity.getCreatedAt());
  }
}
