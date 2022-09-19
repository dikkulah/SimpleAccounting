package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(Authentication authentication) {
        return ResponseEntity.ok().body(accountService.findAllAccountsByUsername(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(Authentication authentication, @RequestBody Account account) {
        return ResponseEntity.ok().body(accountService.addAccount(authentication.getName(), account));
    }


    @GetMapping("{uuid}")
    public ResponseEntity<List<Activity>> getAccountActivities(Authentication authentication, @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(accountService.findAccountActivities(authentication.getName(), uuid));
    }


}
