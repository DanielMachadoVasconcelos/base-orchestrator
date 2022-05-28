package br.com.ead.sales.exceptions;

public class AggregateNotFoundException extends RuntimeException{
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
