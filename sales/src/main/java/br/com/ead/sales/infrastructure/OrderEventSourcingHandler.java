package br.com.ead.sales.infrastructure;

import br.com.ead.sales.agregate.AggregateRoot;
import br.com.ead.sales.events.BaseEvent;
import br.com.ead.sales.model.OrderAggregate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class OrderEventSourcingHandler  implements EventSourcingHandler<OrderAggregate> {

    EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public OrderAggregate getById(String id) {
        var aggregate = new OrderAggregate();
        var events = eventStore.getEvents(id);
        if(events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
