package io.github.ezequiellima10.productservice.transaction.service;

import io.github.ezequiellima10.productservice.transaction.dto.TransferRequestDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransferResponseDTO;


public interface TransferService {

    TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO);
}
