package de.cloud.fundamentals.telegramconnector.rest.dto;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

import javax.validation.constraints.NotNull;

public class RequestDetails {

    private Long chatId;
    private String message;
    private String firstName;
    private String lastName;
    private String userName;
    private String title;
    private Chat.Type chatType;

    public RequestDetails() {
    }

    public RequestDetails(@NotNull Message message) {
        Chat chat = message.chat();
        this.chatId = chat.id();
        this.message = (message.text() != null) ? message.text().toLowerCase() : "";
        this.firstName = chat.firstName();
        this.lastName = chat.lastName();
        this.userName = chat.username();
        this.title = chat.title();
        this.chatType = chat.type();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Chat.Type getChatType() {
        return chatType;
    }

    public void setChatType(Chat.Type chatType) {
        this.chatType = chatType;
    }

    @Override
    public String toString() {
        return "RequestDetails{" +
                "chatId=" + chatId +
                ", message='" + message + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", chatType=" + chatType +
                '}';
    }
}
