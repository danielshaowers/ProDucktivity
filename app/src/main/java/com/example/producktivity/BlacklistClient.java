package com.example.producktivity;


import android.content.Context;

import com.example.producktivity.ClassificationClient;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BlacklistClient {
    private ClassificationClient categorizer;
    private List<BlacklistEntry> listOfApps;
    public BlacklistClient(List<BlacklistEntry> entries, Context t){
        this.categorizer = new ClassificationClient(t);
        this.listOfApps = entries;

    }

    //called when an app is opened to determine whether or not to block it

    /*public Boolean shouldAppBeBlocked(String appName) {
        String category;
        if(this.isInDatabase(appName)){
            //get category from database
            //TODO: check if category exists in DB
            category = getCategoryFromDataList(appName);
        }
        else {
            category = this.getAppCategory(appName);
        }
        //Make a determination about whether the app should be blocked
        return this.classifyApp(category);

    } */

    public void setListOfApps(List<BlacklistEntry> apps) {
        this.listOfApps = apps;
    }

    private Boolean isInDatabase(String appId) {
        for(BlacklistEntry element : this.listOfApps) {
            if(element.getAppName().equalsIgnoreCase(appId)) {
                return true;
            }
        }
        return false;
    }

    //get the category of the app
    private String getAppCategory(String appId) {
        return categorizer.requestAppCategory(appId);
    }

    private String getCategoryFromDataList(String appId) {
        for(BlacklistEntry element : this.listOfApps) {
            if(element.getPackageName().equalsIgnoreCase(appId)) {
                if(element.getCategory() != null)
                    return element.getCategory().toString();
            }
        }
        return "NO CATEGORY IN DATABASE";
    }

    private BlacklistEntry getEntryFromList(String appId) {
        for(BlacklistEntry element : this.listOfApps) {
            if(element.getPackageName().equalsIgnoreCase(appId)) {
                return element;
            }
        }
        return null;
    }

    private Boolean categoryVote(BlacklistEntry app){
        int countProductive = 0;
        for(BlacklistEntry iter: this.listOfApps){
            if(iter.getCategory().equals(app.getCategory())){
                if (iter.isInferredProductive() || iter.isUnrestricted())
                    countProductive++;
                else
                    countProductive--;
            }
        }
        return countProductive >= 0;
    }

    //returns false if unproductive, true if productive
    public Boolean classifyApp(String appId) {
        BlacklistEntry app = getEntryFromList(appId);
        //determine if the app is productive or unproductive based on category and database information
        //if(appCategory.equalsIgnoreCase("PRODUCTIVITY") || appCategory.equalsIgnoreCase("BUSINESS") || appCategory.equalsIgnoreCase("EDUCATION") || appCategory.equalsIgnoreCase("BOOKS_AND_REFERENCE"))
          //  return true;
        //else
          //  return false;

        //if user marks as productive it's productive
        if(app.isUnrestricted()) {
            return true;
        }
        //if user sets time limit it's unproductive
        else if(app.getDayLimit() != BlacklistEntry.NO_LIMIT || app.getWeekLimit() != BlacklistEntry.NO_LIMIT){
            return false;
        }

        //if neither, majority vote of category
        else {
            return categoryVote(app);
        }
    }

}
