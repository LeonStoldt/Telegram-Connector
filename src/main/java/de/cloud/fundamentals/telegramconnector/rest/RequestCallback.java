package de.cloud.fundamentals.telegramconnector.rest;

public interface RequestCallback {

    void postRequest(String serviceBaseUri, Long chatId, String message);

}
