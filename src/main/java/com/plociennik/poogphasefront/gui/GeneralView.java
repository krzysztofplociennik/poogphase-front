package com.plociennik.poogphasefront.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;

public class GeneralView extends SplitLayout {

    private SplitLayout top;

    public GeneralView() {
        top = new SplitLayout();
        setSizeFull();

        setOrientation(SplitLayout.Orientation.VERTICAL);
        setSizeFull();
        addToPrimary(top);
        addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        setPrimaryStyle("minHeight", "170px");
        setPrimaryStyle("maxHeight", "170px");

        top.setOrientation(SplitLayout.Orientation.VERTICAL);
        top.addToPrimary(new Button("new general toptop"));
        top.setWidthFull();
        top.setHeight("300px");
        top.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        top.setPrimaryStyle("minHeight", "110px");
        top.setPrimaryStyle("maxHeight", "110px");

        Button mainPageButton = new Button(VaadinIcon.HOME_O.create());
        mainPageButton.setWidth("200px");
        mainPageButton.setText("Main Page");
        Button postsButton = new Button(VaadinIcon.BULLETS.create());
        postsButton.setWidth("200px");
        postsButton.setText("Posts");
        Button friendsButton = new Button(VaadinIcon.GROUP.create());
        friendsButton.setWidth("200px");
        friendsButton.setText("Friends");
        Button chatButton = new Button(VaadinIcon.CHAT.create());
        chatButton.setWidth("200px");
        chatButton.setText("Chat");
        Button profileButton = new Button(VaadinIcon.USER_CARD.create());
        profileButton.setWidth("200px");
        profileButton.setText("Profile");
        Button settingsButton = new Button(VaadinIcon.COG.create());
        settingsButton.setWidth("200px");
        settingsButton.setText("Settings");

        HorizontalLayout navbarLayout = new HorizontalLayout();
        navbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navbarLayout.setWidthFull();
        navbarLayout.add(mainPageButton, postsButton, friendsButton, chatButton, profileButton, settingsButton);
        top.addToSecondary(navbarLayout);

        mainPageButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(""));
        postsButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("posts"));
        friendsButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("friends"));
        chatButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("chat"));
        profileButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("profile"));
        settingsButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate("settings"));
    }
}
