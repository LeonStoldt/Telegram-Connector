package de.cloud.fundamentals.telegramconnector.rest.services;

public enum ResponseService {
    NORDBAHN("nordbahnService", "http://localhost:8085"); // change to load balancer domain later

    private final String name;
    private final String baseUri;

    ResponseService(String name, String baseUri) {
        this.name = name;
        this.baseUri = baseUri;
    }

    public String getName() {
        return name;
    }

    public String getBaseUri() {
        return baseUri;
    }
}
