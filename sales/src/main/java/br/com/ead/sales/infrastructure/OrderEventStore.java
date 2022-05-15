package br.com.ead.sales.infrastructure;

import br.com.ead.commons.BaseEvent;
import br.com.ead.sales.model.EventModel;
import br.com.ead.sales.exceptions.AggregateNotFoundException;
import br.com.ead.sales.exceptions.ConcurrencyException;
import br.com.ead.sales.model.OrderAggregate;
import br.com.ead.sales.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderEventStore implements EventStore {

    private OrderRepository repository;
    private OrderEventProducer producer;

    @Override
    public void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion) {

        var eventStream = repository.findByAggregatedIdentifier(aggregatedId);
        int latestVersion = eventStream.stream()
                .map(EventModel::getVersion)
                .max(Comparator.naturalOrder())
                .orElse(-1);

        if (expectedVersion != 1 && latestVersion != expectedVersion) {
            var errorMessage = MessageFormat.format("Mismatch on the expected version. Current version is {0}", latestVersion);
            throw new ConcurrencyException(errorMessage);
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .createdAt(Date.from(Instant.now()))
                    .aggregatedIdentifier(aggregatedId)
                    .aggregateType(OrderAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                producer.producer(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregatedId) {
        var eventStream = repository.findByAggregatedIdentifier(aggregatedId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException(MessageFormat.format("Incorrect order ID provided! No order found for ID {0}", aggregatedId));
        }
        return eventStream.stream()
                .map(EventModel::getEventData)
                .sorted(Comparator.comparing(BaseEvent::getVersion))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
