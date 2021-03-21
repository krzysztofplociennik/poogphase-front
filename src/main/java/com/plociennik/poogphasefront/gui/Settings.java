package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("settings")
@PageTitle("Settings")
public class Settings extends HorizontalLayout {
    private ApiClient apiClient;
    private GeneralView generalView;
    private SessionManager sessionManager;

    @Autowired
    public Settings(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.generalView = new GeneralView();
        this.sessionManager = new SessionManager(this.apiClient);
        setSizeFull();

        setupContentView();
    }

    public void setupContentView() {
        VerticalLayout credentialsInfoLayout = new VerticalLayout();
        TextField usernameTextField = new TextField("Username");
        usernameTextField.setValue(sessionManager.getLoggedUser().getUsername());
        usernameTextField.setReadOnly(true);
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setValue(sessionManager.getLoggedUser().getPassword());
        passwordField.setReadOnly(true);
        EmailField mailField = new EmailField("Mail");
        mailField.setValue(sessionManager.getLoggedUser().getMail());
        mailField.setReadOnly(true);
        TextField firstNameTextField = new TextField("Firstname");
        firstNameTextField.setValue(sessionManager.getLoggedUser().getFirstName());
        firstNameTextField.setReadOnly(true);
        TextField lastNameTextField = new TextField("Lastname");
        lastNameTextField.setValue(sessionManager.getLoggedUser().getLastName());
        lastNameTextField.setReadOnly(true);
        TextField dateOfBirthTextField = new TextField("Date of birth");
        dateOfBirthTextField.setValue(sessionManager.getLoggedUser().getDateOfBirth().toString());
        dateOfBirthTextField.setReadOnly(true);

        credentialsInfoLayout.add(new H5("Here is your account info"), usernameTextField, passwordField,
                mailField, firstNameTextField, lastNameTextField, dateOfBirthTextField);
        generalView.addToSecondary(credentialsInfoLayout);
        add(generalView);
    }
}
