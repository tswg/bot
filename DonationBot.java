import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

public class DonationBot extends TelegramLongPollingBot {

    private final String token = "6285000511:AAG6X0qP17SJneTielQLfdWEGAYE61YnRrw";

    @Override
    public String getBotUsername() {
        return "GetMyMoney13Bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.isCommand() && "/start".equals(message.getText())) {
                sendGreetingMessage(message.getChatId());
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            if ("donate".equals(callbackData)) {
                editMessageWithCharityList(update);
            } else if ("support".equals(callbackData)) {
                editMessageWithTalentedPeopleList(update);
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void sendGreetingMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите действие:");
        message.setReplyMarkup(createInlineKeyboardMarkup());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton donateButton = new InlineKeyboardButton();
        donateButton.setText("Пожертвовать");
        donateButton.setCallbackData("donate");

        InlineKeyboardButton supportButton = new InlineKeyboardButton();
        supportButton.setText("Поддержать таланты");
        supportButton.setCallbackData("support");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(donateButton);
        row1.add(supportButton);

        keyboard.add(row1);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    private void editMessageWithCharityList(Update update) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessage.setText(getCharityList());

        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessageWithTalentedPeopleList(Update update) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessage.setText(getTalentedPeopleList());

        try {
            execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getCharityList() {
        return "1. Организация А - ссылка для пожертвований\n2. Организация B - ссылка для пожертвований\n3. Организация C - ссылка для пожертвований";
    }

    private String getTalentedPeopleList() {
        return "1. Талантливый человек А - ссылка для поддержки\n2. Талантливый человек B - ссылка для поддержки\n3. Талантливый человек C - ссылка для поддержки";
    }

}