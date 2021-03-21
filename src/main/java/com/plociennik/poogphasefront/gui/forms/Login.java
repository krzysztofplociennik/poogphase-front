package com.plociennik.poogphasefront.gui.forms;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("login")
@PageTitle("Login")
@UIScope
public class Login extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm loginForm;

    public Login() {
        this.loginForm = new LoginForm();
        addClassName("login-view");
        loginForm.setAction("login");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

//        loginForm.setAction("login");

        add(new H1("Poogphase"), loginForm);
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
}
