package br.com.ead.sales.events;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
public class EventMetadata {

    String correlationId;
    String idempotentKey;

    String eventType;

    OffsetDateTime createdAt;

}
