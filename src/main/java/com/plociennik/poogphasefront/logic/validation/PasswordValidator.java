package com.plociennik.poogphasefront.logic.validation;

import com.plociennik.poogphasefront.client.ApiClient;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordValidator {
    private ApiClient apiClient;

    public PasswordValidator(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public boolean validatePassword(String password, String confirmPassword) {
        return doPasswordsMatch(password, confirmPassword) && customPasswordValidation(password);
    }

    public boolean doPasswordsMatch(String password, String confirmPassword) {
        boolean status = password.equals(confirmPassword);
        if (status) {
            return true;
        } else {
            Notification.show("Passwords don't match!");
            return false;
        }
    }

    public boolean customPasswordValidation(String password) {
        if (password == null) {
            Notification.show("The password is empty!");
            return false;
        } else {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[!@#$%^&+=_()-])"
                    + "(?=\\S+$).{8,20}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            boolean result = m.matches();
            if (result) {
                return true;
            } else {
                Notification.show("The password is invalid!");
                return false;
            }
        }
    }
}
