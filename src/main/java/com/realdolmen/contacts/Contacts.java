package com.realdolmen.contacts;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.StyleConstants;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
@Title("Contacts")
@Theme("mytheme")
@Widgetset("com.realdolmen.contacts.MyAppWidgetset")
public class Contacts extends UI {

    ContactTable contactTable;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        setContent(verticalLayout);

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        //topLayout.setMargin(new MarginInfo(false, true, false, true));
        Button contactsButton = new Button("Contacts");
        topLayout.addComponent(contactsButton);
        Label spacer = new Label();
        spacer.setWidth("100%");
        Button registrationButton = new Button("Registration");
        Button loginButton = new Button("Login");
        contactsButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        registrationButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        loginButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        topLayout.addComponent(spacer);
        topLayout.setExpandRatio(spacer, 1f);
        topLayout.addComponent(registrationButton);
        topLayout.addComponent(loginButton);
        topLayout.setWidth("100%");
        verticalLayout.addComponent(topLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        Panel left = new Panel();
        left.setCaption("Menu");
        left.setSizeFull();
        /*VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSpacing(true);
        leftLayout.setMargin(new MarginInfo(false, true, false, true));
        left.setContent(leftLayout);
        leftLayout.addComponent(new Label("Menu"));*/

        contactTable = new ContactTable();

        left.setContent(new ContactTree(contactTable.contacts));

        Panel right = new Panel();
        right.setCaption("Content");
        right.setSizeFull();
        right.setContent(contactTable);

        horizontalLayout.addComponent(left);
        horizontalLayout.addComponent(right);

        horizontalLayout.setExpandRatio(left, 0.15f);
        horizontalLayout.setExpandRatio(right, 0.85f);

        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.setExpandRatio(horizontalLayout,1f);

        contactsButton.addClickListener(event->right.setContent(contactTable));
        registrationButton.addClickListener(event -> right.setContent(new RegistrationForm()));
        loginButton.addClickListener(event -> right.setContent(new LoginForm()));

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Contacts.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
