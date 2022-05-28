package br.com.ead.sales.infrastructure;

import br.com.ead.sales.commands.BaseCommand;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
