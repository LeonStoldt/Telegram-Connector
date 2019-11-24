package de.cloud.fundamentals.telegramconnector.bo;

import dto.Response;

public class Answer {

    private Long chatId;
    private String message;
    private boolean shouldSend = true;

    public Answer() {
    }

    public Answer(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Answer(Response response) {
        this(response.getChatId(), response.getMessage());
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShouldSend() {
        return shouldSend;
    }

    public void setShouldSend(boolean shouldSend) {
        this.shouldSend = shouldSend;
    }
}
