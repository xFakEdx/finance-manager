package de.homebrewed.financemanager.external.persistance.repository;

import de.homebrewed.financemanager.external.persistance.entity.FinancialTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository
    extends JpaRepository<FinancialTransactionEntity, Long> {

  @Query(
      value =
          """
                      UPDATE transaction SET cleared = :cleared WHERE id = :id
                    """,
      nativeQuery = true)
  @Modifying
  FinancialTransactionEntity updateCleared(@Param("id") Long id, @Param("cleared") Boolean cleared);
}
