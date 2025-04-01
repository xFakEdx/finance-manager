package de.homebrewed.financemanager.external.persistance.repository;

import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  @Query(
      value =
          """
            UPDATE account SET balance = :newBalance WHERE id = :id
          """,
      nativeQuery = true)
  @Modifying
  void updateBalance(@Param("id") Long id, @Param("newBalance") BigDecimal newBalance);
}
