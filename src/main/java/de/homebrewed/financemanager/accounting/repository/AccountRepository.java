package de.homebrewed.financemanager.accounting.repository;

import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
