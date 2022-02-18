package br.com.ead.sales.infrastructure;

import br.com.ead.infrastructure.EventSourcingHandler;
import br.com.ead.sales.commands.OrderPLaceCommand;
import br.com.ead.sales.model.OrderAggregate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderCommandHandler implements CommandHandler {

    EventSourcingHandler<OrderAggregate> eventSourcingHandler;

    @Override
    public void handler(OrderPLaceCommand command) {
        var aggregate = new OrderAggregate(command);
        eventSourcingHandler.save(aggregate);
    }
}
