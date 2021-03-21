package com.plociennik.poogphasefront.logic;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionManager {
    private ApiClient apiClient;
    public UserDto loggedUser;

    @Autowired
    public SessionManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserDto getLoggedUser() {
        return apiClient.getUsers().stream()
                .filter(userDto -> userDto.getUsername().equals("dummy"))
                .findAny().get();
    }
}
