package br.com.ead.payments.events;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class EventModel {

    String id;

    @NotNull
    Date createdAt;

    @NotBlank
    String aggregatedIdentifier;

    @NotBlank
    String aggregateType;

    @Min(0)
    int version;

    @NotBlank
    String eventType;

    @NotNull
    BaseEvent eventData;

}
