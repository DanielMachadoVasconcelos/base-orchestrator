package br.com.ead.infrastructure;

import br.com.ead.commons.BaseCommand;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
