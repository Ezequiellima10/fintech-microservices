package io.github.ezequiellima10.productservice.account.repository;

import io.github.ezequiellima10.productservice.account.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByAlias(String alias);

    List<Account> findByHolderId(Long holderId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockById(Long id);
}
