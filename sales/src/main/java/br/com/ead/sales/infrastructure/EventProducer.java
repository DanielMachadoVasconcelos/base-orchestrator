package br.com.ead.sales.infrastructure;

import br.com.ead.sales.events.BaseEvent;

public interface EventProducer {

    void producer(String topic, BaseEvent event);
}
