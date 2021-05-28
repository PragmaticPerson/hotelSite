package com.keydoorhotel.service.formatter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class DateFormatterTest {

    @Test
    void formatDateWithRightPatternTest() {
        String date = "05.08.2020";
        LocalDate expected = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals(expected, DateFormatter.getDate(date));
    }

    @Test
    void expectedIllegalArgumentExceptionWhenPassedStringWrongFormat() {
        String date = "2020-08-05";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DateFormatter.getDate(date);
        });
        assertEquals("Wrong date format", exception.getMessage());
    }
}
