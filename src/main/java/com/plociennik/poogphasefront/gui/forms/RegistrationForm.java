package com.plociennik.poogphasefront.gui.forms;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.validation.RegistrationValidator;
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
import java.io.IOException;
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
            } catch (InterruptedException | IOException e) {
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

    public void registerUser(UserDto userToBeRegistered) throws InterruptedException, IOException {
        if (registrationValidator.validateUser(userToBeRegistered, confirmPasswordField.getValue())) {
//            apiClient.createUser(userToBeRegistered);
        }
    }
}
