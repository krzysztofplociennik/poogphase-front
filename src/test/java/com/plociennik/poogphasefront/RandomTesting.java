package com.plociennik.poogphasefront;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomTesting {
    @Autowired
    private ApiClient apiClient;

    @Test
    public void size() {
        System.out.println(apiClient.getUsers().size());
        System.out.println(apiClient.getPosts().size());
    }
    
    @Test
    public void checkUsernames() {
        System.out.println("Here are the usernames in the database: \n");

        for (UserDto user : apiClient.getUsers()) {
            System.out.println(user.getUsername());
        }
    }

    @Test
    public void checkIfCertainUsernameExists() {
        String username = "dummy";
        Object obj = null;
        Optional<UserDto> obj2 = apiClient.getUsers().stream()
                .filter(userDto -> userDto.getUsername().equals(username))
                .findAny();

        System.out.println("Checking if " + username + " username is in the base: " + obj2.isPresent());

        String username2 = "dummy";
        Object obj3 = null;
        boolean obj4 = apiClient.getUsers().stream()
                .anyMatch(userDto -> userDto.getUsername().equals(username2));
        System.out.println("Checking if " + username2 + " username is in the base: " + obj4);

    }

    @Test
    public void checkIfCertainMailExists() {
        String mail = "gleefulgluten@myplace.com";
        System.out.println("Checking if " + mail + " mail is in the base: " + apiClient.getUsers().stream()
            .anyMatch(userDto -> userDto.getMail().equals(mail)));
    }
}
