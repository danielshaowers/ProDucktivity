package com.example.producktivity.dbs.blacklist;

public enum Category {
    ALL, ART_AND_DESIGN, AUGMENTED_REALITY, AUTO_AND_VEHICLES, BEAUTY, BOOKS_AND_REFERENCE, BUSINESS,
    COMICS, COMMUNICATION, DATING, EDUCATION, ENTERTAINMENT, EVENTS, FAMILY, FINANCE, FOOD_AND_DRINK,
    GAME, GOOGLE_CAST, HEALTH_AND_FITNESS, HOUSE_AND_HOME, LIBRARIES_AND_DEMO, LIFESTYLE, PRODUCKTIVITY,
    MAPS_AND_NAVIGATION, MEDICAL, SYSTEM, TRAVEL_AND_LOCAL, VIDEO_PLAYERS;

    @Override
    public String toString(){
        String name = name();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++){
            if (name.charAt(i) == '_')
                sb.append(' ');
            else
                sb.append(name.charAt(i));
        }
        return sb.toString();
    }
}