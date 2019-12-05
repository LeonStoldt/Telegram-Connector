package de.cloud.fundamentals.telegramconnector.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Message;
import de.cloud.fundamentals.telegramconnector.rest.dto.Answer;
import de.cloud.fundamentals.telegramconnector.rest.dto.RequestDetails;
import de.cloud.fundamentals.telegramconnector.telegram.TelegramService;
import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final I18n USER_FEEDBACK = new I18n();
    public static final String DISTRIBUTION_URL = "http://localhost:8080/api";

    @Autowired
    public Controller(TelegramService telegramService) {
        telegramService.start();
        telegramService.setCallback(this::collectResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String getStatus() {
        return "TelegramBot is active.";
    }

    private Answer collectResponse(Message message) {
        RequestDetails details = new RequestDetails(message);
        Answer answer = new Answer(details.getChatId());

        try {
            String requestBody = new ObjectMapper().writeValueAsString(details);

            Request request = new RequestBuilder(HttpConstants.Methods.POST)
                    .setUrl(DISTRIBUTION_URL)
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .setBody(requestBody)
                    .build();

            String responseMessage = Dsl.asyncHttpClient()
                    .executeRequest(request)
                    .toCompletableFuture()
                    .thenApply(Response::getResponseBody)
                    .exceptionally(e -> USER_FEEDBACK.get("error.wrong-status-code"))
                    .get();

            answer.setMessage(responseMessage);

        } catch (Exception e) {
            answer = Answer.error(details.getChatId());
        }
        return answer;
    }
}
