package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.dto.ActivityDto;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.model.enums.Currency;
import com.dikkulah.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @Operation(description = "Kullanıcıya ait hesapları listeler",
            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDto.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "SecurityConfig")})
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(Authentication authentication) {
        return ResponseEntity.ok().body(accountService.findAllAccountsByUsername(authentication.getName()));
    }

    @Operation(description = "kullanıcıya hesap ekler",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            parameters = {@Parameter(name = "currency", schema = @Schema(implementation = Currency.class), in = ParameterIn.PATH, example = "DOLLAR")},
            security = {@SecurityRequirement(name = "SecurityConfig")})
    @PostMapping("{currency}")
    public ResponseEntity<AccountDto> addAccount(Authentication authentication, @PathVariable Currency currency) {
        return ResponseEntity.ok().body(accountService.addAccount(authentication.getName(), currency));
    }

    @Operation(description = "Hesaba ait aktiviteleri listeler",
            parameters = {@Parameter(name = "count", description = "listelenecek aktivite sayısı", schema = @Schema(implementation = Integer.class)), @Parameter(name = "uuid", description = "Hesap idsi", schema = @Schema(implementation = UUID.class), in = ParameterIn.PATH)},
            responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDto.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "SecurityConfig")}
    )
    @GetMapping("{uuid}/{count}")
    public ResponseEntity<List<ActivityDto>> getAccountActivities(Authentication authentication, @PathVariable UUID uuid, @PathVariable Integer count) {
        return ResponseEntity.ok().body(accountService.findAccountActivities(authentication.getName(), uuid, count));
    }

    @Operation(description = "Hesaba aktivite ekler",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Aktivite", content = @Content(schema = @Schema(implementation = Activity.class))),
            parameters = {@Parameter(name = "uuid", description = "Hesap idsi", schema = @Schema(implementation = UUID.class), in = ParameterIn.PATH)},
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Boolean.class), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "SecurityConfig")}
    )
    @PostMapping("/{accountId}/activity")
    public ResponseEntity<Boolean> addActivity(Authentication authentication, @RequestBody Activity activity, @PathVariable UUID accountId) {
        return ResponseEntity.ok().body(accountService.addActivity(authentication.getName(), activity, accountId));
    }

    @Operation(description = "Hesabta yeterli para olup olmadığını kontrol eder.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Boolean.class), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            security = {@SecurityRequirement(name = "SecurityConfig")},
            parameters = {@Parameter(name = "uuid", description = "Hesap idsi", schema = @Schema(implementation = UUID.class), in = ParameterIn.PATH),
                    @Parameter(name = "amount", description = "Miktar ", schema = @Schema(implementation = BigDecimal.class), in = ParameterIn.PATH)}

    )
    @GetMapping("/balance/{accountId}/{amount}")
    public ResponseEntity<Boolean> checkBalance(Authentication authentication, @PathVariable UUID accountId, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok().body(accountService.checkBalance(authentication.getName(), amount, accountId));
    }


}
