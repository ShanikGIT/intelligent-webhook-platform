package com.nikhil.webhook.webhook.dto;

public record WebhookResponse(
        String eventId,
        String status
) {
}