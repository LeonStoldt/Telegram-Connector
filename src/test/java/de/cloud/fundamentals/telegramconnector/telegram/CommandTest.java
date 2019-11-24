package de.cloud.fundamentals.telegramconnector.telegram;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void existingCommandIsRecognized() {
        //given
        String message = "Message containing the command /start. This command should be detected.";
        //when
        Command actual = Command.of(message);
        //then
        assertEquals(Command.START, actual);
    }

    @Test
    void notExistingCommandIsRecognizedAsNoCommand() {
        //given
        String message = "Message containing no known command.";
        //when
        Command actual = Command.of(message);
        //then
        assertEquals(Command.NO_COMMAND, actual);
    }

    @Test
    void commandListIsGeneratedCorrectly() {
        //when
        String actual = Command.getCommandList();

        //then
        String expected =
                "- /start (Beim Bot registrieren bzw. Bot aktivieren)\n" +
                "- /hilfe (Ausgeben aller verfügbaren Befehle)\n" +
                "- /stop (Beim Bot abmelden)\n" +
                "- /delete (Gespeicherte Daten löschen lassen)\n" +
                "- /info (Ausgeben der gesamten gespeicherten Dateien des Users)\n" +
                "- nb (Auskunft der aktuellen Fahrzeiten der Nordbahn für einen bestimmten Bahnhof)";
        assertEquals(actual, expected);
    }
}