package io.github.ezequiellima10.productservice.shared.exception;

import io.github.ezequiellima10.productservice.account.exception.*;
import io.github.ezequiellima10.productservice.shared.dto.ExceptionResponseDTO;
import io.github.ezequiellima10.productservice.shared.dto.ExceptionResponseFieldsDTO;
import io.github.ezequiellima10.productservice.transaction.exception.SameAccountTransferException;
import io.github.ezequiellima10.productservice.transaction.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccountNotFound(AccountNotFoundException a){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.NOT_FOUND.value() , LocalDateTime.now(), a.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccountAlreadyExists(AccountAlreadyExistsException a){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.CONFLICT.value() , LocalDateTime.now(), a.getMessage());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInsufficientFunds(InsufficientFundsException i){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.UNPROCESSABLE_CONTENT.value() , LocalDateTime.now(), i.getMessage());
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exceptionResponseDto);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidAmount(InvalidAmountException i){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.UNPROCESSABLE_CONTENT.value() , LocalDateTime.now(), i.getMessage());
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exceptionResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseFieldsDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ExceptionResponseFieldsDTO exceptionResponseFieldsDTO = new ExceptionResponseFieldsDTO(LocalDateTime.now(), ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage()).toList());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseFieldsDTO);
    }

    @ExceptionHandler(AccountClosedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccountClosed(AccountClosedException a){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.CONFLICT.value() , LocalDateTime.now(), a.getMessage());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
    }

    @ExceptionHandler(NonZeroBalanceException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNonZeroBalance(NonZeroBalanceException n){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.UNPROCESSABLE_CONTENT.value() , LocalDateTime.now(), n.getMessage());
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exceptionResponseDto);
    }

    @ExceptionHandler(SameAccountTransferException.class)
    public ResponseEntity<ExceptionResponseDTO> handleSameAccountTransfer(SameAccountTransferException s){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.UNPROCESSABLE_CONTENT.value() , LocalDateTime.now(), s.getMessage());
        return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(exceptionResponseDto);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleTransactionNotFound(TransactionNotFoundException t){
        ExceptionResponseDTO exceptionResponseDto = new ExceptionResponseDTO(HttpStatus.NOT_FOUND.value() , LocalDateTime.now(), t.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }
}


