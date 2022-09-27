package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.dto.LoginRequest;
import com.dikkulah.accountservice.dto.TokenRequest;
import com.dikkulah.accountservice.dto.TokenResponse;
import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.service.UserService;
import com.dikkulah.accountservice.utility.JWTUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JWTUtility jwtUtility;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Kullanıcı adı ve şifre", content = @Content(schema = @Schema(implementation = TokenRequest.class))),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TokenResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }

    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Kullanıcı adı ve şifre", content = @Content(schema = @Schema(implementation = LoginRequest.class))),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class), mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping("singup")

    public ResponseEntity<User> singUp(@RequestBody User request) {
        log.info(request.toString());
        return new ResponseEntity<>(userService.save(request), HttpStatus.OK);
    }


}
