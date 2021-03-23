package com.plociennik.poogphasefront.gui.forms;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.ChatMessageSender;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class PossibleFriendForm extends FormLayout {
    private ApiClient apiClient;
    private SessionManager sessionManager;
    private ChatMessageSender chatMessageSender;

    @Autowired
    public PossibleFriendForm(ApiClient apiClient, UserDto possibleFriend) {
        this.apiClient = apiClient;
        this.sessionManager = new SessionManager(this.apiClient);
        this.chatMessageSender = new ChatMessageSender(this.apiClient);
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Paragraph username = new Paragraph(possibleFriend.getUsername());
        Button friendInviteButton = new Button("add friend");
        friendInviteButton.addClickListener(buttonClickEvent -> {
            try {
                addFriend(sessionManager.getLoggedInUser(), possibleFriend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button sendMessageButton = new Button("message", buttonClickEvent -> fastMessageView(possibleFriend));
        Text text = new Text("============================");

        buttonsLayout.add(friendInviteButton, sendMessageButton);
        verticalLayout.add(username, buttonsLayout, text);
        add(verticalLayout);
    }

    public void addFriend(UserDto user, UserDto friendToBe) throws IOException {
        user.getFriends().add(friendToBe.getUsername());
        apiClient.updateUser(user);
        friendToBe.getFriends().add(user.getUsername());
        apiClient.updateUser(friendToBe);
    }

    public void fastMessageView(UserDto possibleFriend) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        TextArea quickMessageTextArea = new TextArea("Your message");
        quickMessageTextArea.setWidth("250px");
        quickMessageTextArea.setHeight("150px");
        Button sendMessageButton = new Button("send", buttonClickEvent -> {
            try {
                chatMessageSender.sendMessage(sessionManager.getLoggedInUser(), possibleFriend, quickMessageTextArea.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.close();
        });
        Button closeDialogButton = new Button("cancel", buttonClickEvent -> dialog.close());

        buttonsLayout.add(sendMessageButton, closeDialogButton);
        dialogLayout.add(quickMessageTextArea, buttonsLayout);
        dialog.add(dialogLayout);
        dialog.open();

        add(dialog);
    }
}
