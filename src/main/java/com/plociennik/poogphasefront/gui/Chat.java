package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.gui.forms.ChatMessageForm;
import com.plociennik.poogphasefront.logic.ChatMessageSender;
import com.plociennik.poogphasefront.logic.FriendsManager;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route("chat")
@PageTitle("Chat")
@UIScope
public class Chat extends HorizontalLayout {
    private GeneralView generalView;
    private SplitLayout chatPageContentLayout;
    private ApiClient apiClient;
    private SessionManager sessionManager;
    private ChatMessageSender chatMessageSender;

    @Autowired
    public Chat(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.sessionManager = new SessionManager(this.apiClient);
        this.chatMessageSender = new ChatMessageSender(this.apiClient);

        setSizeFull();
        setupChatPageContentView();
        pickUserView();
    }

    public void setupChatPageContentView() {
        this.generalView = new GeneralView();
        this.chatPageContentLayout = new SplitLayout();

        chatPageContentLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        chatPageContentLayout.setPrimaryStyle("minWidth", "450px");
        chatPageContentLayout.setPrimaryStyle("maxWidth", "450px");
        generalView.addToSecondary(chatPageContentLayout);
        add(generalView);
    }

    public void pickUserView() {
        FriendsManager friendsManager = new FriendsManager(this.apiClient);
        VerticalLayout pickUserLayout = new VerticalLayout();
        HorizontalLayout searchUserLayout = new HorizontalLayout();

        TextField searchUserTextField = new TextField();
        searchUserTextField.setPlaceholder("type username");
        Icon searchIcon = new Icon(VaadinIcon.ANGLE_RIGHT);
        searchIcon.addClickListener(iconClickEvent -> searchForUser(searchUserTextField.getValue()));
        H4 pickUserText = new H4("You can search for someone to send them a message");
        H4 friendPickText = new H4("Or you can pick someone from your friend list");

        searchUserLayout.add(searchUserTextField, searchIcon);
        searchUserLayout.setAlignItems(Alignment.CENTER);
        pickUserLayout.add(pickUserText, searchUserLayout, friendPickText);

        for (UserDto friend : friendsManager.searchFriends(sessionManager.getLoggedUser())) {
            pickUserLayout.add(new Button(friend.getUsername(), buttonClickEvent -> chatWindowView(friend)));
        }

        chatPageContentLayout.addToPrimary(pickUserLayout);
    }

    public void chatWindowView(UserDto selectedUser) {
        VerticalLayout chatWindowLayout = new VerticalLayout();
        VerticalLayout messagesListLayout = new VerticalLayout();
        HorizontalLayout sendMessageLayout = new HorizontalLayout();
        SplitLayout windowSplitLayout = new SplitLayout();

        TextField writeMessageTextField = new TextField();
        Button sendMessageButton = new Button("send");

        windowSplitLayout.addToPrimary(messagesListLayout);
        windowSplitLayout.addToSecondary(sendMessageLayout);
        windowSplitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        windowSplitLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        windowSplitLayout.setSizeFull();
        windowSplitLayout.setPrimaryStyle("minHeight", "450px");
        windowSplitLayout.setPrimaryStyle("maxHeight", "450px");

        sendMessageLayout.add(writeMessageTextField, sendMessageButton);
        sendMessageLayout.setWidthFull();

        messagesListLayout.setWidthFull();
        chatWindowLayout.add(windowSplitLayout);

        chatPageContentLayout.addToSecondary(chatWindowLayout);

        UserDto loggedUser = apiClient.getUsers().stream()
                .filter(userDto -> userDto.getUsername().equals("dummy"))
                .findAny()
                .get();

        List<ChatMessageDto> chatLog = loggedUser.getMessages().stream()
                .filter(chatMessageDto -> chatMessageDto.getRecipient().equals(selectedUser.getUsername()))
                .collect(Collectors.toList());
        chatLog.addAll(selectedUser.getMessages().stream()
                .filter(chatMessageDto -> chatMessageDto.getRecipient().equals(loggedUser.getUsername()))
                .collect(Collectors.toList()));

        chatLog = chatLog.stream().sorted(Comparator.comparing(ChatMessageDto::getDateTime))
                .collect(Collectors.toList());

        if (chatLog.size() == 0) {
            messagesListLayout.add(new H4("Well, there's actually nothing here..."));
        } else {
            for (ChatMessageDto message : chatLog) {
                messagesListLayout.add(new ChatMessageForm(this.apiClient, message));
            }
        }

        writeMessageTextField.setWidth("900px");
        sendMessageButton.addClickShortcut(Key.ENTER);
        sendMessageButton.addClickListener(buttonClickEvent -> {
            try {
                chatMessageSender.sendMessage(loggedUser, selectedUser, writeMessageTextField.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatWindowView(selectedUser);
        });
    }

    public void searchForUser(String searchedPhrase) {
        Button cancelButton = new Button("go back", buttonClickEvent -> pickUserView());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        VerticalLayout layout = new VerticalLayout();
        List<UserDto> searchedUserList = apiClient.getUsers().stream()
                .filter(userDto -> userDto.getUsername().contains(searchedPhrase))
                .filter(userDto -> !userDto.getUsername().equals(sessionManager.getLoggedUser().getUsername()))
                .collect(Collectors.toList());

        if (searchedUserList.size() != 0) {
            layout.add(cancelButton);
            for (UserDto resultUser : searchedUserList) {
                layout.add(new Button(resultUser.getUsername(), buttonClickEvent -> chatWindowView(resultUser)));
            }
        } else {
            layout.add(new H5("Sorry, it seems that there are no users with that username"), cancelButton);
        }
        chatPageContentLayout.addToPrimary(layout);
    }
}

