package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.dto.ActivityDto;
import com.dikkulah.accountservice.model.enums.Currency;
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
    public ResponseEntity<List<AccountDto>> getAllAccounts(Authentication authentication) {
        return ResponseEntity.ok().body(accountService.findAllAccountsByUsername(authentication.getName()));
    }

    @PostMapping("{currency}")
    public ResponseEntity<String> addAccount(Authentication authentication, @PathVariable Currency currency) {
        try {
            accountService.addAccount(authentication.getName(), currency);
            return ResponseEntity.ok().body("ACCOUNT CREATED");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("{uuid}/{count}")

    public ResponseEntity<List<ActivityDto>> getAccountActivities(Authentication authentication, @PathVariable UUID uuid, @PathVariable Integer count) {
        return ResponseEntity.ok().body(accountService.findAccountActivities(authentication.getName(), uuid, count));
    }


}
