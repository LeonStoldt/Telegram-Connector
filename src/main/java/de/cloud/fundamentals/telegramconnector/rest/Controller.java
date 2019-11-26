package de.cloud.fundamentals.telegramconnector.rest;

import de.cloud.fundamentals.telegramconnector.bo.Answer;
import de.cloud.fundamentals.telegramconnector.telegram.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private final TelegramService telegramService;

    @Autowired
    public Controller(TelegramService telegramService) {
        this.telegramService = telegramService;
        telegramService.start();
        telegramService.setCallback(this::postRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = JSON)
    public String getStatus() {
        return "TelegramBot is active.";
    }

    private void postRequest(String serviceBaseUri, Long chatId, String message) {
        try {
            WebClient
                    .create(serviceBaseUri)
                    .post()
                    .uri("/api")
                    .body(BodyInserters.fromValue(message))
                    .exchange()
                    .subscribe(response -> response.bodyToMono(String.class)
                            .subscribe(body -> {
                                        Answer answer = Answer.error(chatId);

                                        if (response.statusCode().equals(HttpStatus.OK)) {
                                            LOGGER.info("received response successfully");
                                            answer = new Answer(chatId, body);
                                        }
                                        telegramService.sendMessage(Objects.requireNonNull(answer));
                                    }, e -> sendErrorMessage(chatId, e)
                            ), e -> sendErrorMessage(chatId, e));
        } catch (Exception e) {
            sendErrorMessage(chatId, e);
        }
    }

    private void sendErrorMessage(Long chatId, Throwable e) {
        LOGGER.warn("could not send message due to exception", e);
        telegramService.sendMessage(Answer.error(chatId));
    }
}
