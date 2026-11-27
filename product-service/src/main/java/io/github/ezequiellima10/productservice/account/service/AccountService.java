package io.github.ezequiellima10.productservice.account.service;

import io.github.ezequiellima10.productservice.account.dto.AccountRequestDTO;
import io.github.ezequiellima10.productservice.account.dto.AccountRequestUpdateDTO;
import io.github.ezequiellima10.productservice.account.dto.AccountResponseDTO;
import io.github.ezequiellima10.productservice.shared.dto.ResponsePageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AccountService {

    AccountResponseDTO create(AccountRequestDTO accountRequestDTO);

    ResponsePageDTO<AccountResponseDTO> findAll(Pageable pageable);

    AccountResponseDTO findById(Long id);

    List<AccountResponseDTO> findByHolderId(Long holderId);

    AccountResponseDTO update(Long id, AccountRequestUpdateDTO accountRequestUpdateDTO);

    void delete(Long id);
}
