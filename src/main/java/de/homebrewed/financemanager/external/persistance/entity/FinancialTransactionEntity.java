package de.homebrewed.financemanager.external.persistance.entity;

import de.homebrewed.financemanager.domain.FinancialTransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class FinancialTransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "transaction_name", nullable = false, unique = true, length = 64)
  private UUID transactionName;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", nullable = false, length = 50)
  private FinancialTransactionType transactionType;

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "transaction_date", nullable = false)
  private LocalDateTime transactionDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private TransactionCategoryEntity category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private AccountEntity account;

  @Version
  @Column(name = "version", nullable = false)
  private Integer version;

  @Column(name = "cleared", nullable = false)
  private Boolean cleared = false;

  public Currency getCurrency() {
    return Currency.getInstance(currency);
  }

  public void setCurrency(Currency currency) {
    this.currency = currency.getCurrencyCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "("
        + "id = "
        + id
        + ", "
        + "transactionName = "
        + transactionName
        + ", "
        + "transactionType = "
        + transactionType
        + ", "
        + "amount = "
        + amount
        + ", "
        + "currency = "
        + currency
        + ", "
        + "transactionDate = "
        + transactionDate
        + ", "
        + "category = "
        + category
        + ", "
        + "version = "
        + version
        + ", "
        + "cleared = "
        + cleared
        + ")";
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    FinancialTransactionEntity that = (FinancialTransactionEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
