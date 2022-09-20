package com.example.application.utility;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;



@Component
public class CookieUtility {

    public void addCookies(String name, String value) {
        HttpServletResponse cookies = (HttpServletResponse) VaadinResponse.getCurrent();
        Cookie cookie = new Cookie(name, value);
        cookies.addCookie(cookie);
    }

    public String getCookie(String name) {
        HttpServletRequest request =
                (HttpServletRequest) VaadinRequest.getCurrent();
        Cookie foundCookie = Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), name)).findFirst().orElseThrow();
        return foundCookie.getValue();
    }

}
