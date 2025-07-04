package com.example.card.dto;

public record HrEvent(String eventType, String eventId,String timestamp,Identity identity) {}

record Identity(String value) {}