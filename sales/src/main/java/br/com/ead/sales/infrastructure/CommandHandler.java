package br.com.ead.sales.infrastructure;


import br.com.ead.sales.commands.OrderPLaceCommand;

public interface CommandHandler {
    void handler(OrderPLaceCommand command);
}
