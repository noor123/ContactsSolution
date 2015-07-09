package com.realdolmen.contacts;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

/**
 * Created by cda5732 on 30/06/2015.
 */
public class LoginForm extends CustomComponent {

    LoginFormDesign design = new LoginFormDesign();

    @DesignRoot
    protected static class LoginFormDesign extends FormLayout {
        TextField username;
        PasswordField password;
        Button login;
    }

    public LoginForm() {
        Design.read(design);
        Panel panel = new Panel();
        panel.setSizeUndefined();
        panel.setContent(design);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(panel);
        verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        setCompositionRoot(verticalLayout);

        design.login.addClickListener(event -> {
            Notification.show("Login button clicked!");
        });
    }
}
