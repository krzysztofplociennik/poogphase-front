package com.plociennik.poogphasefront.logic.validation;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

@Service
public class RegistrationValidator {
    private ApiClient apiClient;
    private UsernameValidator usernameValidator;
    private PasswordValidator passwordValidator;
    private MailValidator mailValidator;
    private DateValidator dateValidator;

    public RegistrationValidator(ApiClient apiClient,
                                 UsernameValidator usernameValidator,
                                 PasswordValidator passwordValidator,
                                 MailValidator mailValidator,
                                 DateValidator dateValidator) {
        this.apiClient = apiClient;
        this.usernameValidator = usernameValidator;
        this.passwordValidator = passwordValidator;
        this.mailValidator = mailValidator;
        this.dateValidator = dateValidator;
    }

    public boolean validateUser(UserDto userToBeRegistered, String confirmPassword) {
        if (haveFieldsBeenFilled(userToBeRegistered.getUsername(), userToBeRegistered.getPassword(), confirmPassword, userToBeRegistered.getMail()) &&
                usernameValidator.validateUsername(userToBeRegistered.getUsername()) &&
                passwordValidator.validatePassword(userToBeRegistered.getPassword(), confirmPassword) &&
                mailValidator.validateMail(userToBeRegistered.getMail()) &&
                dateValidator.validateDate(userToBeRegistered.getDateOfBirth())) {
            Notification.show("The user: " + userToBeRegistered.getUsername() + " has been registered successfully! You can reload this page in order to log in",
                    4000,
                    Notification.Position.BOTTOM_CENTER);
            return true;
        } else {
            Notification.show("Unsuccessful registration!");
            return false;
        }
    }

    public boolean haveFieldsBeenFilled(String username, String password, String confirmPassword, String mail) {
        boolean status = !username.equals("") &&
                !password.equals("") &&
                !confirmPassword.equals("") &&
                !mail.equals("");
        if (status) {
            return true;
        } else {
            Notification.show("Not all fields have been filled!");
            return false;
        }
    }
}
