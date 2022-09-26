package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.dto.ActivityDto;
import com.dikkulah.accountservice.model.Activity;
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
    public ResponseEntity<AccountDto> addAccount(Authentication authentication, @PathVariable Currency currency) {
        return ResponseEntity.ok().body(accountService.addAccount(authentication.getName(), currency));
    }


    @GetMapping("{uuid}/{count}")
    public ResponseEntity<List<ActivityDto>> getAccountActivities(Authentication authentication, @PathVariable UUID uuid, @PathVariable Integer count) {
        return ResponseEntity.ok().body(accountService.findAccountActivities(authentication.getName(), uuid, count));
    }

    @PostMapping("/{accountId}/activity")
    public ResponseEntity<Boolean> addActivity(Authentication authentication, @RequestBody Activity activity,@PathVariable UUID accountId) {
        log.info(activity.toString() +  accountId);
        return ResponseEntity.ok().body(accountService.addActivity(authentication.getName(), activity,accountId));
    }


}
