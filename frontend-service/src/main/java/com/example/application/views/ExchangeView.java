package com.example.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Forex - Exchange")
@Route(value = "exchange", layout = MainLayout.class)
@Slf4j
public class ExchangeView extends VerticalLayout {
    public ExchangeView() {

    }
}
