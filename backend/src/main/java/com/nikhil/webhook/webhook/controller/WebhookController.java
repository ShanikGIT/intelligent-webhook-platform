package com.nikhil.webhook.webhook.controller;

import com.nikhil.webhook.webhook.dto.WebhookRequest;
import com.nikhil.webhook.webhook.dto.WebhookResponse;
import com.nikhil.webhook.webhook.service.WebhookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<WebhookResponse> receiveWebhook(
            @Valid @RequestBody WebhookRequest request
    ) {

        WebhookResponse response =
                webhookService.receiveWebhook(request);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }
}