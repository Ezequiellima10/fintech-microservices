package io.github.ezequiellima10.productservice.transaction.mapper;

import io.github.ezequiellima10.productservice.transaction.dto.TransferResponseDTO;
import io.github.ezequiellima10.productservice.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public TransferResponseDTO entityToDto(Transaction transferOut, Transaction transferIn) {
        return TransferResponseDTO.builder()
                .originAccountId(transferOut.getAccountId())
                .destinationAccountId(transferIn.getAccountId())
                .amount(transferOut.getAmount())
                .description(transferOut.getDescription())
                .build();
    }
}
