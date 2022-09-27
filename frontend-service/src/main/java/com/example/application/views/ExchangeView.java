package com.example.application.views;

import com.example.application.model.Account;
import com.example.application.model.Exchange;
import com.example.application.model.MultiplyRequest;
import com.example.application.model.enums.ActivityType;
import com.example.application.model.enums.Currency;
import com.example.application.service.BackendService;
import com.example.application.utility.AES;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;

@PageTitle("Forex - Exchange")
@Route(value = "exchange", layout = MainLayout.class)
@Slf4j
public class ExchangeView extends VerticalLayout {
    public ExchangeView(BackendService backendService, CookieUtility cookieUtility) {
        var token = AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET);
        var accounts = backendService.getAccounts(token).getBody();
        assert accounts != null;
        NumberField multiple = new NumberField();
        NumberField quantity = new NumberField();
        com.vaadin.flow.component.button.Button exchange = new Button();
        exchange.setText("Exchange");
        quantity.setLabel("-");
        NumberField result = new NumberField();
        result.setLabel("Çevirilen Miktar");
        result.setReadOnly(true);
        Select<Account> accountTo = new Select<>();

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
        exchange.setEnabled(false);
        activity.addValueChangeListener(e -> {
            resetValues(multiple, quantity, accountTo, accountFrom, currency, currencyConvert);
            switch (activity.getValue()) {
                case BUY -> {
                    currencyConvert.setLabel("Satış yapılacak Para Birimi");
                    currency.setLabel("Alım yapılacak para birimi");
                    accountFrom.setLabel("Aktarılacak Hesap");
                    accountTo.setLabel("Satış Yapılacak Hesap");
                    multiple.setLabel("Alış Kuru");
                    quantity.setLabel("Alınacak Miktar");
                }
                case SELL -> {
                    currencyConvert.setLabel("Alım yapılacak Para Birimi");
                    currency.setLabel("Satış yapılacak para birimi");
                    accountFrom.setLabel("Satış Yapılacak Hesap");
                    accountTo.setLabel("Aktarılacak Hesap");
                    multiple.setLabel("Satış Kuru");
                    quantity.setLabel("Satılacak Miktar");
                }
            }
        });


        currency.addValueChangeListener(e -> {
            accountFrom.setItems(accounts.stream().filter(account -> account.getCurrency() == currency.getValue()).toList());
            currencyConvert.setItems(Arrays.stream(Currency.values()).filter(currency1 -> currency.getValue() != currency1).toList());
            if ((currency.getValue() != null) && (currencyConvert.getValue() != null)) {
                setMultiply(backendService, multiple, activity, currency, currencyConvert);
            }
            horizontalTop.add(accountFrom);
        });
        currencyConvert.addValueChangeListener(e -> {
            accountTo.setItems(accounts.stream().filter(account -> currencyConvert.getValue() == account.getCurrency()).toList());
            if ((currency.getValue() != null) && (currencyConvert.getValue() != null)) {
                setMultiply(backendService, multiple, activity, currency, currencyConvert);
            }
            horizontal3.add(quantity, multiple, result);
        });
        exchange.addClickListener(e -> {
            Notification notification = new Notification();
            notification.setDuration(5000);
            if (activity.getValue() == ActivityType.BUY) {
                int x = accountTo.getValue().getAmount().compareTo(BigDecimal.valueOf(result.getValue()));
                if (!accountTo.isEmpty() && !accountFrom.isEmpty() && !quantity.isEmpty()) {
                    if (x >= 0) {
                        Exchange exchangeRequest = new Exchange();
                        exchangeRequest.setAccountFrom(accountFrom.getValue().getId());
                        exchangeRequest.setAccountTo(accountTo.getValue().getId());
                        exchangeRequest.setToken(token);
                        exchangeRequest.setQuantity(BigDecimal.valueOf(quantity.getValue()));
                        exchangeRequest.setCurrencyTo(currencyConvert.getValue());
                        exchangeRequest.setCurrencyFrom(currency.getValue());
                        exchangeRequest.setTotalAmount(BigDecimal.valueOf(result.getValue()));
                        exchangeRequest.setConversionMultiple(BigDecimal.valueOf(multiple.getValue()));
                        try {
                            backendService.doExchange(exchangeRequest);
                            notification.setText("İşlem gerekleştirildi.");
                            notification.setOpened(true);
                            UI.getCurrent().getPage().reload();

                        } catch (Exception ex) {
                            log.info(ex.getMessage());
                            notification.setText("İşlem gerçekleştirilemedi.");
                            notification.setOpened(true);
                        }
                    } else {
                        notification.setText("Heabınızdaki para yeterli değil");
                        notification.setOpened(true);
                    }
                } else {
                    notification.setText("Tüm bilgileri giriniz.");
                    notification.setOpened(true);
                }
            } else if (activity.getValue() == ActivityType.SELL) {
                int x = accountFrom.getValue().getAmount().compareTo(BigDecimal.valueOf(quantity.getValue()));
                if (!accountTo.isEmpty() && !accountFrom.isEmpty() && !quantity.isEmpty()) {
                    if (x >= 0) {
                        Exchange exchangeRequest = new Exchange();
                        exchangeRequest.setAccountFrom(accountTo.getValue().getId());
                        exchangeRequest.setAccountTo(accountFrom.getValue().getId());
                        exchangeRequest.setToken(token);
                        exchangeRequest.setQuantity(BigDecimal.valueOf(result.getValue()));
                        exchangeRequest.setCurrencyTo(currency.getValue());
                        exchangeRequest.setCurrencyFrom(currencyConvert.getValue());
                        exchangeRequest.setTotalAmount(BigDecimal.valueOf(quantity.getValue()));
                        exchangeRequest.setConversionMultiple(BigDecimal.valueOf(multiple.getValue()));
                        try {
                            backendService.doExchange(exchangeRequest);
                            notification.setText("İşlem gerekleştirildi.");
                            notification.setOpened(true);
                            UI.getCurrent().getPage().reload();
                        } catch (Exception ex) {
                            log.info(ex.getMessage());
                            notification.setText("İşlem gerçekleştirilemedi.");
                            notification.setOpened(true);
                        }
                    } else {
                        notification.setText("Heabınızdaki para yeterli değil");
                        notification.setOpened(true);
                    }
                } else {
                    notification.setText("Tüm bilgileri giriniz.");
                    notification.setOpened(true);
                }
            }

        });


        quantity.addValueChangeListener(e -> {
            result.setValue(quantity.getValue() * multiple.getValue());
        });
        horizontal2.add(currencyConvert, accountTo);
        horizontalTop.add(activity, currency);

        add(horizontalTop, horizontal2, horizontal3);
        add(exchange);

    }

    private static void resetValues(NumberField multiple, NumberField quantity, Select<Account> accountTo, Select<Account> accountFrom, Select<Currency> currency, Select<Currency> currencyConvert) {
        currencyConvert.setValue(null);
        currency.setValue(null);
        accountFrom.setValue(null);
        accountTo.setValue(null);
        multiple.setValue(null);
        ;
        quantity.setValue(null);
    }

    private void setMultiply(BackendService backendService, NumberField multiple, Select<ActivityType> activity, Select<Currency> currency, Select<Currency> currencyConvert) {
        if (activity.getValue() == ActivityType.BUY)
            multiple.setValue(backendService.getMultiply(new MultiplyRequest(currency.getValue(), currencyConvert.getValue(), ActivityType.BUY)));
        else
            multiple.setValue(backendService.getMultiply(new MultiplyRequest(currency.getValue(), currencyConvert.getValue(), ActivityType.SELL)));
    }


}
