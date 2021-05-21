package org.sberbank.simonov.bank.exception;

public class ImpossibleToUpdateEntityException extends RuntimeException {
    public ImpossibleToUpdateEntityException(String message) {
        super(message);
    }
}
