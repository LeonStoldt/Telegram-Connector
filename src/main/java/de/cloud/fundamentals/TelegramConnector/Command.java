package de.cloud.fundamentals.TelegramConnector;

public enum Command {
    START("/start"),
    HELP("/hilfe"),
    STOP("/stop"),
    DELETE("/delete"),
    INFO("/info");

    final private String command;

    Command(String command) {
        this.command = command;
    }

}
