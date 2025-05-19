package com.telegram.core.service;

import com.telegram.core.config.TdlibClient;
import com.telegram.core.dto.ContactDto;
import com.telegram.core.session.SessionManager;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final TdlibClient tdlibClient;
    private final UserService userService;
    static TdApi.Object result;

    public ContactService(TdlibClient tdlibClient, UserService userService) {
        this.tdlibClient = tdlibClient;
        this.userService = userService;
    }

    public TdApi.Object getContacts() {
        return result;
    }

    public TdApi.Object addContact(ContactDto contactDto) {
        Client client = tdlibClient.getClient();
        TdApi.User user = userService.searchUserByPhoneNumber(contactDto.getPhoneNumber());
        if (user == null) {
            return null;
        }
        TdApi.Contact contact = new TdApi.Contact();
        contact.firstName = contactDto.getFirstName();
        contact.lastName = contactDto.getLastName();
        contact.phoneNumber = contactDto.getPhoneNumber();
        contact.vcard = contactDto.getVcard();
        contact.userId = user.id;
        client.send(new TdApi.AddContact(contact,true), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.Object object) {
                result = object;
            }
        });
        return result;
    }
}
