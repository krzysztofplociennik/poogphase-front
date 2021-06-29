package com.plociennik.poogphasefront.logic;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

@Service
public class RegistrationValidator {
    private ApiClient apiClient;

    public RegistrationValidator(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void validateUser(UserDto userToBeRegistered, String confirmPassword) {
        if (!checkIfFieldsHaveBeenFilled(userToBeRegistered.getUsername(), userToBeRegistered.getPassword(), confirmPassword, userToBeRegistered.getMail())) {
            Notification.show("Not all fields have been filled!");
        } else if (!checkIfUsernameIsAvailable(userToBeRegistered.getUsername())) {
            Notification.show("The username has already been registered!");
        } else if (!checkIfPasswordsMatch(userToBeRegistered.getPassword(), confirmPassword)) {
            Notification.show("The passwords don't match!");
        } else if (!checkIfEmailIsAvailable(userToBeRegistered.getMail())) {
            Notification.show("The mail has already been registered!");
        } else {
            Notification.show("The user: " + userToBeRegistered.getUsername() + " has been registered successfully! You can reload this page in order to log in",
                    4000,
                    Notification.Position.BOTTOM_CENTER);
        }
    }

    public boolean checkIfFieldsHaveBeenFilled(String username, String password, String confirmPassword, String mail) {
        return !username.equals("") &&
                !password.equals("") &&
                !confirmPassword.equals("") &&
                !mail.equals("");
    }

    public boolean checkIfUsernameIsAvailable(String searchedUsername) {
        return apiClient.getUsers().stream()
                .noneMatch(userDto -> userDto.getUsername().equals(searchedUsername));
    }

    public boolean checkIfPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean checkIfEmailIsAvailable(String email) {
        return apiClient.getUsers().stream()
                .noneMatch(userDto -> userDto.getMail().equals(email));
    }
}
