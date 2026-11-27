package io.github.ezequiellima10.productservice.account.dto;

public record TransferBalancesDTO(

        AccountResponseDTO origin,
        AccountResponseDTO destination
) {
}
