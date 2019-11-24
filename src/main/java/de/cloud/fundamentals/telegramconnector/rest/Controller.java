package de.cloud.fundamentals.telegramconnector.rest;

import de.cloud.fundamentals.telegramconnector.bo.Answer;
import de.cloud.fundamentals.telegramconnector.telegram.TelegramService;
import dto.DataTransferObject;
import dto.Request;
import dto.Response;
import io.jsonwebtoken.JwtException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import security.JwsHelper;

import java.net.URI;
import java.util.List;

@RestController
public class Controller {

    public static final String TELEGRAM_CONNECTOR = "telegramConnector";

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String HEADER_TOKEN_KEY = "token";

    private final TelegramService telegramService;
    private final JwsHelper jwsHelper;

    @Autowired
    public Controller(TelegramService telegramService) {
        this.jwsHelper = new JwsHelper();
        this.telegramService = telegramService;
        telegramService.start();
        telegramService.setCallback(Controller.this::postRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = JSON)
    public String getStatus() {
        return "TelegramBot is active.";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/api", consumes = JSON, produces = JSON)
    public void receiveResponse(@RequestHeader HttpHeaders header, @RequestBody Response dto) {
        Answer answer;
        List<String> tokenValue = header.get(HEADER_TOKEN_KEY);

        if (tokenValue != null && !tokenValue.isEmpty()) {
            String jws = tokenValue.get(0);

            if (jwsHelper.isValid(dto, jws)) {
                try {
                    answer = new Answer(dto);
                    telegramService.sendMessage(answer);
                } catch (JwtException e) {
                    LOGGER.warn("invalid jws ({})", jws, e);
                }
            }
        }
    }

    private void postRequest(Request dto, URI serviceUri) {
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build(); //change verifier later
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            RequestEntity<Request> request = new RequestEntity<>(dto, getHeader(dto), HttpMethod.POST, serviceUri, Request.class);
            ResponseEntity<Request> response = new RestTemplate(requestFactory).exchange(request, Request.class);

            if (isStatusCodeOk(response.getStatusCodeValue())) {
                LOGGER.info("sent request with status code 200");
            } else {
                LOGGER.warn("sending failed for request {}", dto.toMap());
                //retry?
            }
        } catch (Exception e) {
            LOGGER.warn("could not send message due to exception", e);
        }
    }

    private HttpHeaders getHeader(DataTransferObject dto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add(HEADER_TOKEN_KEY, jwsHelper.generateToken(dto));
        return header;
    }

    private boolean isStatusCodeOk(int statusCode) {
        return HttpStatus.valueOf(statusCode).equals(HttpStatus.OK);
    }
}
