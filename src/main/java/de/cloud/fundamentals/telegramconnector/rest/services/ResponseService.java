package de.cloud.fundamentals.telegramconnector.rest.services;

import de.cloud.fundamentals.telegramconnector.telegram.Command;

import java.util.Arrays;

public enum ResponseService {
    NORDBAHN("http://localhost:8085", Command.NB),
    URL_SHORTENER("http://localhost:8090", Command.SHORTEN_URL); // change to load balancer domain later

    private final String baseUri;
    private final Command command;

    ResponseService(String baseUri, Command command) {
        this.baseUri = baseUri;
        this.command = command;
    }

    public static String uriFor(Command command) {
        return Arrays.stream(values())
                .filter(service -> service.getCommand().equals(command))
                .findFirst()
                .map(ResponseService::getBaseUri)
                .orElse("");
    }

    public String getBaseUri() {
        return baseUri;
    }

    public Command getCommand() {
        return command;
    }
}
