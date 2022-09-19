package com.example.application.utility;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

import static com.example.application.utility.AES.decrypt;
import static com.example.application.utility.AES.encrypt;


@Component
public class CookieUtility {

    private static final String SECRET = "finansalTeknolojiler";

    public void addCookies(String name, String value) {
        HttpServletResponse cookies = (HttpServletResponse) VaadinResponse.getCurrent();
        Cookie cookie = new Cookie(name, encrypt(value, SECRET));
        cookies.addCookie(cookie);
    }

    public String getCookie(String name) {
        HttpServletRequest request =
                (HttpServletRequest) VaadinRequest.getCurrent();
        Cookie foundCookie = Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), name)).findFirst().orElseThrow();
        return decrypt(foundCookie.getValue(), SECRET);
    }

}
