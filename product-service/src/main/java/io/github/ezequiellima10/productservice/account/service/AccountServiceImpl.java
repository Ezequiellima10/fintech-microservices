package io.github.ezequiellima10.productservice.account.service;

import io.github.ezequiellima10.productservice.account.dto.AccountRequestDTO;
import io.github.ezequiellima10.productservice.account.dto.AccountRequestUpdateDTO;
import io.github.ezequiellima10.productservice.account.dto.AccountResponseDTO;
import io.github.ezequiellima10.productservice.account.dto.TransferBalancesDTO;
import io.github.ezequiellima10.productservice.account.exception.AccountAlreadyExistsException;
import io.github.ezequiellima10.productservice.account.exception.AccountNotFoundException;
import io.github.ezequiellima10.productservice.account.mapper.AccountMapper;
import io.github.ezequiellima10.productservice.account.model.Account;
import io.github.ezequiellima10.productservice.account.repository.AccountRepository;
import io.github.ezequiellima10.productservice.shared.dto.ResponsePageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, AccountTransactionService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public AccountResponseDTO deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findWithLockById(accountId)
                .orElseThrow(()-> new AccountNotFoundException("No existe una cuenta con el id " + accountId));

        account.deposit(amount);

        return accountMapper.entityToResponseDTO(account);
    }

    @Transactional
    @Override
    public AccountResponseDTO withdraw(Long accountId, BigDecimal amount) {

        Account account = accountRepository.findWithLockById(accountId).orElseThrow(() -> new AccountNotFoundException("No existe una cuenta con el id " + accountId));

        account.withdraw(amount);

        return accountMapper.entityToResponseDTO(account);
    }

    @Override
    public void existsAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("No existe el account con el id " + accountId);
        }
    }

    @Transactional
    @Override
    public TransferBalancesDTO transfer(Long originId, Long destinationId, BigDecimal amount) {

        Account first  = lock(Math.min(originId, destinationId));
        Account second = lock(Math.max(originId, destinationId));
        Account origin = first.getId().equals(originId) ? first : second;
        Account destination = (origin == first) ? second : first;

        origin.withdraw(amount);
        destination.deposit(amount);

        return new TransferBalancesDTO(accountMapper.entityToResponseDTO(origin), accountMapper.entityToResponseDTO(destination));
    }

    private Account lock(Long accountId) {
        return accountRepository.findWithLockById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("No existe una cuenta con el id " + accountId));
    }

    @Override
    @Transactional
    public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {

        if (accountRepository.existsByAlias(accountRequestDTO.alias())){
            throw new AccountAlreadyExistsException("Ya existe una cuenta con el alias " + accountRequestDTO.alias());
        }

        Account account = accountMapper.toEntity(accountRequestDTO);

        return accountMapper.entityToResponseDTO(accountRepository.save(account));
    }

    @Override
    public ResponsePageDTO<AccountResponseDTO> findAll(Pageable page) {
        Page<Account> accountPage = accountRepository.findAll(page);
        Page<AccountResponseDTO> dtoPage = accountPage.map(accountMapper::entityToResponseDTO);
        return new ResponsePageDTO<>(dtoPage.getContent(), dtoPage.getNumber(), dtoPage.getSize(), dtoPage.getTotalElements(), dtoPage.getTotalPages());
    }

    @Override
    public AccountResponseDTO findById(Long id) {
        return accountMapper.entityToResponseDTO((accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("No existe la cuenta con el id: " + id))));
    }

    @Override
    public List<AccountResponseDTO> findByHolderId(Long holderId) {
        return accountRepository.findByHolderId(holderId).stream()
                .map(accountMapper::entityToResponseDTO)
                .toList();
    }

    @Transactional
    @Override
    public AccountResponseDTO update(Long id, AccountRequestUpdateDTO dto) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("no existe una cuenta con el id: " + id));

        if (!dto.alias().equals(account.getAlias()) && accountRepository.existsByAlias(dto.alias())) {
            throw new AccountAlreadyExistsException("Ya existe una cuenta con el alias " + dto.alias());
        }

        account.setAlias(dto.alias());

        return accountMapper.entityToResponseDTO(account);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        Account accountToClose = accountRepository.findWithLockById(id).orElseThrow(()-> new AccountNotFoundException("No existe la cuenta con el id: " + id));

        accountToClose.close();
    }
}
