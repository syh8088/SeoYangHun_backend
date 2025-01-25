package com.wirebarley.global.util;

import java.time.*;
import java.util.Date;

public class TimeConverter {

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Long toEpochMilli (LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime nowStartDateTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
    }

    public static LocalDateTime nowEndDateTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
    }
}