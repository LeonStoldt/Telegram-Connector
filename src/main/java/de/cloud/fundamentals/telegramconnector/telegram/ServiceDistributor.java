package de.cloud.fundamentals.telegramconnector.telegram;

import com.pengrad.telegrambot.model.Chat;
import de.cloud.fundamentals.telegramconnector.bo.Answer;
import de.cloud.fundamentals.telegramconnector.bo.Client;
import de.cloud.fundamentals.telegramconnector.persistence.dao.ClientDao;
import de.cloud.fundamentals.telegramconnector.rest.RequestCallback;
import de.cloud.fundamentals.telegramconnector.rest.services.ResponseService;
import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceDistributor {

    private static final I18n USER_FEEDBACK = new I18n();

    private final ClientDao dao;
    private RequestCallback callback;

    public ServiceDistributor(ClientDao dao) {
        this.dao = dao;
    }

    public void setCallback(RequestCallback callback) {
        this.callback = callback;
    }

    public void answerClient(Answer answer, Chat chat, String messageText) {
        Command command = Command.of(messageText);
        Long chatId = answer.getChatId();
        answer.setChatId(chatId);

        switch (command) {
            case START:
                setStartResponse(chat, isKnownAndActiveClient(chatId), answer);
                break;
            case INFO:
                answer.setMessage(getInfoMessage(chat));
                break;
            case DELETE:
                deleteClient(answer, chat);
                break;
            default:
                if (isKnownAndActiveClient(chatId)) {
                    setServiceAnswer(answer, command, chat, messageText);
                } else {
                    answer.setMessage(USER_FEEDBACK.get("hint.register"));
                }
        }
    }

    public void deleteClient(Answer answer, Chat chat) {
        dao.getClientById(chat.id()).ifPresentOrElse(
                client -> {
                    dao.deleteClient(client);
                    answer.setMessage(USER_FEEDBACK.get("answer.command.delete-success"));
                },
                () -> answer.setMessage(USER_FEEDBACK.get("answer.command.delete-empty"))
        );
    }

    private boolean isKnownAndActiveClient(Long chatId) {
        return dao.existsById(chatId) && isActiveClient(chatId);
    }

    private boolean isActiveClient(Long chatId) {
        Optional<Client> optionalClient = dao.getClientById(chatId);
        return optionalClient.isPresent() && optionalClient.get().isActive();
    }

    private void setStartResponse(Chat chat, boolean active, Answer answer) {
        if (active) {
            answer.setMessage(USER_FEEDBACK.format("answer.command.start-known", chat.firstName()));
        } else {
            dao.getClientById(chat.id()).ifPresentOrElse(
                    client -> dao.changeActiveState(client.getChatId()),
                    () -> dao.persist(new Client(chat)));
            answer.setMessage(USER_FEEDBACK.format("answer.command.start", chat.firstName()));
        }
    }

    private String getInfoMessage(Chat chat) {
        return USER_FEEDBACK.format("answer.command.info", dao.getClientAsString(chat.id()));
    }

    private void setServiceAnswer(Answer answer, Command command, Chat chat, String messageText) {
        switch (command) {
            case HELP:
                answer.setMessage(Command.getCommandList());
                break;
            case STOP:
                dao.changeActiveState(chat.id());
                answer.setMessage(USER_FEEDBACK.get("answer.command.stop"));
                break;
            default:
                if (!command.equals(Command.NO_COMMAND)) {
                    callback.postRequest(ResponseService.uriFor(command), chat.id(), getParams(messageText, command));
                    answer.setShouldSend(false);
                } else {
                    answer.setMessage(USER_FEEDBACK.get("answer.default"));
                }
        }
    }

    private String getParams(String messageText, Command command) {
        String keyWord = command.getKeyWord();
        int indexEndOfCommand = messageText.indexOf(keyWord) + keyWord.length();
        return messageText.substring(indexEndOfCommand);
    }
}
