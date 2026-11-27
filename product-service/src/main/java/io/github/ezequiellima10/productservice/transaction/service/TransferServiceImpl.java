package io.github.ezequiellima10.productservice.transaction.service;

import io.github.ezequiellima10.productservice.account.dto.TransferBalancesDTO;
import io.github.ezequiellima10.productservice.account.service.AccountTransactionService;
import io.github.ezequiellima10.productservice.transaction.dto.TransferRequestDTO;
import io.github.ezequiellima10.productservice.transaction.dto.TransferResponseDTO;
import io.github.ezequiellima10.productservice.transaction.exception.SameAccountTransferException;
import io.github.ezequiellima10.productservice.transaction.mapper.TransferMapper;
import io.github.ezequiellima10.productservice.transaction.model.Transaction;
import io.github.ezequiellima10.productservice.transaction.model.TransactionType;
import io.github.ezequiellima10.productservice.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransactionRepository transactionRepository;
    private final AccountTransactionService accountTransactionService;
    private final TransferMapper transferMapper;

    @Transactional
    @Override
    public TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) {
        sameAccount(transferRequestDTO.originAccountId(), transferRequestDTO.destinationAccountId());
        TransferBalancesDTO balances = accountTransactionService.transfer(transferRequestDTO.originAccountId(), transferRequestDTO.destinationAccountId(), transferRequestDTO.amount());

        Transaction out = transactionRepository.save(new Transaction(transferRequestDTO.originAccountId(), TransactionType.TRANSFER_OUT, transferRequestDTO.amount(), balances.origin().balance(), transferRequestDTO.description()));
        Transaction in = transactionRepository.save(new Transaction(transferRequestDTO.destinationAccountId(), TransactionType.TRANSFER_IN, transferRequestDTO.amount(), balances.destination().balance(), transferRequestDTO.description()));

        return transferMapper.entityToDto(out, in);
    }

    private void sameAccount(Long AccountId1, Long AccountId2) {

        if (AccountId1.equals(AccountId2)) {
            throw new SameAccountTransferException("La cuenta de destino debe ser distinta a la de origen.");
        }
    }


}
