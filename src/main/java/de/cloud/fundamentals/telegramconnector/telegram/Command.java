package de.cloud.fundamentals.telegramconnector.telegram;

import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Command {
    START("/start", "description.command.start"),
    HELP("/hilfe", "description.command.help"),
    STOP("/stop", "description.command.stop"),
    DELETE("/delete", "description.command.delete"),
    INFO("/info", "description.command.info"),
    NB("nb", "description.command.nb"),
    SHORTEN_URL("/shorturl", "description.command.shorten-url"),
    NO_COMMAND("", "");

    private static final I18n USER_FEEDBACK = new I18n();

    private final String keyWord; //change to keywords later
    private final String descriptionKey;

    Command(String keyWord, String descriptionKey) {
        this.keyWord = keyWord;
        this.descriptionKey = descriptionKey;
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

    public static String getCommandList() {
        return Arrays.stream(values())
                .filter(command -> !command.equals(Command.NO_COMMAND))
                .map(Command::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "- " + keyWord + " (" + USER_FEEDBACK.get(descriptionKey) + ")";
    }
}
