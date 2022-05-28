package br.com.ead.infrastructure;

import br.com.ead.agregate.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);
    T getById(String id);
}
