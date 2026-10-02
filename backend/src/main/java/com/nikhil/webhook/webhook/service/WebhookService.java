package com.nikhil.webhook.webhook.service;

import tools.jackson.core.exc.JacksonIOException;
import tools.jackson.databind.ObjectMapper;
import com.nikhil.webhook.webhook.dto.WebhookRequest;
import com.nikhil.webhook.webhook.dto.WebhookResponse;
import com.nikhil.webhook.webhook.entity.WebhookEvent;
import com.nikhil.webhook.webhook.enums.EventStatus;
import com.nikhil.webhook.webhook.repository.WebhookEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class WebhookService {

    private final WebhookEventRepository webhookEventRepository;
    private final ObjectMapper objectMapper;

    public WebhookService(
            WebhookEventRepository webhookEventRepository,
            ObjectMapper objectMapper
    ) {
        this.webhookEventRepository = webhookEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public WebhookResponse receiveWebhook(WebhookRequest request) {

        if (webhookEventRepository.existsByEventId(request.eventId())) {
            return new WebhookResponse(
                    request.eventId(),
                    "ALREADY_RECEIVED"
            );
        }

        try {
            String payload = objectMapper.writeValueAsString(request.payload());

            WebhookEvent event = new WebhookEvent();

            event.setEventId(request.eventId());
            event.setEventType(request.eventType());
            event.setSource(request.source());
            event.setPayload(payload);
            event.setStatus(EventStatus.RECEIVED);
            event.setRetryCount(0);
            event.setReceivedAt(OffsetDateTime.now());

            webhookEventRepository.save(event);

            return new WebhookResponse(
                    event.getEventId(),
                    "ACCEPTED"
            );

        } catch (JacksonIOException e) {
            throw new IllegalArgumentException(
                    "Unable to serialize webhook payload",
                    e
            );
        }
    }
}