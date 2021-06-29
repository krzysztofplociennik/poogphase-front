package com.plociennik.poogphasefront.gui.forms;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.RegistrationValidator;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

public class RegistrationForm extends VerticalLayout {
    private ApiClient apiClient;
    private TextField usernameTextField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private EmailField emailField;
    private DatePicker datePicker;
    private RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationForm(ApiClient apiClient, RegistrationValidator registrationValidator) {
        this.apiClient = apiClient;
        this.registrationValidator = registrationValidator;

        usernameTextField = new TextField("Username");
        passwordField = new PasswordField("Password");
        confirmPasswordField = new PasswordField("Confirm password");
        emailField = new EmailField("Email");
        datePicker = new DatePicker("Date of birth");

        Button registerButton = new Button("register", buttonClickEvent -> {
            UserDto unregisteredUser = new UserDto();
            unregisteredUser.setUsername(usernameTextField.getValue());
            unregisteredUser.setPassword(passwordField.getValue());
            unregisteredUser.setMail(emailField.getValue());
            try {
                unregisteredUser.setDateOfBirth(LocalDate.of(
                        datePicker.getValue().getYear(),
                        datePicker.getValue().getMonthValue(),
                        datePicker.getValue().getDayOfMonth()));
            } catch (NullPointerException e) {
                Notification.show("There's no date!");
            }
            try {
                registerUser(unregisteredUser);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Button cancelButton = new Button("go back", buttonClickEvent -> {
            UI.getCurrent().navigate("login");
            getUI().get().getPage().reload();
        });
        HorizontalLayout buttonsLayout = new HorizontalLayout(registerButton, cancelButton);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        add(usernameTextField, passwordField, confirmPasswordField, emailField, datePicker, buttonsLayout);
    }

//    public boolean checkIfUsernameIsPresent(String searchedUsername) {
//        return apiClient.getUsers().stream()
//                .anyMatch(userDto -> userDto.getUsername().equals(searchedUsername));
//    }
//
//    public boolean checkIfEmailIsPresent(String email) {
//        return apiClient.getUsers().stream()
//                .anyMatch(userDto -> userDto.getMail().equals(email));
//    }
//
//    public boolean checkIfFieldsHaveBeenFilled() {
//        return  !usernameTextField.getValue().equals("") &&
//                !passwordField.getValue().equals("") &&
//                !confirmPasswordField.getValue().equals("") &&
//                !emailField.getValue().equals("");
//    }

    public void registerUser(UserDto userToBeRegistered) throws InterruptedException {
//        if (!checkIfFieldsHaveBeenFilled()) {
//            Notification.show("Not all fields have been filled!");
//        } else if (checkIfEmailIsPresent(userToBeRegistered.getMail())) {
//            Notification.show("The mail has already been registered!");
//        } else if (checkIfUsernameIsPresent(userToBeRegistered.getUsername())) {
//            Notification.show("The username has already been registered!");
//        } else if (!passwordField.getValue().equals(confirmPasswordField.getValue())) {
//            Notification.show("The passwords don't match!");
//        } else {
//            Notification.show("The user: " + userToBeRegistered.getUsername() + " has been registered successfully! You can reload this page in order to log in",
//                    4000,
//                    Notification.Position.BOTTOM_CENTER);
//        }
        registrationValidator.validateUser(userToBeRegistered, confirmPasswordField.getValue());
        apiClient.createUser(userToBeRegistered);
    }
}
