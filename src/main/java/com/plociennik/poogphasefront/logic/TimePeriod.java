package com.plociennik.poogphasefront.logic;

import java.time.LocalDateTime;
import java.time.Period;

public class TimePeriod {
    public static String howLongAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Period timeBetween = Period.between(dateTime.toLocalDate(), now.toLocalDate());

        if (timeBetween.getYears() >= 1) {
            return "in " + dateTime.getYear();
        } else if(timeBetween.getMonths() > 1) {
            return "in " + dateTime.getMonth();
        } else if(timeBetween.getMonths() == 1) {
            return "last month";
        } else if (timeBetween.getDays() > 1) {
            return timeBetween.getDays() + " days ago";
        } else if (timeBetween.getDays() == 1) {
            return "yesterday";
        } else if (now.getHour() - dateTime.getHour() > 1) {
            return now.getHour() - dateTime.getHour() + " hours ago";
        } else if (now.getHour() - dateTime.getHour() == 1) {
            return "an hour ago";
        } else if (now.getMinute() - dateTime.getMinute() > 1) {
            return now.getMinute() - dateTime.getMinute() + " minutes ago";
        } else if (now.getMinute() - dateTime.getMinute() == 1) {
            return "a minute ago";
        } else {
            return "just now";
        }
    }
}
