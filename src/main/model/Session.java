package model;

import java.time.LocalDate;

// Represents a session having a duration (in hours), id, and date of creation
public class Session {
    private int durationInHours; // tracks duration
    private int sessionId = 1; // tracks the current session id
    private LocalDate date; // the day the session was created

    /*
     * REQUIRES: durationInHours >= 0
     * EFFECTS: creates a new session with the given durationInHours;
     * sessionId is equivalent to the number of days so far;
     * date is set to the current date.
     */
    public Session(int durationInHours) {
        this.durationInHours = durationInHours;
        this.date = LocalDate.now();
    }

    /*
     * REQUIRES: duration >= 0
     * MODIFIES: this
     * EFFECTS: adds time to durationInHours for the session
     */
    public void addDuration(int durationInHours) {
        this.durationInHours += durationInHours;
    }

    /*
     * REQUIRES: id >= 1
     * MODIFIES: this
     * EFFECTS: sets the id for the session
     */
    public void setId(int sessionId) {
        this.sessionId = sessionId;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the date for the session
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public int getId() {
        return sessionId;
    }

    public LocalDate getDate() {
        return date;
    }

    /*
     * EFFECTS: returns a string representation of session
     */
    @Override
    public String toString() {
        return "Session ID: " + sessionId
                + ", Duration: " + durationInHours + " hours"
                + ", Date: " + date;
    }
}
