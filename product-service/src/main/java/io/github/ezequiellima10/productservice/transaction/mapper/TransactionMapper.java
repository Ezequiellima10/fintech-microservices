package io.github.ezequiellima10.productservice.transaction.mapper;

import io.github.ezequiellima10.productservice.transaction.dto.TransactionRequestDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransactionResponseDTO;
import io.github.ezequiellima10.productservice.transaction.model.Transaction;
import io.github.ezequiellima10.productservice.transaction.model.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionMapper {

    public TransactionResponseDTO entityToDto(Transaction entity) {
        return TransactionResponseDTO.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId())
                .type(entity.getType())
                .amount(entity.getAmount())
                .balanceAfter(entity.getBalanceAfter())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public Transaction toEntity(TransactionRequestDTO dto, Long accountId, TransactionType type, BigDecimal balanceAfter) {
        return new Transaction(accountId, type, dto.amount(), balanceAfter, dto.description());
    }
}
