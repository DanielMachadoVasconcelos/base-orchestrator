package br.com.ead.sales.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String errorMessage) {
        super(errorMessage);
    }
}
