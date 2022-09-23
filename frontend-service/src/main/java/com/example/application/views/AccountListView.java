package com.example.application.views;


import com.example.application.model.Account;
import com.example.application.model.enums.Currency;
import com.example.application.service.BackendService;
import com.example.application.utility.AES;
import com.example.application.utility.CookieUtility;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Forex - Hesaplarım")
@Route(value = "accounts", layout = MainLayout.class)
@Slf4j
public class AccountListView extends VerticalLayout {

    public AccountListView(BackendService backendService, CookieUtility cookieUtility) {
        Button createAccount = new Button("Hesap Aç");
        createAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(createAccount);
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Hesap Aç");
        VerticalLayout dialogLayout = new VerticalLayout();
        Select<String> selectCurrency = new Select<>();
        selectCurrency.setLabel("Para Birimi");
        selectCurrency.setItems(Currency.TL.toString(), Currency.GOLD.toString(), Currency.EURO.toString(), Currency.DOLLAR.toString());
        selectCurrency.setPlaceholder("Para Birimi seçiniz");
        selectCurrency.setEmptySelectionAllowed(false);

        dialogLayout.add(selectCurrency);
        dialog.add(dialogLayout);
        Button saveButton = new Button("Hesabı Aç");
        saveButton.addClickListener(buttonClickEvent -> {
            var response = backendService.createAccount(Currency.valueOf(selectCurrency.getValue())
                    , AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET)
            ).getBody();
            assert response != null;
            addAccountStrategy(cookieUtility, response);
            dialog.close();
        });
        Button cancelButton = new Button("Vazgeç", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        createAccount.addClickListener(buttonClickEvent -> dialog.open());
        add(dialog);


        // tokena ait hesapları çağırma
        var accounts = backendService.getAccounts(AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET)).getBody();
        // var olan hesapları cinsine göre component oluşturma döngüsü.
        assert accounts != null;

        for (Account account : accounts) {
            addAccountStrategy(cookieUtility, account);
        }
    }

    private void addAccountStrategy(CookieUtility cookieUtility, Account account) {
        if (account.getCurrency() == Currency.DOLLAR) {
            var card = new ClickableCard(new IconItem(new Icon(VaadinIcon.DOLLAR), "Dolar Hesabı", account.getAmount() + " $ \n" + "Hesap no: " + account.getId()), new Actions(getActionButton(cookieUtility, account)));
            card.setSizeFull();
            add(card);
        } else if (account.getCurrency() == Currency.TL) {
            var card = new ClickableCard(new IconItem(getImage("gold.png", "/META-INF/pictures/gold.png"), "Lira Hesabı", account.getAmount() + " ₺\n" + "Hesap no: " + account.getId()), new Actions(getActionButton(cookieUtility, account)));
            card.setSizeFull();
            add(card);
        } else if (account.getCurrency() == Currency.EURO) {
            var card = new ClickableCard(new IconItem(new Icon(VaadinIcon.EURO), "Euro Hesabı", account.getAmount() + " €\n" + "Hesap no: " + account.getId()), new Actions(getActionButton(cookieUtility, account)));
            card.setSizeFull();
            add(card);
        } else if (account.getCurrency() == Currency.GOLD) {
            var card = new ClickableCard(new IconItem(getImage("tl.png", "/META-INF/pictures/tl.png"), "Altın Hesabı", account.getAmount() + " gram\n" + "Hesap no: " + account.getId()), new Actions(getActionButton(cookieUtility, account)));
            card.setSizeFull();
            add(card);
        }
    }


    private ActionButton getActionButton(CookieUtility cookieUtility, Account account) {
        return new ActionButton("Hesap detayları", event -> {
            cookieUtility.addCookies("account", AES.encrypt(account.getId(), AES.SECRET));
            UI.getCurrent().getPage().setLocation("/details");
        });
    }

    private Image getImage(String name, String path) {
        StreamResource imageResource = new StreamResource(name, () -> getClass().getResourceAsStream(path));
        Image image = new Image(imageResource, name);
        image.setHeight("40px");
        image.setWidth("40px");
        return image;
    }


}
