package com.example.application.views;

import com.example.application.model.Activity;
import com.example.application.model.enums.ActivityType;
import com.example.application.service.BackendService;
import com.example.application.utility.AES;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@PageTitle("Forex - Hesap Detayları ve işlem")
@Route(value = "details", layout = MainLayout.class)
@CssImport(
        themeFor = "vaadin-grid",
        value = "themes/myapp/grid.css"
)
public class AccountActivitiesView extends VerticalLayout {
    public AccountActivitiesView(BackendService backendService, CookieUtility cookieUtility) {
        var activitiesResponse = backendService.getAccountActivities(AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET), UUID.fromString(Objects.requireNonNull(AES.decrypt(cookieUtility.getCookie("account"), AES.SECRET))), 5);


        Select<String> itemCountSelect = new Select<>();
        itemCountSelect.setLabel("Veri Sayısı");
        itemCountSelect.setItems("5", "10", "25", "50");
        itemCountSelect.setPlaceholder("5");
        itemCountSelect.setEmptySelectionAllowed(false);

        add(itemCountSelect);


        Grid<Activity> activityGrid = new Grid<>(Activity.class, false);
        activityGrid.addColumn(activity ->
                activity.getActivityType() == ActivityType.BUY ? "+" + activity.getAmount() : "-" + activity.getAmount()
        ).setHeader("Miktar");
        activityGrid.addColumn(Activity::getActivityType).setHeader("İşlem Tipi");
        activityGrid.addColumn(Activity::getDescription).setHeader("Açıklama");
        activityGrid.setClassNameGenerator(activity -> activity.getActivityType() == ActivityType.SELL ? "warn" : "ok");
        activityGrid.setItems(activitiesResponse.getBody());

        add(activityGrid);
        itemCountSelect.addValueChangeListener(e -> activityGrid.setItems(backendService.getAccountActivities(AES.decrypt(cookieUtility.getCookie("token"), AES.SECRET), UUID.fromString(Objects.requireNonNull(AES.decrypt(cookieUtility.getCookie("account"), AES.SECRET))), Integer.valueOf(itemCountSelect.getValue())).getBody()));
    }
}
