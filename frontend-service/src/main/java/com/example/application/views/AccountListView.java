package com.example.application.views;


import com.example.application.model.Account;
import com.example.application.model.enums.Currency;
import com.example.application.service.BackendService;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@PageTitle("Forex - Hesaplarım")
@Route(value = "accounts", layout = MainLayout.class)
public class AccountListView extends VerticalLayout {

    public AccountListView(BackendService backendService) throws IOException {
        //gold image
        StreamResource goldImageResource = new StreamResource("gold.png", () -> getClass().getResourceAsStream("/META-INF/pictures/gold.png"));
        Image goldImage = new Image(goldImageResource, "gold.png");
        goldImage.setHeight("40px");
        goldImage.setWidth("40px");
        //tl image
        StreamResource tlImageResource = new StreamResource("tl.png", () -> getClass().getResourceAsStream("/META-INF/pictures/tl.png"));
        Image tlImage = new Image(tlImageResource, "tl.png");
        tlImage.setHeight("40px");
        tlImage.setWidth("40px");

        // tokena ait hesapları çağırma
        var accounts = backendService.getAccounts(getTokenFromCookies()).getBody();


        // var olan hesapları cinsine göre component oluşturma döngüsü.
        assert accounts != null;
        final String ACCOUNT_DETAILS = "Hesap detayları";
        final String ACCOUNT_NO = "Hesap no: ";
        for (Account account : accounts) {
            if (account.getCurrency() == Currency.DOLLAR) {
                var card = new ClickableCard(
                        new IconItem(new Icon(VaadinIcon.DOLLAR), "Dolar Hesabı", account.getAmount() + " $ \n" +ACCOUNT_NO  + account.getId())
                        , new Actions(new ActionButton(ACCOUNT_DETAILS, event -> {

                    UI.getCurrent().getPage().setLocation("/details");
                })));
                card.setSizeFull();
                add(card);
            } else if (account.getCurrency() == Currency.TL) {
                var card = new ClickableCard(
                        new IconItem(tlImage, "Lira Hesabı", account.getAmount() + " ₺\n" + ACCOUNT_NO + account.getId()),
                        new Actions(new ActionButton(ACCOUNT_DETAILS, event -> {/* Handle Action*/})));
                card.setSizeFull();
                add(card);
            } else if (account.getCurrency() == Currency.EURO) {
                var card = new ClickableCard(
                        new IconItem(new Icon(VaadinIcon.EURO), "Euro Hesabı", account.getAmount() + " €\n" + ACCOUNT_NO + account.getId()),
                        new Actions(new ActionButton(ACCOUNT_DETAILS, event -> {/* Handle Action*/})));
                card.setSizeFull();
                add(card);
            } else if (account.getCurrency() == Currency.GOLD) {
                var card = new ClickableCard(
                        new IconItem(goldImage, "Altın Hesabı", account.getAmount() + " gram\n" + ACCOUNT_NO + account.getId()),
                        new Actions(new ActionButton(ACCOUNT_DETAILS, event -> {/* Handle Action*/})));
                card.setSizeFull();
                add(card);
            }
        }
    }

    private String getTokenFromCookies() {
        HttpServletRequest request =
                (HttpServletRequest) VaadinRequest.getCurrent();
        Cookie foundCookie = Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), "token")).findFirst().orElseThrow();
        return foundCookie.getValue();
    }
}
