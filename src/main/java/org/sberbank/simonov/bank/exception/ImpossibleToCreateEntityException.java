package org.sberbank.simonov.bank.exception;

public class ImpossibleToCreateEntityException extends RuntimeException {
    public ImpossibleToCreateEntityException(String message) {
        super(message);
    }
}
