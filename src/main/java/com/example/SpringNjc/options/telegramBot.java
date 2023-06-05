package com.example.SpringNjc.options;

import com.example.SpringNjc.config.BotCod;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class telegramBot  extends TelegramLongPollingBot {

    final BotCod config;

    public telegramBot(BotCod config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessge(chatId, "пара уехала в Киров");


            }
        }

    }

    private void startCommand(long chatId, String name) {
        String answer = "Привет" + name + ", это NJC team!";

        sendMessge(chatId, answer);

    }

    private void sendMessge(long chadId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chadId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {


        }
    }
}