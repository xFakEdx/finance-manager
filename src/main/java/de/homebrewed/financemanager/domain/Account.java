package de.homebrewed.financemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder
@RequiredArgsConstructor
@ToString
public class Account {

  private final Long id;
  private final String accountName;
  private final BigDecimal balance;
  private final LocalDateTime lastChanged;
  private final LocalDateTime createdAt;
  
  
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(id, account.id) && Objects.equals(accountName, account.accountName) && Objects.equals(balance,
            account.balance) && Objects.equals(lastChanged, account.lastChanged) && Objects.equals(createdAt,
            account.createdAt);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, accountName, balance, lastChanged, createdAt);
  }
}
