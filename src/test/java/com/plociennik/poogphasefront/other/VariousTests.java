package com.plociennik.poogphasefront.other;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariousTests {

    public static void main(String[] args) {

//        List<ChatMessageDto> chatLog = new ArrayList<>();
//
//        ChatMessageDto message = new ChatMessageDto();
//        message.setDateTime(LocalDateTime.of(LocalDate.of(2021, 3, 28), LocalTime.of(17, 22)));
//        message.setContent("Paul: hey there Mark!");
//        ChatMessageDto message2 = new ChatMessageDto();
//        message2.setDateTime(LocalDateTime.of(LocalDate.of(2021, 3, 29), LocalTime.of(9, 1)));
//        message2.setContent("Mark: oh hey, sorry I was busy");
//        ChatMessageDto message3 = new ChatMessageDto();
//        message3.setDateTime(LocalDateTime.of(LocalDate.of(2021, 3, 29), LocalTime.of(9, 2)));
//        message3.setContent("Mark: how are you?");
//        ChatMessageDto message4 = new ChatMessageDto();
//        message4.setDateTime(LocalDateTime.of(LocalDate.of(2021, 3, 29), LocalTime.of(9, 10)));
//        message4.setContent("Paul: im super good, thanks!");
//
//        chatLog.addAll(Arrays.asList(message, message2, message3, message4));
    }

    public static void chatTimeStamp(ChatMessageDto chatMessageDto) {
        if (Period.between(chatMessageDto.getDateTime().toLocalDate(), LocalDate.now()).getDays() > 0) {
            System.out.println(chatMessageDto.getDateTime().getDayOfMonth() +
                    " " + chatMessageDto.getDateTime().getMonth() +
                    " " + chatMessageDto.getContent());
        } else {
            System.out.println(chatMessageDto.getDateTime().getHour() +
                    ":" + chatMessageDto.getDateTime().getMinute() +
                    " " + chatMessageDto.getContent());
        }
    }
}
