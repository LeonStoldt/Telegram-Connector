package de.cloud.fundamentals.telegramconnector.rest;

import de.cloud.fundamentals.telegramconnector.telegram.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    public Controller(TelegramService telegramService) {
        telegramService.start();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = "application/json;charset=utf-8")
    public String getStatus() {
        return "TelegramBot is running.";
    }
}
