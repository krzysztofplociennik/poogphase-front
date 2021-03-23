package com.plociennik.poogphasefront.logic;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private ApiClient apiClient;
    public UserDto loggedUser;

    @Autowired
    public SessionManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserDetails getUserDetailsOfLoggedInUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserDto getLoggedInUser() {
        return apiClient.getUsers().stream()
                .filter(user -> user.getUsername().equals(getUserDetailsOfLoggedInUser().getUsername()))
                .findAny().get();
    }
}
