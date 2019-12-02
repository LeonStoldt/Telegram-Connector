package de.cloud.fundamentals.telegramconnector.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import de.cloud.fundamentals.telegramconnector.rest.RequestCallback;
import de.cloud.fundamentals.telegramconnector.rest.dto.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TelegramService extends TelegramBot {

    private static final String TOKEN = System.getenv().get("TELEGRAM_API_TOKEN");

    private RequestCallback callback;

    @Autowired
    public TelegramService() {
        super(TOKEN);
    }

    public void setCallback(RequestCallback callback) {
        this.callback = callback;
    }

    public void start() {
        setUpdatesListener(list -> {
            list.forEach(update -> {
                Message message = null;

                if (update.message() != null) message = update.message();
                else if (update.editedMessage() != null) message = update.editedMessage();
                else if (update.channelPost() != null) message = update.channelPost();
                else if (update.editedChannelPost() != null) message = update.editedChannelPost();

                if (message != null) sendMessage(callback.collectResponse(message));
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void sendMessage(Answer answer) {
        execute(new SendMessage(answer.getChatId(), answer.getMessage()).parseMode(ParseMode.Markdown));
    }
}
