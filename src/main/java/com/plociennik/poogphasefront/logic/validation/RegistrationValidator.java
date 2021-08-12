package com.plociennik.poogphasefront.logic.validation;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class RegistrationValidator {
    private ApiClient apiClient;
    private UsernameValidator usernameValidator;
    private PasswordValidator passwordValidator;
    private MailValidator mailValidator;

    public RegistrationValidator(ApiClient apiClient,
                                 UsernameValidator usernameValidator,
                                 PasswordValidator passwordValidator,
                                 MailValidator mailValidator) {
        this.apiClient = apiClient;
        this.usernameValidator = usernameValidator;
        this.passwordValidator = passwordValidator;
        this.mailValidator = mailValidator;
    }

    public boolean validateUser(UserDto userToBeRegistered, String confirmPassword) {
        boolean validationStatus = false;
        if (!haveFieldsBeenFilled(userToBeRegistered.getUsername(), userToBeRegistered.getPassword(), confirmPassword, userToBeRegistered.getMail())) {
            Notification.show("Not all fields have been filled!");
        } else if (!usernameValidator.validateUsername(userToBeRegistered.getUsername())) {

        } else if (!passwordValidator.validatePassword(userToBeRegistered.getPassword(), confirmPassword)) {

        } else if (!mailValidator.validateMail(userToBeRegistered.getMail())) {

        } else if (isUserTooYoung(userToBeRegistered.getDateOfBirth())) {
            Notification.show("The user is too young!");
        } else if (isUserTooOld(userToBeRegistered.getDateOfBirth())) {
            Notification.show("The user is too old!");
        } else if (isUserFromTheFuture(userToBeRegistered.getDateOfBirth())) {
            Notification.show("This is a wrong date!");
        } else {
            Notification.show("The user: " + userToBeRegistered.getUsername() + " has been registered successfully! You can reload this page in order to log in",
                    4000,
                    Notification.Position.BOTTOM_CENTER);
            validationStatus = true;
        }
        return validationStatus;
    }

    public boolean haveFieldsBeenFilled(String username, String password, String confirmPassword, String mail) {
        return !username.equals("") &&
                !password.equals("") &&
                !confirmPassword.equals("") &&
                !mail.equals("");
    }

    public boolean isUsernameAvailable(String searchedUsername) {
        return apiClient.getUsers().stream()
                .noneMatch(userDto -> userDto.getUsername().equals(searchedUsername));
    }

    public boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean isEmailAvailable(String email) {
        return apiClient.getUsers().stream()
                .noneMatch(userDto -> userDto.getMail().equals(email));
    }

    public boolean isUserTooYoung(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears() <= 13 && Period.between(date, LocalDate.now()).getYears() >= 0;
    }

    public boolean isUserTooOld(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears() >= 110;
    }

    public boolean isUserFromTheFuture(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears() < 0;
    }


}
