package de.cloud.fundamentals.telegramconnector.rest;

import com.pengrad.telegrambot.model.Message;
import de.cloud.fundamentals.telegramconnector.rest.dto.Answer;

public interface RequestCallback {

    Answer collectResponse(Message message);

}
