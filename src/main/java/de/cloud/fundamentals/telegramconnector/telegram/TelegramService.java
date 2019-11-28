package de.cloud.fundamentals.telegramconnector.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import de.cloud.fundamentals.telegramconnector.bo.Answer;
import de.cloud.fundamentals.telegramconnector.persistence.dao.ClientDao;
import de.cloud.fundamentals.telegramconnector.rest.RequestCallback;
import de.cloud.fundamentals.telegramconnector.userfeedback.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class TelegramService extends TelegramBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramService.class);
    private static final I18n USER_FEEDBACK = new I18n();
    private static final String TOKEN = System.getenv().get("TELEGRAM_API_TOKEN");

    private final ServiceDistributor distributor;

    @Autowired
    public TelegramService(ClientDao dao) {
        super(TOKEN);
        this.distributor = new ServiceDistributor(dao);
    }

    public void setCallback(RequestCallback callback) {
        distributor.setCallback(callback);
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
                distributor.answerClient(answer, chat, messageText);
            } else {
                LOGGER.warn("received message without text: {}", message);
                answer.setMessage(USER_FEEDBACK.get("error.invalid-message"));
            }
            sendMessage(answer);
        } else {
            LOGGER.warn("received update without message: {}", update);
        }
    }

    public void sendMessage(Answer answer) {
        if (answer.isShouldSend()) {
            execute(new SendMessage(answer.getChatId(), answer.getMessage()).parseMode(ParseMode.Markdown));
        }
    }
}
