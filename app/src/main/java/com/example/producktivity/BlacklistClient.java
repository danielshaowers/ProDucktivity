package com.example.producktivity;

public class BlacklistClient {
    private ClassificationClient categorizer;
    public BlacklistClient(){
        this.categorizer = new ClassificationClient();
    }

    //called when an app is opened to determine whether or not to block it
    public Boolean shouldAppBeBlocked(String AppName) {
        //Get app ID from app name

        //check if app is in database, if yes use the recorded classification.  if no call getAppCategory and classifyApp

        //Make a determination about whether the app should be blocked
        return Boolean.TRUE;
    }

    //get the category of the app
    public String getAppCategory(String appId) {
        return categorizer.requestAppCategory(appId);
    }

    //
    public String classifyApp(String appCategory) {
        //determine if the app is productive or unproductive based on category and database information
        if(appCategory.equalsIgnoreCase("GAME"))
            return"UNPRODUCTIVE";

        return "";
    }





}
