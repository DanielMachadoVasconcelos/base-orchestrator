package br.com.ead.sales.infrastructure;

import br.com.ead.commons.BaseEvent;

public interface EventProducer {

    void producer(String topic, BaseEvent event);
}
