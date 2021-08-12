package com.plociennik.poogphasefront.logic.validation;
import com.plociennik.poogphasefront.client.ApiClient;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsernameValidator {
    private ApiClient apiClient;

    public UsernameValidator(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public boolean validateUsername(String username) {
        return isUsernameAvailable(username) && customUsernameValidation(username);
    }

    public boolean isUsernameAvailable(String username) {
        boolean isUsernameFree = apiClient.getUsers().stream()
                .noneMatch(userDto -> userDto.getUsername().equals(username));
        if (isUsernameFree) {
            return true;
        } else {
            Notification.show("The username is not available!");
            return false;
        }
    }

    public boolean customUsernameValidation(String username) {
        if (username == null) {
            Notification.show("The username is empty!");
            return false;
        } else {
            String regex = "^[A-Za-z]\\w{4,29}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);
            boolean result = m.matches();
            if (result) {
                return true;
            } else {
                Notification.show("The username is invalid!");
                return false;
            }
        }
    }
}
