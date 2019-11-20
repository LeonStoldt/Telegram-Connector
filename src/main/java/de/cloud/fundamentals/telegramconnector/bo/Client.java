package de.cloud.fundamentals.telegramconnector.bo;

import com.pengrad.telegrambot.model.Chat;
import de.cloud.fundamentals.telegramconnector.persistence.domain.ClientEntity;

public class Client {

    private final Long chatId;
    private boolean active;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String title;
    private final Chat.Type type;

    public Client(Chat chat) {
        this.chatId = chat.id();
        this.active = true;
        this.type = chat.type();
        this.firstName = chat.firstName();
        this.lastName = chat.lastName();
        this.userName = chat.username();
        this.title = chat.title();
    }

    public Client(ClientEntity entity) {
        this.chatId = entity.getChatId();
        this.active = entity.isActive();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.userName = entity.getUserName();
        this.title = entity.getTitle();
        this.type = entity.getType();
    }

    public Long getChatId() {
        return chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public Chat.Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "chatId=" + chatId +
                ", active=" + active +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}
