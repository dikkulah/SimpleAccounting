package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<AccountDto>> getAllAccounts(Authentication authentication) {
        return ResponseEntity.ok().body(accountService.findAllAccountsByUsername(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(Authentication authentication, @RequestBody Account account) {
        return ResponseEntity.ok().body(accountService.addAccount(authentication.getName(), account));
    }


    @GetMapping("{uuid}/{count}")
    public ResponseEntity<Page<Activity>> getAccountActivities(Authentication authentication, @PathVariable UUID uuid, @PathVariable Integer count) {
        return ResponseEntity.ok().body(accountService.findAccountActivities(authentication.getName(), uuid, count));
    }


}
