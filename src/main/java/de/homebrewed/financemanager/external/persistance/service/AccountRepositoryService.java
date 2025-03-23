package de.homebrewed.financemanager.external.persistance.service;

import de.homebrewed.financemanager.external.acl.AccountRepositoryAcl;
import de.homebrewed.financemanager.domain.Account;
import de.homebrewed.financemanager.external.mapper.AccountMapper;
import de.homebrewed.financemanager.external.persistance.entity.AccountEntity;
import de.homebrewed.financemanager.external.persistance.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountRepositoryService implements AccountRepositoryAcl {

  private final AccountRepository accountRepository;

  @Override
  public Account save(Account account) {
    AccountEntity savedEntity = accountRepository.save(AccountMapper.toEntity(account));
    return AccountMapper.toDomain(savedEntity);
  }

  @Override
  public Account findById(Long id) {
    AccountEntity foundEntity =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Account with ID " + id + "not found"));
    return AccountMapper.toDomain(foundEntity);
  }

  @Override
  public Account updateBalance(Long id, BigDecimal balance) {
    return AccountMapper.toDomain(accountRepository.updateBalance(id, balance));
  }
}
