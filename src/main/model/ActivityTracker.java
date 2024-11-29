package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents the tracker which holds a list of activities
public class ActivityTracker implements Writable {
    private List<Activity> activities; // list of activities

    /*
     * EFFECTS: creates a new ActivityTracker with an empty list of activities
     */
    public ActivityTracker() {
        this.activities = new ArrayList<>();
    }

    /*
     * REQUIRES: activity is not null
     * MODIFIES: this
     * EFFECTS: adds activity to activities list if it's not already present
     */
    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);

            // Log the event
            EventLog.getInstance().logEvent(new Event("Activity added: " + activity.getName()));
        }
    }

    /*
     * REQUIRES: activity is not null and exists in the list
     * MODIFIES: this
     * EFFECTS: removes activity from activities list
     */
    public void removeActivity(Activity activity) {
        if (!activities.contains(activity)) {
            throw new IllegalArgumentException("Activity not found");
        }
        activities.remove(activity);

        // Log the event
        EventLog.getInstance().logEvent(new Event("Activity removed: " + activity.getName()));
    }

    /*
     * REQUIRES: name is not null or empty
     * EFFECTS: gets activity from activities list by name;
     * returns null if not found
     */
    public Activity getActivityByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Activity name cannot be null.");
        }
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    /*
     * EFFECTS: returns list of activity names
     */
    public List<String> getActivityNames() {
        List<String> activityNames = new ArrayList<>();
        for (Activity activity : activities) {
            activityNames.add(activity.getName());
        }
        return activityNames;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts activities list alphabetically;
     * if same first character, then sorts by next and so on
     */
    public void sortByName() {
        activities.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts activities list in decreasing order of streak;
     * if same, positions don't swap
     */
    public void sortByStreak() {
        activities.sort((a1, a2) -> Integer.compare(a2.getStreak(), a1.getStreak()));
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts activities list in decreasing order of total time;
     * if same, positions don't swap
     */
    public void sortByTotalTime() {
        activities.sort((a1, a2) -> Double.compare(a2.getTotalTime(), a1.getTotalTime()));
    }

    // Credit to JsonSerializationDemo
    // Converts the activity tracker to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: returns activities in this tracker as a JSON array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity activity : activities) {
            jsonArray.put(activity.toJson());
        }

        return jsonArray;
    }
}
