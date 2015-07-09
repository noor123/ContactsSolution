package com.realdolmen.contacts;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.*;

/**
 * Created by cda5732 on 30/06/2015.
 */
public class RegistrationForm extends CustomComponent {
    TextField username = new TextField("User name");
    PasswordField password = new PasswordField("Password");
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField email = new TextField("Email");

    Button register = new Button("Register");
    Button cancel = new Button("Cancel");

    private boolean valid = true;

    public RegistrationForm() {
        username.setRequired(true);
        username.setRequiredError("User name is required!");
        username.setValidationVisible(false);
        username.setNullRepresentation("");
        username.setNullSettingAllowed(true);

        password.setRequired(true);
        password.setRequiredError("Password is required!");
        password.setValidationVisible(false);
        password.setNullRepresentation("");
        password.setNullSettingAllowed(true);

        email.setRequired(true);
        email.setRequiredError("Email is required!");
        email.setValidationVisible(false);
        email.setNullRepresentation("");
        email.setNullSettingAllowed(true);
        email.addValidator(new EmailValidator("Email address is invalid!"));

        Panel panel = new Panel();
        panel.setSizeUndefined();

        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        formLayout.addComponent(username);
        formLayout.addComponent(password);
        formLayout.addComponent(firstName);
        formLayout.addComponent(lastName);
        formLayout.addComponent(email);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponent(register);
        buttons.addComponent(cancel);
        formLayout.addComponent(buttons);

        panel.setContent(formLayout);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(panel);
        verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        setCompositionRoot(verticalLayout);

        register.addClickListener(event -> {
            valid = true;
            formLayout.forEach(comp -> {
                if (comp instanceof AbstractField) {
                    AbstractField field = (AbstractField) comp;
                    field.setValidationVisible(false);
                    try {
                        field.validate();
                    } catch (Validator.InvalidValueException e) {
                        field.setValidationVisible(true);
                        valid = false;
                    }
                }
            });
            if (valid) {
                Notification.show("Form is valid! Thanks for registering!");
            }
        });

        cancel.addClickListener(event -> {
            formLayout.forEach(comp -> {
                if (comp instanceof AbstractField) {
                    AbstractField field = (AbstractField) comp;
                    field.setValue("");
                    field.setValidationVisible(false);
                }
            });
            Notification.show("Cancel button clicked!");
        });
    }
}
