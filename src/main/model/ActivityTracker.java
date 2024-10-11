package model;

import java.util.ArrayList;
import java.util.List;

public class ActivityTracker {
    private List<Activity> activities; // list of activities
    
    /* 
     * EFFECTS: creates a new ActivityTracker with an empty list of activities
    */
    public ActivityTracker() {
        // !!!
    }

    /* 
     * MODIFIES: this
     * EFFECTS: adds activity to activities list
    */
    public void addActivity(Activity activity) {
        // !!!
    }
    
    /* 
     * MODIFIES: this
     * EFFECTS: removes activity from activities list
    */
    public void removeActivity(Activity activity) {
        // !!!
    }

    /* 
     * EFFECTS: get activity from activities list by name
    */
    public Activity getActivity(String name) {
        // !!!
        return activities.get(0);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    /*  
     * EFFECTS: returns list of activity names
    */
    public List<String> getActivitieNames() {
        // !!!
        return new ArrayList<>();
    }

    /* 
     * MODIFIES: this
     * EFFECTS: sorts activities list alphabetically
    */
    public void sortByName() {
        // !!!
    }

    /* 
     * MODIFIES: this
     * EFFECTS: sorts actvities list in decreasing order of streak
    */
    public void sortByStreak() {
        // !!!
    }

    /* 
     * MODIFIES: this
     * EFFECTS: sorts activities list in decreasing order of total time
    */
    public void sortByTotalTime() {
        // !!!
    }
}