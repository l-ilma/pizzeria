package com.example.pizzeria.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromStatus(Status status) {
        return status.name();
    }

    @TypeConverter
    public static Status toStatus(String status) {
        return Status.valueOf(status);
    }
}
