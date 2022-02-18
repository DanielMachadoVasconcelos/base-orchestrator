package br.com.ead.sales.infrastructure;

import br.com.ead.sales.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregatedId);
}