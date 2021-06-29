package com.plociennik.poogphasefront.gui.forms;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.logic.RegistrationValidator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Login")
@UIScope
public class Login extends HorizontalLayout implements BeforeEnterObserver {
    private LoginForm loginForm;
    private ApiClient apiClient;
    private RegistrationValidator registrationValidator;

    @Autowired
    public Login(ApiClient apiClient, RegistrationValidator registrationValidator) {
        this.loginForm = new LoginForm();
        this.apiClient = apiClient;
        this.registrationValidator = registrationValidator;

        VerticalLayout loginLayout = new VerticalLayout();
        addClassName("login-view");
        loginForm.setAction("login");
        setSizeFull();

        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        loginLayout.setAlignItems(Alignment.CENTER);

        Button registerButton = new Button("register new account", buttonClickEvent -> showRegistrationForm());
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        loginLayout.add(new H1("Poogphase"), loginForm, registerButton);
        add(loginLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

    public void showRegistrationForm() {
        add(new RegistrationForm(this.apiClient, this.registrationValidator));
    }
}
