package io.proj3ct.JuhlsBot.service;

import config.BotConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.core.Context;


@SpringBootTest

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
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
            String massageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();


            switch (massageText) {
                case "/start":

                        startCommandRecived(chatID, update.getMessage().getChat().getFirstName());
                        break;
                default:

                        sendMessage(chatID, "Сорян, я не доделал");

            }
        }

    }

    private void startCommandRecived(long chatID, String name)  {


        String answer = "Привет, " + name +", как ты!";

        sendMessage(chatID, answer);
    }
    private void sendMessage(long chatId, String TextToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(TextToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e) {

        }
    }
}
