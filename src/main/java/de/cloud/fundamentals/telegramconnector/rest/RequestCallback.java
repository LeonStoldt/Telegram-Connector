package de.cloud.fundamentals.telegramconnector.rest;

import dto.Request;

import java.net.URI;

public interface RequestCallback {

    void postRequest(Request dto, URI serviceUri);

}
