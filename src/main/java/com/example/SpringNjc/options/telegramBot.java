package com.example.SpringNjc.options;

import com.example.SpringNjc.config.BotCod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@ComponentScan

public class telegramBot  extends TelegramLongPollingBot {

    final BotCod config;
    static final String INFO = "Всем привет!\n" +
            "Мы рады сообщить об открытии нашей группы по доставке одежды и обуви." + "\n" +
            "Список команд: \n"+ "\n"+
            "/info - Вся информаиця о нас\n"+
            "/price - Способы доставки и их стоимость + расчет заказа\n"+
            "/social - Наши соц.сети\n"+
            "/feedback - Наши отзывы\n"+
            "/chekprice - Расчет заказа\n"+
            "\n"+
            "У нас вы можете найти:\n" + "\n" +
            "⭕Разнообразные подборки товаров(Как по отдельности, так и полные аутфиты) по разумным ценам. " + "\n" +
            "⭕Единомышленников, а может и друзей, ведь специально для вас мы сделали чат, в котором вы можете обсудить последние релизы, или же просто пообщаться о чем угодно." + "\n" +
            "⭕Отзывчивость. Если вдруг, при выборе товара у вас возникнут какие-либо трудности, мы всегда готовы помочь." + "\n" +
            "⭕ Ну и конечно же различные бонусы и акции. Чтобы процесс покупки был более приятным." + "\n" +
            "\n" +
            "Желаем вам приятного шоппинга❤\n" +
            "\n" + "\n" +
            "Благодоря боту вы можете расчитать стоимость товара и задать вопросы нашему менеджуру https://t.me/evgenikobzar" + "\n" +
            "\n" +
            "Команда NJC";
    static final String SOCSET = "Мы есть в вк \n" +
            "https://vk.com/notjustclothes";

    static final String START = "Список команд: \n"+
            "/info - Вся информаиця о нас\n"+
            "/price - Способы доставки и их стоимость\n"+
            "/social - Наши соц. сети";

    static final String FEEDBACK = "\uD83D\uDD25 Отзывы нашиx клиентов \uD83D\uDD25 \n"
            + "https://vk.com/topic-218951979_49239256";

    static final String INFO_PRICE = "\uD83D\uDD25Выбираем способ доставки, их всего два\uD83D\uDD25 \n" +
            "\uD83D\uDE9B1)Способ (Обычная доставка) - Стоимость товара + доставка + наша комиссия за доставку.\n" +
            "⚪Такая доставка идет 2.5-3 недели⚪\n" +
            "\n"+
            "❗НАПРИМЕР❗ 629¥ = 8.550р (стоимость кроссовок) + 1.150р (доставка 530р/кг) + 2.000р (наша комиссия за доставку) \n" +
            "Итог:\uD83D\uDCB011.700\uD83D\uDCB0 \n" +
            "\n"+
            "\uD83D\uDE9B2)Способ (Быстрая доставка) - Стоимсоть товара + доставка +10% от стоимости заказа за быструю доставку + наша комиссия.\n" +
            "⚪Такая доставка идет 7-10 дней⚪\n" +
            "\n"+
            "❗НАПРИМЕР❗ 629¥ = 8.550р (стоимость кроссовок) + 10% = 855р + 800р (доставка 400р/кг) + 2.000р (наша комиссия за доставку)\n " +
            "Итог:\uD83D\uDCB012.555\uD83D\uDCB0" +
            "\n" +  "\n" +
            "Расичтать стоимость заказа /chekprice\n"+
            "* В эти сроки не входит время доставки товара от Poizon до склада в Китае + доставка по России, если вы делаете заказ из другого города.";


