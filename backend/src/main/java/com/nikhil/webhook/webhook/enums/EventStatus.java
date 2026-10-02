package com.nikhil.webhook.webhook.enums;

public enum EventStatus {

    RECEIVED,
    QUEUED,
    PROCESSING,
    PROCESSED,
    FAILED,
    RETRYING,
    DLQ
}