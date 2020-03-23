package com.example.producktivity.dbs;

import androidx.room.TypeConverter;

public class CategoryConverter {

    @TypeConverter
    public static Category fromCategory(String value) {
        if(value != null) {
            for(Category c : Category.values()) {
                if(value.compareTo(c.name()) == 0) {
                    return c;
                }
            }
        }
        return null;
    }

    @TypeConverter
    public static String categoryToString(Category c) {
        return c.name();
    }
}
