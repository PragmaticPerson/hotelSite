package com.keydoorhotel.service.formatter;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;

public class DateFormatter {

    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public static LocalDate getDate(String str) {
        try {
            return LocalDate.parse(str, ofPattern(DATE_FORMAT));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Wrong date format");
        }
    }
}
