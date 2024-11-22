package model;

import java.time.LocalDate;

import org.json.JSONObject;

import persistence.Writable;

// Represents a session having a duration (in hours), id, and date of creation
public class Session implements Writable {
    private double durationInHours; // tracks duration
    private int sessionId; // tracks the current session id
    private LocalDate date; // the day the session was created

    /*
     * REQUIRES: durationInHours >= 0.0
     * EFFECTS: creates a new session with the given durationInHours;
     * date is set to the current date.
     */
    public Session(double durationInHours) {
        this.durationInHours = durationInHours;
        this.date = LocalDate.now();
    }

    /*
     * REQUIRES: durationInHours >= 0.0
     * MODIFIES: this
     * EFFECTS: adds time to durationInHours for the session
     */
    public void addDuration(double durationInHours) {
        this.durationInHours += durationInHours;
    }

    /*
     * REQUIRES: durationInHours >= 0.0
     * MODIFIES: this
     * EFFECTS: sets the durationInHours for the session
     */
    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    /*
     * REQUIRES: sessionId >= 1
     * MODIFIES: this
     * EFFECTS: sets the id for the session
     */
    public void setId(int sessionId) {
        this.sessionId = sessionId;
    }

    /*
     * REQUIRES: date is not null
     * MODIFIES: this
     * EFFECTS: sets the date for the session
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDurationInHours() {
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
                + ", Duration: " + String.format("%.2f", durationInHours) + " hours"
                + ", Date: " + date;
    }

    // Credit to JsonSerializationDemo
    // Converts the session to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("durationInHours", durationInHours);
        json.put("sessionId", sessionId);
        json.put("date", date.toString());
        return json;
    }
}
