package model;

import java.util.List;

import java.time.LocalDate;

public class Activity {
    private String name;             // activity name
    private List<Session> sessions;  // list of sessions of activity
    private int streak = 0;          // the current streak in days of activity
    private int totalTime = 0;       // the total time in hours on activity
    private int nextSessionId = 1;   // the next session id

    /* 
     * REQUIRES: name is not empty 
     * EFFECTS: creates a new activity with the given name;    
     *          initializes with an empty list of sessions.
    */
    public Activity(String name) {
        // !!!
    }

    /* 
     * REQUIRES: name is not empty
     * MODIFIES: this
     * EFFECTS: sets the name of the activity
    */
    public void setName() {
        // !!!
    }

    /* 
     * REQUIRES: new session must have a different date to previous ones
     * MODIFIES: this
     * EFFECTS: adds new session to activity;
     *          updates totalTime and streak;
     *          assigns the nextSessionId;
     *          updates the nextSessionId;
    */
    public void addSession(Session session) {
        // !!!
    }
    
    /* 
     * REQUIRES: new session must have the same date to previous ones
     * MODIFIES: this
     * EFFECTS: updates the session's time by adding a new session
    */
    public void updateSessionTime(Session session) {
        // !!!
    }

    /* 
     * REQUIRES: List<Session> sessions != empty
     * MODIFIES: this
     * EFFECTS: removes session from List<Session> sessions
    */
    public void removeSession(Session session) {
        // !!!
    }

    public String getName() {
        return name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    /*
     * EFFECTS: returns list of sessions from a specific date
     */
    public List<Session> getSessionsOnDate(LocalDate date) {
        // !!!
        return sessions;
    }

    public int getStreak() {
        return streak;
    }

    public int getTotalTime() {
        return totalTime;
    }

    /*
     * EFFECTS: returns the total time of sessions from a sepcific date
     */
    public int getDateTotalTime() {
        // !!!
        return totalTime;
    }

    public int getNextSessionId() {
        return nextSessionId;
    }

    /*
     * EFFECTS: returns a string representation of the activity
     */
    @Override
    public String toString() {
        return "";
        // !!!
    }
}