package com.realdolmen.contacts;

import com.realdolmen.domain.Contact;
import com.realdolmen.domain.Group;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by cda5732 on 1/07/2015.
 */
public class ContactTree extends CustomComponent {

    JPAContainer<Contact> contacts;

    public ContactTree(JPAContainer<Contact> contacts) {
        this.contacts = contacts;
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(new MarginInfo(false, true, false, true));
        JPAContainer<Group> groups = JPAContainerFactory.make(Group.class, "contacts");
        groups.setParentProperty("parent");
        Tree tree = new Tree("Groups", groups);
        tree.setItemCaptionMode( AbstractSelect.ItemCaptionMode.EXPLICIT);
        tree.setItemCaptionPropertyId("name");
        groups.rootItemIds().forEach(tree::expandItemsRecursively);
        tree.setImmediate(true);

        tree.addValueChangeListener(event -> {
            contacts.removeAllContainerFilters();
            if(tree.getValue()!= null && tree.getValue().toString().equals("1")) {
                contacts.refresh();
                return;
            }
            contacts.addContainerFilter(new Compare.Equal("contactGroup.id", tree.getValue()));
            contacts.refresh();
        });

        verticalLayout.addComponent(tree);
        setCompositionRoot(verticalLayout);
    }
}
