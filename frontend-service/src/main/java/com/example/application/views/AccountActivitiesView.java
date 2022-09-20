package com.example.application.views;

import com.example.application.service.BackendService;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@PageTitle("Forex - Hesap Detayları ve işlem")
@Route(value = "details", layout = MainLayout.class)
public class AccountActivitiesView extends VerticalLayout {
    public AccountActivitiesView(BackendService backendService, CookieUtility cookieUtility) {
        var a = backendService.getAccountActivities(cookieUtility.getCookie("token"), UUID.fromString(cookieUtility.getCookie("account")), 5);


    }
}
