package br.com.ead.sales.infrastructure;

import br.com.ead.sales.commands.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handle(T command);
}
