package com.nikhil.webhook.webhook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record WebhookRequest(

        @NotBlank(message = "eventId is required")
        String eventId,

        @NotBlank(message = "eventType is required")
        String eventType,

        @NotBlank(message = "source is required")
        String source,

        @NotNull(message = "payload is required")
        Map<String, Object> payload
) {
}