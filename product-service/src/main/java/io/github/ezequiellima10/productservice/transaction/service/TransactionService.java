package io.github.ezequiellima10.productservice.transaction.service;

import io.github.ezequiellima10.productservice.shared.dto.ResponsePageDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransactionRequestDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransactionResponseDTO;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponseDTO deposit(TransactionRequestDTO dto,  Long accountId);

    TransactionResponseDTO withdraw(TransactionRequestDTO dto, Long accountId);

    ResponsePageDTO<TransactionResponseDTO> findTransactionHistory(Long accountId, Pageable pageable);

    TransactionResponseDTO findById(Long id);
}
