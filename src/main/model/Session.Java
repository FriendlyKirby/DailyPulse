package model;

import java.time.LocalDate;

public class Session {
    private int durationInHours;  // tracks duration
    private int sessionId = 1;    // tracks the current session id
    private LocalDate date;       // the day the session was created
    
    /* 
     * REQUIRES: durationInHours >= 0
     * EFFECTS: creates a new session with the given durationInHours;    
     *          sessionId is equivalent to the number of days so far;
     *          date is set to the current date.
    */
    public Session(int durationInHours) {
        // !!!
    }

    /* 
     * REQUIRES: id >= 1
     * MODIFIES: this
     * EFFECTS: sets the id for the session
     * 
    */
    public void setId() {
        // !!!
    }

    /* 
     * MODIFIES: this
     * EFFECTS: sets the date for the session
     * 
    */
    public void setDate() {
        // !!!
    }

    public int getDurationInHours() {
        // !!!
    }

    public int getId() {
        // !!!
    }

    public LocalDate getDate() {
        // !!!
    }

}