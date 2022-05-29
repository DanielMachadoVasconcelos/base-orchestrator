package br.com.ead.sales.controllers;

import br.com.ead.sales.commands.OrderPLaceCommand;
import br.com.ead.sales.commons.dtos.BaseResponse;
import br.com.ead.sales.commons.dtos.OrderPLaceResponse;
import br.com.ead.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    CommandDispatcher commandDispatcher;

    @PostMapping
    private BaseResponse placeOrder(@RequestBody @Valid @NotNull OrderPLaceCommand command) {
        log.info("Received a OrderPLaceCommand request. Command={}", command);
        var id = Optional.ofNullable(command.getId()).orElseGet(UUID.randomUUID()::toString);
        command.setId(id);
        commandDispatcher.send(command);
        return OrderPLaceResponse.builder()
                .id(id)
                .message("Order place request completed successfully!")
                .build();
    }
}
