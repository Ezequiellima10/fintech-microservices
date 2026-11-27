package io.github.ezequiellima10.productservice.account.mapper;

import io.github.ezequiellima10.productservice.account.dto.AccountRequestDTO;
import io.github.ezequiellima10.productservice.account.dto.AccountResponseDTO;
import io.github.ezequiellima10.productservice.account.model.Account;
import io.github.ezequiellima10.productservice.account.model.CheckingAccount;
import io.github.ezequiellima10.productservice.account.model.SavingsAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    public AccountResponseDTO entityToResponseDTO(Account account) {
        BigDecimal overdraftLimit = account instanceof CheckingAccount ? ((CheckingAccount) account).getOverdraftLimit() : BigDecimal.ZERO;
        return AccountResponseDTO.builder().id(account.getId())
                                            .alias(account.getAlias())
                                            .accountType(account.getAccountType())
                                            .balance(account.getBalance())
                                            .overdraftLimit(overdraftLimit)
                                            .holderId(account.getHolderId())
                                            .createdAt(account.getCreatedAt())
                                            .build();
    }

    public Account toEntity(AccountRequestDTO dto) {
        return switch (dto.accountType()) {
            case SAVINGS -> new SavingsAccount(dto.holderId(), dto.alias());
            case CHECKING -> new CheckingAccount(dto.holderId(), dto.alias(), dto.overdraftLimit());
        };
    }
}
