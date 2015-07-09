package com.realdolmen.contacts;

import com.realdolmen.domain.Contact;
import com.realdolmen.domain.Group;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Container;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.ui.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by cda5732 on 1/07/2015.
 */
public class ContactTable extends CustomComponent {

    JPAContainer<Contact> contacts;
    VerticalLayout layout;
    VerticalLayout tableLayout;
    VerticalLayout contactForm;

    boolean valid = true;

    public ContactTable() {
        layout = new VerticalLayout();
        layout.setSizeFull();

        contacts = JPAContainerFactory.make(Contact.class, "contacts");
        //contacts.addEntity(new Contact("Theo", "Tester", Gender.MALE, new Date()));
        contacts.sort(new String[]{"lastName"}, new boolean[]{true});
        contacts.addNestedContainerProperty("address.city");
        contacts.addNestedContainerProperty("contactGroup.id");
        contacts.addNestedContainerProperty("contactGroup.name");

        tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();

        Table contactTable = new Table("Contacts", contacts);
        contactTable.setVisibleColumns("id", "firstName", "lastName", "gender", "birthday", "likeness", "address.city", "contactGroup.name");
        contactTable.setColumnHeader("firstName", "First Name");
        contactTable.setColumnHeader("lastName", "Last Name");
        contactTable.setColumnHeader("gender", "Gender");
        contactTable.setColumnHeader("birthday", "Birthday");
        contactTable.setColumnHeader("likeness", "Like/5");
        contactTable.setColumnHeader("address.city", "City");
        contactTable.setColumnHeader("contactGroup.name", "Group");

        contactTable.setSelectable(true);
        contactTable.setImmediate(true);


        contactTable.addValueChangeListener(event -> {
            if (event.getProperty().getValue() != null) { // handle row unselect
                Contact selected = contacts.getItem(event.getProperty().getValue()).getEntity();
                createFieldGroup(selected);
                layout.replaceComponent(tableLayout, contactForm);
            }
        });

        contactTable.setConverter("birthday", new StringToDateConverter() {
            @Override
            public DateFormat getFormat(Locale locale) {

                return new SimpleDateFormat("dd/MM/yyyy");

            }
        });

        contactTable.setSizeFull();
        tableLayout.addComponent(contactTable);

        Button newContactButton = new Button("New Contact");
        newContactButton.addClickListener(event -> {
            createFieldGroup(new Contact());
            layout.replaceComponent(tableLayout, contactForm);
        });

        tableLayout.addComponent(newContactButton);

        layout.addComponent(tableLayout);
        this.setCompositionRoot(layout);
    }

    public void addFilter(Container.Filter filter) {
        contacts.addContainerFilter(filter);
    }

    public void createFieldGroup(Contact contact) {
        BeanItem<Contact> item = new BeanItem<Contact>(contact);
        BeanFieldGroup fieldGroup = new BeanFieldGroup(Contact.class);
        fieldGroup.setItemDataSource(item);
        fieldGroup.setBuffered(true);
        Field<?> firstName = fieldGroup.buildAndBind("First name", "firstName");

        ((AbstractTextField)firstName).setNullRepresentation("");
        Field<?> lastName = fieldGroup.buildAndBind("Last name", "lastName");

        ((AbstractTextField)lastName).setNullRepresentation("");
        Field<?> gender = fieldGroup.buildAndBind("Gender", "gender", ComboBox.class);

        Field<?> birthday = fieldGroup.buildAndBind("Birthday", "birthday", DateField.class);

        ((DateField) birthday).setDateFormat("dd/MM/yyyy");
        Field<?> city = fieldGroup.buildAndBind("City", "address.city");
        ((AbstractTextField)city).setNullRepresentation("");
        JPAContainer<Group> groups = JPAContainerFactory.make(Group.class, "contacts");

        ComboBox group = new ComboBox("Group", groups);
        group.setNullSelectionAllowed(true);
        group.setNewItemsAllowed(false);
        group.setItemCaptionPropertyId("name");
        group.setConverter(new SingleSelectConverter<Group>(group));

        fieldGroup.bind(group, "contactGroup");



        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(firstName);
        formLayout.addComponent(lastName);
        formLayout.addComponent(gender);
        formLayout.addComponent(birthday);
        formLayout.addComponent(city);
        formLayout.addComponent(group);

        formLayout.forEach(comp -> {
            if (comp instanceof AbstractField) {
                AbstractField field = (AbstractField) comp;
                field.setValidationVisible(false);
            }});

        Button button = new Button("Save");

        formLayout.addComponent(button);
        formLayout.setMargin(true);
        Panel panel = new Panel(contact.getId() != null ? "Edit Contact" : "New Contact");
        panel.setSizeUndefined();
        panel.setContent(formLayout);

        contactForm = new VerticalLayout();
        contactForm.setSizeFull();
        contactForm.addComponent(panel);
        contactForm.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        button.addClickListener(event -> {
            valid = true;
            try {
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
                if(!valid) {
                    Notification.show("Some fields are invalid, please correct them...");
                    return;
                }
                fieldGroup.commit();
                contacts.addEntity(item.getBean());
                layout.replaceComponent(contactForm, tableLayout);
            } catch (FieldGroup.CommitException e) {
                Notification.show("Some fields are invalid, please correct them...");
            }

        });
    }
}
