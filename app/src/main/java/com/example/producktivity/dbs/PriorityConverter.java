package com.example.producktivity.dbs;

import androidx.room.TypeConverter;

public class PriorityConverter {

    @TypeConverter
    public static Priority fromPriority(String value) {
        if (value != null) {
            for (Priority p : Priority.values()) {
                if (value.compareTo(p.name()) == 0)
                    return p;
            }
        }
        return null;
    }

    @TypeConverter
    public static String priorityToString(Priority p) {
        return p.name();
    }
}

