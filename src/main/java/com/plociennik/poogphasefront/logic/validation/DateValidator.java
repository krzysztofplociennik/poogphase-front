package com.plociennik.poogphasefront.logic.validation;

import com.plociennik.poogphasefront.client.ApiClient;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class DateValidator {
    private ApiClient apiClient;

    public DateValidator(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public boolean validateDate(LocalDate date) {
        return !isUserTooYoung(date) && !isUserTooOld(date) && !isUserFromTheFuture(date);
    }

    public boolean isUserTooYoung(LocalDate date) {
        boolean result = Period.between(date, LocalDate.now()).getYears() <= 13 && Period.between(date, LocalDate.now()).getYears() >= 0;
        if (result) {
            Notification.show("The user is too young!");
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserTooOld(LocalDate date) {
        boolean result = Period.between(date, LocalDate.now()).getYears() >= 150;
        if (result) {
            Notification.show("The user is (very possibly) too old!");
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserFromTheFuture(LocalDate date) {
        boolean result = Period.between(date, LocalDate.now()).getYears() < 0;
        if (result) {
            Notification.show("The user can't be from the future!");
            return true;
        } else {
            return false;
        }
    }
}
