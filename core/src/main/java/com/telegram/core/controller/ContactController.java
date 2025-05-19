package com.telegram.core.controller;

import com.telegram.core.dto.ContactDto;
import com.telegram.core.service.ContactService;
import org.drinkless.tdlib.TdApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/add")
    public TdApi.Object addContact(@RequestBody ContactDto contactDto) {
        return contactService.addContact(contactDto);
    }
}
