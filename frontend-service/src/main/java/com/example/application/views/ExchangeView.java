package com.example.application.views;

import com.example.application.model.Account;
import com.example.application.model.enums.ActivityType;
import com.example.application.model.enums.Currency;
import com.example.application.service.BackendService;
import com.example.application.utility.AES;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

@PageTitle("Forex - Exchange")
@Route(value = "exchange", layout = MainLayout.class)
@Slf4j
public class ExchangeView extends VerticalLayout {
    public ExchangeView(BackendService backendService, CookieUtility cookieUtility) {
        var accounts = backendService.getAccounts(AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET)).getBody();
        assert accounts != null;
        NumberField multiple = new NumberField();
        NumberField quantity = new NumberField();
        com.vaadin.flow.component.button.Button exchange = new Button();
        exchange.setText("Exchange");
        quantity.setLabel("Miktar");
        NumberField result = new NumberField();
        result.setLabel("Çevirilen Miktar");
        result.setReadOnly(true);
        Select<Account> accountTo = new Select<>();
        accountTo.setLabel("Aktarılacak Hesap");

        Select<Account> accountFrom = new Select<>();


        Select<ActivityType> activity = new Select<>();
        Select<Currency> currency = new Select<>();

        Select<Currency> currencyConvert = new Select<>();

        HorizontalLayout horizontalTop = new HorizontalLayout();
        HorizontalLayout horizontal2 = new HorizontalLayout();
        HorizontalLayout horizontal3 = new HorizontalLayout();

        activity.setLabel("Yapılacak işlem");

        currency.setItems(Currency.values());
        activity.setItems(ActivityType.values());

        multiple.setLabel("Kur");
        multiple.setReadOnly(true);
        currencyConvert.setLabel("-");
        currency.setLabel("-");
        accountFrom.setLabel("-");
        accountTo.setLabel("-");

        activity.addValueChangeListener(e -> {
            if (activity.getValue() == ActivityType.BUY) {
                currencyConvert.setLabel("Satış yapılacak Para Birimi");
                currency.setLabel("Alım yapılacak para birimi");
                accountFrom.setLabel("Aktarılacak Hesap");
                accountTo.setLabel("Satış Yapılacak Hesap");
                multiple.setLabel("Satış Kuru");

            } else if (activity.getValue() == ActivityType.SELL) {
                currencyConvert.setLabel("Alım yapılacak Para Birimi");
                currency.setLabel("Satış yapılacak para birimi");
                accountFrom.setLabel("Alım Yapılacak Hesap");
                accountTo.setLabel("Aktarılacak Hesap");
                multiple.setLabel("Alış Kuru");
            }


        });


        currency.addValueChangeListener(e -> {
            accountFrom.setItems(accounts.stream().filter(account -> account.getCurrency() == currency.getValue()).toList());
            currencyConvert.setItems(Arrays.stream(Currency.values()).filter(currency1 -> currency.getValue() != currency1).toList());
            if (currency.getValue() != null && currencyConvert.getValue() != null) {
                multiple.setValue(calculateMultiple(currency.getValue(), currencyConvert.getValue()));
            }

            horizontalTop.add(accountFrom);
        });
        currencyConvert.addValueChangeListener(e -> {
            accountTo.setItems(accounts.stream().filter(account -> currencyConvert.getValue() == account.getCurrency()).toList());
            if (currency.getValue() != null && currencyConvert.getValue() != null) {
                multiple.setValue(calculateMultiple(currency.getValue(), currencyConvert.getValue()));
            }


            horizontal3.add(quantity, multiple, result);
        });


        quantity.addValueChangeListener(e -> {
            result.setValue(quantity.getValue() * multiple.getValue());
        });
        horizontal2.add(currencyConvert, accountTo);
        horizontalTop.add(activity, currency);

        add(horizontalTop, horizontal2, horizontal3);
        add(exchange);

    }

    private Double calculateMultiple(Currency from, Currency to) {
        Random random = new Random();
        return switch (from) {
            case DOLLAR -> switch (to) {
                case TL -> random.nextDouble() * (19.0 - 17.0) + 17.0;
                case DOLLAR -> 1D;
                case EURO -> random.nextDouble() * (1.1 - 1.0) + 1.0;
                case GOLD -> random.nextDouble() * (0.02 - 0.015) + 0.015;
            };
            case TL -> switch (to) {
                case TL -> 1D;
                case DOLLAR, EURO -> random.nextDouble() * (0.06 - 0.05) + 0.05;
                case GOLD -> random.nextDouble() * (0.0012 - 0.0010) + 0.0010;
            };
            case GOLD -> switch (to) {
                case TL -> random.nextDouble() * (950. - 900.) + 900.;
                case DOLLAR, EURO -> random.nextDouble() * (53. - 50.) + 50.;
                case GOLD -> 1D;
            };
            case EURO -> switch (to) {
                case TL -> random.nextDouble() * (18.2 - 17.) + 17.;
                case DOLLAR -> random.nextDouble() * (0.99 - 0.93) + 0.93;
                case EURO -> 1D;
                case GOLD -> random.nextDouble() * (0.019D - 0.014D) + 0.014D;
            };
        };
    }
}