    public telegramBot(BotCod config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "го ворк"));
        listofCommands.add(new BotCommand("/info", "информация о нас"));
        listofCommands.add(new BotCommand("/social", "соц.сети где вы можете нас найти"));
        listofCommands.add(new BotCommand("/price", "как расчитывается стоимость?"));
        listofCommands.add(new BotCommand("/chekprice", "расчет стоимости"));
        listofCommands.add(new BotCommand("/feedback", "Наши отзывы"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
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

                case "/help":
                    startHelp(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/settings":
                    sendMessage(chatId,START);
                    break;

                case "/social":
                    sendMessage(chatId, SOCSET);
                    break;
                case "/price":
                    sendMessage(chatId, INFO_PRICE);
                    break;
                case "/chekprice":
                    chekprice(chatId);
                    break;

                case"/feedback":
                    sendMessage(chatId,FEEDBACK);
                    break;


                case "/info":
                    sendMessage(chatId, INFO);
                    break;
            }


        }
        else if (update.hasCallbackQuery()){
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chaiId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals("YES_BUTTON")) {
                String text = "Выберите товар\uD83D\uDCE6";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chaiId));
                message.setText(text);
                message.setMessageId((int)messageId);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rows = new ArrayList<>();
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add( InlineKeyboardButton.builder().text("Футболка\uD83D\uDC55").callbackData("button1").build());
                row1.add( InlineKeyboardButton.builder().text("Кроссовки\uD83D\uDC5F").callbackData("button2").build());
                rows.add(row1);

                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add( InlineKeyboardButton.builder().text("Штаны\uD83D\uDC56").callbackData("button3").build());
                row2.add( InlineKeyboardButton.builder().text("Сумка\uD83C\uDF92").callbackData("button6").build());
                rows.add(row2);

                List<InlineKeyboardButton> row3 = new ArrayList<>();
                row3.add( InlineKeyboardButton.builder().text("Куртка❄\uFE0F").callbackData("button4").build());
                row3.add( InlineKeyboardButton.builder().text("Худи\uD83C\uDF27").callbackData("button5").build());
                rows.add(row3);

                inlineKeyboardMarkup.setKeyboard(rows);
                message.setReplyMarkup(inlineKeyboardMarkup);


                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred:" + e.getMessage());
                }


            }

            else if(callbackData.equals("NO_BUTTON")){
                    String text = "Выберите товар\uD83D\uDCE6";
                    EditMessageText message = new EditMessageText();
                    message.setChatId(String.valueOf(chaiId));
                    message.setText(text);
                    message.setMessageId((int)messageId);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rows = new ArrayList<>();
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add( InlineKeyboardButton.builder().text("Футболка\uD83D\uDC55").callbackData("button1").build());
                row1.add( InlineKeyboardButton.builder().text("Кроссовки\uD83D\uDC5F").callbackData("button2").build());
                rows.add(row1);

                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add( InlineKeyboardButton.builder().text("Штаны\uD83D\uDC56").callbackData("button3").build());
                row2.add( InlineKeyboardButton.builder().text("Сумка\uD83C\uDF92").callbackData("button6").build());
                rows.add(row2);

                List<InlineKeyboardButton> row3 = new ArrayList<>();
                row3.add( InlineKeyboardButton.builder().text("Куртка❄\uFE0F").callbackData("button4").build());
                row3.add( InlineKeyboardButton.builder().text("Худи\uD83C\uDF27").callbackData("button5").build());
                rows.add(row3);

                inlineKeyboardMarkup.setKeyboard(rows);
                message.setReplyMarkup(inlineKeyboardMarkup);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred:" + e.getMessage());


                }

            }

            if(callbackData.equals("button1")){
                String text = "Выберите товар\uD83D\uDCE6";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chaiId));
                message.setText(text);
                message.setMessageId((int)messageId);
            }



        }

    }




    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет," + name + " это NJC team!";
        log.info("Replied to user" + name);
        sendMessage(chatId, answer);
    }


    private void startHelp(long chatId, String name){
        String answer = "Чем могу помочь? "+ name;
        log.info("Replied to user"+name);

        sendMessage(chatId,answer);
    }

    private void chekprice (long chatId ){
        SendMessage message =  new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите способ доставки \uD83D\uDCE6");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline =  new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var Yesbutton = new InlineKeyboardButton();

        Yesbutton.setText("Быстрая доставка \uD83D\uDE80");
        Yesbutton.setCallbackData("YES_BUTTON");
            

        var noBotton = new InlineKeyboardButton();
        noBotton.setText("Обычная доставка \uD83D\uDE9B");
        noBotton.setCallbackData("NO_BUTTON");

        var futb = new InlineKeyboardButton();
        futb.setText("Ds");
        futb.setCallbackData("FUT");

        rowInline.add(Yesbutton);
        rowInline.add(noBotton);

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }

    private void loft( long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("выберете что нибудь");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add( InlineKeyboardButton.builder().text("Button 1").callbackData("button1").build());
        row.add( InlineKeyboardButton.builder().text("Button 2").callbackData("button2").build());
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }


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