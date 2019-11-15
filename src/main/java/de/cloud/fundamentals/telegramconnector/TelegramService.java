package de.cloud.fundamentals.telegramconnector;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramService extends TelegramBot {

    private static final I18n USER_FEEDBACK = new I18n();
    private static final String TOKEN = System.getenv().get("TELEGRAM_API_TOKEN");

    @Autowired
    public TelegramService() {
        super(TOKEN);
    }

    public void start() {
        setUpdatesListener(list -> {
            list.forEach(this::onUpdateReceived);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void onUpdateReceived(Update update) {
        Message message = update.message();

        if (message != null && message.text() != null) {

            final String messageText = message.text().toLowerCase();
            final long chatId = message.chat().id();
            String name = message.chat().firstName();

            String result;

            switch (Command.of(messageText)) {
                case START:
                    result = USER_FEEDBACK.format("message.start", name);
                    break;
                case HELP:
                    result = USER_FEEDBACK.get("message.help");
                    break;
                case STOP:
                    result = USER_FEEDBACK.get("message.stop");
                    break;
                case DELETE:
                    result = USER_FEEDBACK.get("message.delete");
                    break;
                case INFO:
                    result = USER_FEEDBACK.get("message.info");
                    break;
                default:
                    result = USER_FEEDBACK.get("message.default");
            }

            execute(new SendMessage(chatId, result)
                    .parseMode(ParseMode.Markdown));
        }
    }
}
