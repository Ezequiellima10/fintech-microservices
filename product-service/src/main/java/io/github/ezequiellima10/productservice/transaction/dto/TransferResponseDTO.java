package io.github.ezequiellima10.productservice.transaction.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferResponseDTO(

        Long originAccountId,

        Long destinationAccountId,

        BigDecimal amount,

        String description
) {
}
