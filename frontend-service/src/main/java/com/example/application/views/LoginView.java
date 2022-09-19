package com.example.application.views;

import com.example.application.model.Token;
import com.example.application.service.BackendService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Route("")
@RouteAlias("/login")
@PageTitle("Login | Ufuk Forex")
@Slf4j
public class LoginView extends Composite<LoginOverlay> {


    public LoginView(BackendService backendService) {
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setOpened(true);
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.setTitle("Ufuk Forex");
        loginOverlay.setDescription("Ufuk Dikkülah");
        loginOverlay.addLoginListener(loginEvent -> {
            ResponseEntity<Token> loginResponse;
            try {
                loginResponse = backendService.login(loginEvent.getUsername(), loginEvent.getPassword());
            } catch (Exception e) {
                loginResponse = null;
            }

            if (loginResponse == null) {
                log.info("hatalı");
                loginOverlay.setError(true);
                loginOverlay.setVisible(true);
            } else {
                Token successLogIn = loginResponse.getBody();
                encryptTokenAndAddCookies(successLogIn);
                UI.getCurrent().getPage().setLocation("/accounts");
            }
        });
    }


    private void encryptTokenAndAddCookies(Token successLogIn) {
        HttpServletResponse cookies = (HttpServletResponse) VaadinResponse.getCurrent();
        Cookie cookie = new Cookie("token", Objects.requireNonNull(successLogIn).getToken());
        cookies.addCookie(cookie);
    }


}