package io.github.ezequiellima10.productservice.transaction.dto;

import io.github.ezequiellima10.productservice.transaction.model.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponseDTO(

        Long id,

        Long accountId,

        TransactionType type,

        BigDecimal amount,

        BigDecimal balanceAfter,

        String description,

        LocalDateTime createdAt) {
}
