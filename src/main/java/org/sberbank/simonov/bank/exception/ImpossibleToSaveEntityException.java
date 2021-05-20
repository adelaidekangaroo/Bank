package org.sberbank.simonov.bank.exception;

public class ImpossibleToSaveEntityException extends RuntimeException {
    public ImpossibleToSaveEntityException(String message) {
        super(message);
    }
}
