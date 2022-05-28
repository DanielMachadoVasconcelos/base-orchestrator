package br.com.ead.sales.infrastructure;

import br.com.ead.sales.agregate.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);
    T getById(String id);
}
