package com.telegram.core.controller;

import com.telegram.core.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getMe")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @GetMapping("/getUserByNumber")
    public ResponseEntity<?> getUserByNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.getUserByNumber(phoneNumber));
    }

    @GetMapping("/getUserFullInfo")
    public ResponseEntity<?> getUserFullInfo(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserFullInfo(userId));
    }

    @GetMapping("/getUserLink")
    public ResponseEntity<?> getUserLink() {
        return ResponseEntity.ok(userService.getUserLink());
    }

    @GetMapping("/getContacts")
    public ResponseEntity<?> getContacts() {
        return ResponseEntity.ok(userService.getContacts());
    }

    @GetMapping("/getApplicationConfig")
    public ResponseEntity<?> getApplicationConfig() {
        return ResponseEntity.ok(userService.getApplicationConfig());
    }

    @GetMapping("/getActiveSessions")
    public ResponseEntity<?> getActiveSessions() {
        return ResponseEntity.ok(userService.getActiveSessions());
    }

    @GetMapping("/getTimeZones")
    public ResponseEntity<?> getTimeZones() {
        return ResponseEntity.ok(userService.getTimeZones());
    }

    @GetMapping("/getAccountTtl")
    public ResponseEntity<?> getAccountTtl() {
        return ResponseEntity.ok(userService.getAccountTtl());
    }

    @GetMapping("/getDatabaseStatistics")
    public ResponseEntity<?> getDatabaseStatistics() {
        return ResponseEntity.ok(userService.getDatabaseStatistics());
    }
}
