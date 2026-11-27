package io.github.ezequiellima10.productservice.transaction.exception;

public class SameAccountTransferException extends RuntimeException {
    public SameAccountTransferException(String message) {
        super(message);
    }
}
