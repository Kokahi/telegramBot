package com.example.SpringNjc.options;

import com.example.SpringNjc.config.BotCod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Slf4j
@ComponentScan
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
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                    default:
                    sendMessage(chatId, "пара уехала в Киров");


            }
        }

    }

    private void startCommandReceived(long chatId, String name)  {
        String answer = "Привет," + name + " это NJC team!";
         log.info("Replied to user" + name);

            sendMessage(chatId, answer);
    }

    private void sendMessage(long chadId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chadId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());


        }
    }
}