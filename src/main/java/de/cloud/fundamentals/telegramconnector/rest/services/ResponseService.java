package de.cloud.fundamentals.telegramconnector.rest.services;

import java.net.URI;

public enum ResponseService {
    NORDBAHN("nordbahnService", "https://localhost:8443/api"); // change to load balancer domain later


    private final String name;
    private final URI uri;


    ResponseService(String name, String uri) {
        this.name = name;
        this.uri = URI.create(uri);
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return uri;
    }
}
