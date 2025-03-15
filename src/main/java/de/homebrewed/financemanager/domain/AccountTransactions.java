package de.homebrewed.financemanager.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class AccountTransactions {

  private final Account account;
  private final List<FinancialTransaction> financialTransactions;
  
  public List<FinancialTransaction> getFinancialTransactions() {
    return Collections.unmodifiableList(financialTransactions);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountTransactions that)) return false;
    return Objects.equals(account, that.account)
        && Objects.equals(financialTransactions, that.financialTransactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, financialTransactions);
  }

  @Override
  public String toString() {
    return "AccountTransactions{"
        + "account="
        + account
        + ", transactions="
        + financialTransactions
        + '}';
  }
}
