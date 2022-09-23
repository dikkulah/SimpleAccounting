package com.example.application.views;

import com.example.application.model.Account;
import com.example.application.service.BackendService;
import com.example.application.utility.AES;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Forex - Exchange")
@Route(value = "exchange", layout = MainLayout.class)
@Slf4j
public class ExchangeView extends VerticalLayout {
    public ExchangeView(BackendService backendService, CookieUtility cookieUtility) {
        var accounts = backendService.getAccounts(AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET)).getBody();
        assert accounts != null;
        HorizontalLayout selectItems = new HorizontalLayout();
        Select<String> accountFrom = new Select<>();
        accountFrom.setLabel("Satış hesabı");
        accountFrom.setItems(accounts.stream().map(Account::toString).toList());
        
        Select<String> accountTo = new Select<>();
        accountTo.setLabel("Alım hesabı");
        accountTo.setItems(accounts.stream().map(Account::toString).toList());

        selectItems.add(accountFrom, accountTo);
        add(selectItems);


    }
}
