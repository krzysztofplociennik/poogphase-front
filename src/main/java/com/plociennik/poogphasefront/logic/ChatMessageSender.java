package com.plociennik.poogphasefront.logic;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import com.plociennik.poogphasefront.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.time.LocalDateTime;

public class ChatMessageSender {
    private ApiClient apiClient;

    @Autowired
    public ChatMessageSender(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void sendMessage(UserDto author, UserDto recipient, String message) throws IOException {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setDateTime(LocalDateTime.now());
        chatMessageDto.setAuthorId(author.getId());
        chatMessageDto.setRecipient(recipient.getUsername());
        chatMessageDto.setContent(message);
        apiClient.createMessage(chatMessageDto);
    }
}
