package io.github.ezequiellima10.productservice.transaction.service;

import io.github.ezequiellima10.productservice.account.dto.AccountResponseDTO;
import io.github.ezequiellima10.productservice.account.service.AccountTransactionService;
import io.github.ezequiellima10.productservice.shared.dto.ResponsePageDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransactionRequestDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransactionResponseDTO;
import io.github.ezequiellima10.productservice.transaction.exception.TransactionNotFoundException;
import io.github.ezequiellima10.productservice.transaction.mapper.TransactionMapper;
import io.github.ezequiellima10.productservice.transaction.model.Transaction;
import io.github.ezequiellima10.productservice.transaction.model.TransactionType;
import io.github.ezequiellima10.productservice.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountTransactionService accountTransactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponseDTO deposit(TransactionRequestDTO dto, Long accountId) {
           AccountResponseDTO accountdto = accountTransactionService.deposit(accountId, dto.amount());
            Transaction transaction = transactionRepository.save(transactionMapper.toEntity(dto, accountId, TransactionType.DEPOSIT, accountdto.balance()));

        return transactionMapper.entityToDto(transaction);
    }

    @Transactional
    @Override
    public TransactionResponseDTO withdraw(TransactionRequestDTO dto, Long accountId) {
        AccountResponseDTO accountResponseDTO = accountTransactionService.withdraw(accountId, dto.amount());
        Transaction transaction = transactionRepository.save(transactionMapper.toEntity(dto, accountId, TransactionType.WITHDRAWAL, accountResponseDTO.balance()));

        return transactionMapper.entityToDto(transaction);
    }

    @Override
    public ResponsePageDTO<TransactionResponseDTO> findTransactionHistory(Long accountId, Pageable pageable) {
        accountTransactionService.existsAccount(accountId);
        Page<TransactionResponseDTO> page = transactionRepository
                .findByAccountIdOrderByCreatedAtDesc(accountId, pageable)
                .map(transactionMapper::entityToDto);

        return new ResponsePageDTO<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public TransactionResponseDTO findById(Long id) {
        return transactionMapper.entityToDto(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("No existe el movimiento con id " + id)));
    }
}
