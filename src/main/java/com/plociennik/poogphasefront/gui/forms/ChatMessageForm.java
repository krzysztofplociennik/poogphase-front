package com.plociennik.poogphasefront.gui.forms;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatMessageForm extends FormLayout {
    private ApiClient apiClient;
    private SessionManager sessionManager;

    @Autowired
    public ChatMessageForm(ApiClient apiClient, ChatMessageDto chatMessageDto) {
        this.apiClient = apiClient;
        this.sessionManager = new SessionManager(this.apiClient);

        UserDto author = apiClient.getUsers().stream()
                .filter(userDto -> userDto.getId() == chatMessageDto.getAuthorId())
                .findFirst().get();

        Paragraph details = new Paragraph(author.getUsername() +
                String.format(" %02d:%02d", chatMessageDto.getDateTime().getHour(), chatMessageDto.getDateTime().getMinute()));
        Paragraph content = new Paragraph(chatMessageDto.getContent());
        Paragraph separator = new Paragraph("----------------");

        VerticalLayout verticalLayout = new VerticalLayout(details, content, separator);
        add(verticalLayout);
    }
}
