package com.example.application.views;


import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.utility.CookieUtility;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout(CookieUtility cookieUtility) {
        setPrimarySection(Section.DRAWER);
        addDrawerContent(cookieUtility);
        addHeaderContent();
        if (cookieUtility.getCookie("token").equals("") || cookieUtility.getCookie("token") == null
        ) {
            UI.getCurrent().getPage().setLocation("/login");
        }
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent(CookieUtility cookieUtility) {
        H1 appName = new H1("Ufuk Forex");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter(cookieUtility));
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();
        nav.addItem(new AppNavItem("Hesaplar??m", AccountListView.class, "la la-money-check"));
        nav.addItem(new AppNavItem("Exchange", ExchangeView.class, "la la-exchange-alt"));

        return nav;
    }

    private Footer createFooter(CookieUtility cookieUtility) {
        Footer footer = new Footer();
        Button button = new Button("????k???? Yap");
        button.addClickListener(e -> {
            cookieUtility.addCookies("token", "");
            cookieUtility.addCookies("account", "");
            UI.getCurrent().getPage().setLocation("/login");
        });

        footer.add(button);
        return footer;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
