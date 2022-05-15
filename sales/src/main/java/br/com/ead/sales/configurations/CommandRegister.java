package br.com.ead.sales.configurations;

import br.com.ead.sales.commands.OrderPLaceCommand;
import br.com.ead.infrastructure.CommandDispatcher;
import br.com.ead.sales.infrastructure.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CommandRegister {

    CommandDispatcher commandDispatcher;
    CommandHandler commandHandler;

    @EventListener(ApplicationReadyEvent.class)
    public void registerHandlers() {
        log.debug("Registering the Order commands handlers to the Commander Dispatcher!");
        commandDispatcher.registerHandler(OrderPLaceCommand.class, commandHandler::handler);
    }
}