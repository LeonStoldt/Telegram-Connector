package de.cloud.fundamentals.telegramconnector.bo;

import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;

public class Answer {

    private Long chatId;
    private String message;
    private boolean shouldSend = true;
    private static final I18n USER_FEEDBACK = new I18n();

    public Answer() {
    }

    public Answer(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public static Answer error(Long chatId) {
        return new Answer(chatId, USER_FEEDBACK.get("error.wrong-status-code"));
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
