package br.com.ead.infrastructure;

import br.com.ead.commons.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handle(T command);
}
