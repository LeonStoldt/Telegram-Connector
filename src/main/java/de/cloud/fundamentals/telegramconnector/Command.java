package de.cloud.fundamentals.telegramconnector;

import java.util.Arrays;

public enum Command {
    START("/start"),
    HELP("/hilfe"),
    STOP("/stop"),
    DELETE("/delete"),
    INFO("/info"),
    NO_COMMAND("");

    private final String keyWord;

    Command(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public static Command of(String message) {
        return Arrays
                .stream(values())
                .filter(value -> message.contains(value.getKeyWord()))
                .findFirst()
                .orElse(NO_COMMAND);
    }
}
