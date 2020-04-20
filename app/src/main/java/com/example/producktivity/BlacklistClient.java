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

    public List<BlacklistEntry> getListOfApps() {
        return listOfApps;
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
    //can't set it based on inferred productive, becase this IS how you set inferred productive
    public static Boolean categoryVote(BlacklistEntry app, List<BlacklistEntry> listOfApps){
        int countProductive = 0;
        if (app.isUnrestricted())
            return true;
        if (app.getDayLimit() != BlacklistEntry.NO_LIMIT || app.getWeekLimit() != BlacklistEntry.NO_LIMIT)
            return false;
        for(BlacklistEntry iter: listOfApps){
            if(iter.getCategory().equals(app.getCategory())){
                if (iter.isInferredProductive() || iter.isUnrestricted())
                    countProductive++;
                else if(iter.getDayLimit() != BlacklistEntry.NO_LIMIT || iter.getWeekLimit() != BlacklistEntry.NO_LIMIT)
                    countProductive--;
            }
        }
        return countProductive >= 0;
    }

    //returns false if unproductive, true if productive
    public Boolean classifyApp(String appId) {
        BlacklistEntry app = getEntryFromList(appId);
        //if user marks as productive it's productive
        return categoryVote(app, listOfApps);
    }

}
