package com.telegram.core.service;

import com.telegram.core.config.TdlibClient;
import com.telegram.core.session.SessionManager;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final TdlibClient tdlibClient;
    private final SessionManager sessionManager;
    private final UserService userService;
    private final ContactService contactService;
    static TdApi.Object result;


    public MessageService(TdlibClient tdlibClient, SessionManager sessionManager, UserService userService, ContactService contactService) {
        this.tdlibClient = tdlibClient;
        this.sessionManager = sessionManager;
        this.userService = userService;
        this.contactService = contactService;
    }


    public TdApi.Object getMessages(Long chatId, long[] messageIds) {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetMessages(chatId, messageIds),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getMessageProperties(Long chatId, Long messageId) {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetMessageProperties(chatId, messageId),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getMessageReadDate(Long chatId, Long messageId) {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetMessageReadDate(chatId, messageId),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Object getDatabaseStatistics() {
        TdApi.User user = null;
        Client client = tdlibClient.getClient();
        client.send(new TdApi.GetDatabaseStatistics(),
                new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
        return result;
    }

    public TdApi.Message sendMessage(Long chatId, String message) {
        Client client = tdlibClient.getClient();
        // initialize reply markup just for testing
        TdApi.InlineKeyboardButton[] row = {new TdApi.InlineKeyboardButton("https://telegram.org?1", new TdApi.InlineKeyboardButtonTypeUrl()), new TdApi.InlineKeyboardButton("https://telegram.org?2", new TdApi.InlineKeyboardButtonTypeUrl()), new TdApi.InlineKeyboardButton("https://telegram.org?3", new TdApi.InlineKeyboardButtonTypeUrl())};
        TdApi.ReplyMarkup replyMarkup = new TdApi.ReplyMarkupInlineKeyboard(new TdApi.InlineKeyboardButton[][]{row, row, row});
        TdApi.InputMessageContent content = new TdApi.InputMessageText(new TdApi.FormattedText(message, null), null, true);
        client.send(new TdApi.SendMessage(chatId, 0, null, null, replyMarkup, content), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                result = object;
            }
        });
        return (TdApi.Message) result;
    }

    public TdApi.Message sendMessage(String phoneNumber, String message) {
        Client client = tdlibClient.getClient();
        TdApi.User user = userService.getUserByNumber(phoneNumber);
        if (user == null) {
            return null;
        }
        client.send(new TdApi.CreatePrivateChat(user.id, true), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                result = object;
                TdApi.Chat chat = (TdApi.Chat) result;
                TdApi.InlineKeyboardButton[] row = {new TdApi.InlineKeyboardButton("https://telegram.org?1", new TdApi.InlineKeyboardButtonTypeUrl()), new TdApi.InlineKeyboardButton("https://telegram.org?2", new TdApi.InlineKeyboardButtonTypeUrl()), new TdApi.InlineKeyboardButton("https://telegram.org?3", new TdApi.InlineKeyboardButtonTypeUrl())};
                TdApi.ReplyMarkup replyMarkup = new TdApi.ReplyMarkupInlineKeyboard(new TdApi.InlineKeyboardButton[][]{row, row, row});
                TdApi.InputMessageContent content = new TdApi.InputMessageText(new TdApi.FormattedText(message, null), null, true);
                client.send(new TdApi.SendMessage(chat.id, 0, null, null, replyMarkup, content), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.Object object) {
                        result = object;
                    }
                });
            }
        });

        return (TdApi.Message) result;
    }

}
