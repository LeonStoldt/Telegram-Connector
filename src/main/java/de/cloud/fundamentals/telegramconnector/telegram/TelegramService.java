package de.cloud.fundamentals.telegramconnector.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;
import de.cloud.fundamentals.telegramconnector.bo.Answer;
import de.cloud.fundamentals.telegramconnector.bo.Client;
import de.cloud.fundamentals.telegramconnector.persistence.dao.ClientDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class TelegramService extends TelegramBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramService.class);
    private static final I18n USER_FEEDBACK = new I18n();
    private static final String TOKEN = System.getenv().get("TELEGRAM_API_TOKEN");

    private final ClientDao dao;

    @Autowired
    public TelegramService(ClientDao dao) {
        super(TOKEN);
        this.dao = dao;
    }

    public void start() {
        setUpdatesListener(list -> {
            list.forEach(this::onUpdateReceived);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void onUpdateReceived(@NotNull Update update) {
        Answer answer = new Answer();
        Message message = update.message();

        if (message != null) {
            Chat chat = message.chat();
            answer.setChatId(chat.id());
            if (message.text() != null) {
                final String messageText = message.text().toLowerCase();
                LOGGER.info("received message with chatId {} and message {}", answer.getChatId(), messageText);
                updateAnswer(answer, Command.of(messageText), chat);
            } else {
                LOGGER.warn("received message without text: {}", message);
                answer.setMessage(USER_FEEDBACK.get("error.invalid-message"));
            }
            sendMessage(answer);
        } else {
            LOGGER.warn("received update without message: {}", update);
        }
    }

    private void updateAnswer(Answer answer, Command command, Chat chat) {
        if (dao.existsById(answer.getChatId()) && isActiveClient(answer.getChatId())) {
            answerActiveClient(answer, command, chat);
        } else {
            answerInactiveOrUnknownClient(answer, command, chat);
        }
    }

    private void answerInactiveOrUnknownClient(Answer answer, Command command, Chat chat) {
        switch (command) {
            case START:
                dao.getClientById(chat.id()).ifPresentOrElse(
                        client -> dao.changeActiveState(client.getChatId()),
                        () -> registerClient(chat));
                answer.setMessage(USER_FEEDBACK.format("answer.command.start", chat.firstName()));
                break;
            case INFO:
                answer.setMessage(getInfoMessage(chat));
                break;
            case DELETE:
                deleteClient(answer, chat);
                break;
            default:
                answer.setMessage(USER_FEEDBACK.get("hint.register"));
        }
    }

    private void answerActiveClient(Answer answer, Command command, Chat chat) {
        switch (command) {
            case START:
                answer.setMessage(USER_FEEDBACK.format("answer.command.start-known", chat.firstName()));
                break;
            case HELP:
                answer.setMessage(USER_FEEDBACK.get("answer.command.help"));
                break;
            case STOP:
                dao.changeActiveState(chat.id());
                answer.setMessage(USER_FEEDBACK.get("answer.command.stop"));
                break;
            case DELETE:
                deleteClient(answer, chat);
                break;
            case INFO:
                answer.setMessage(getInfoMessage(chat));
                break;
            default:
                answer.setMessage(USER_FEEDBACK.get("answer.default"));
        }
    }

    private boolean isActiveClient(Long chatId) {
        Optional<Client> optionalClient = dao.getClientById(chatId);
        return optionalClient.isPresent() && optionalClient.get().isActive();
    }

    private void registerClient(Chat chat) {
        Client clientToRegister = new Client(chat);
        dao.persist(clientToRegister);
    }

    private void deleteClient(Answer answer, Chat chat) {
        dao.getClientById(chat.id()).ifPresentOrElse(
                client -> {
                    dao.deleteClient(client);
                    answer.setMessage(USER_FEEDBACK.get("answer.command.delete-success"));
                },
                () -> answer.setMessage(USER_FEEDBACK.get("answer.command.delete-empty"))
        );
    }

    private String getInfoMessage(Chat chat) {
        return USER_FEEDBACK.format("answer.command.info", dao.getClientAsString(chat.id()));
    }

    private void sendMessage(Answer answer) {
        execute(new SendMessage(answer.getChatId(), answer.getMessage()).parseMode(ParseMode.Markdown));
    }
}
