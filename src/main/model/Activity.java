package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.time.LocalDate;

// Represents an activity having a name, list of sessions, streaks (in days), and total time
public class Activity implements Writable {
    private String name; // activity name
    private List<Session> sessions; // list of sessions of activity
    private int streak = 0; // the current streak in days of activity
    private double totalTime = 0.0; // the total time in hours on activity
    private int nextSessionId = 1; // the next session id

    /*
     * REQUIRES: name is not empty
     * EFFECTS: creates a new activity with the given name;
     * initializes with an empty list of sessions.
     */
    public Activity(String name) {
        this.name = name;
        this.sessions = new ArrayList<>();
    }

    /*
     * REQUIRES: name is not empty
     * MODIFIES: this
     * EFFECTS: sets the name of the activity
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * REQUIRES: streak >= 0
     * MODIFIES: this
     * EFFECTS: sets the streak of the activity
     */
    public void setStreak(int streak) {
        this.streak = streak;
    }

    /*
     * REQUIRES: totalTime >= 0.0
     * MODIFIES: this
     * EFFECTS: sets the totalTime of the activity
     */
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    /*
     * REQUIRES: nextSessionId >= 1
     * MODIFIES: this
     * EFFECTS: sets the nextSessionId of the activity
     */
    public void setNextSessionId(int nextSessionId) {
        this.nextSessionId = nextSessionId;
    }

    /*
     * REQUIRES: session is not null
     * MODIFIES: this
     * EFFECTS: adds new session to activity;
     * updates totalTime;
     * updates streak based on session dates;
     * assigns the nextSessionId as the session id;
     * increments nextSessionId.
     */
    public void addSession(Session session) {
        // Assign session ID using nextSessionId
        session.setId(nextSessionId);
        nextSessionId++;

        // Add session to the list
        sessions.add(session);

        // Update total time
        totalTime += session.getDurationInHours();

        // Update streak
        updateStreak(session);

        // Log the event
        EventLog.getInstance().logEvent(new Event("Session added to activity: " + name));
    }

    /*
     * REQUIRES: session is not null
     * MODIFIES: this
     * EFFECTS: updates the streak based on the dates of the sessions
     */
    private void updateStreak(Session session) {
        if (!sessions.isEmpty()) {
            if (sessions.size() == 1) {
                streak = 1;
            } else {
                Session previousSession = sessions.get(sessions.size() - 2);
                LocalDate previousDate = previousSession.getDate();
                LocalDate currentDate = session.getDate();

                if (currentDate.equals(previousDate.plusDays(1))) {
                    streak++;
                } else if (!currentDate.equals(previousDate)) {
                    streak = 1;
                }
            }
        }
    }

    /*
     * REQUIRES: session is not null
     * MODIFIES: this
     * EFFECTS: removes session from sessions list;
     * updates totalTime;
     * recalculates the streak.
     */
    public void removeSession(Session session) {
        if (this.sessions.contains(session)) {
            this.sessions.remove(session);
            totalTime -= session.getDurationInHours();
            recalculateStreak();

            // Log the event
            EventLog.getInstance().logEvent(new Event("Session removed from activity: " + name));
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: recalculates the streak based on the current sessions list
     */
    private void recalculateStreak() {
        streak = 0;
        if (!sessions.isEmpty()) {
            // Sort sessions by date
            sessions.sort((s1, s2) -> s1.getDate().compareTo(s2.getDate()));
            streak = 1;
            for (int i = 1; i < sessions.size(); i++) {
                LocalDate prevDate = sessions.get(i - 1).getDate();
                LocalDate currDate = sessions.get(i).getDate();
                if (currDate.equals(prevDate.plusDays(1))) {
                    streak++;
                } else {
                    streak = 1;
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    /*
     * REQUIRES: date is not null
     * EFFECTS: returns list of sessions from a specific date
     */
    public List<Session> getSessionsOnDate(LocalDate date) {
        List<Session> sessionOnDate = new ArrayList<>();

        for (Session session : this.sessions) {
            if (session.getDate().equals(date)) {
                sessionOnDate.add(session);
            }
        }

        return sessionOnDate;
    }

    public int getStreak() {
        return streak;
    }

    public double getTotalTime() {
        return totalTime;
    }

    /*
     * REQUIRES: date is not null
     * EFFECTS: returns the total time of sessions from a specific date
     */
    public double getDateTotalTime(LocalDate date) {
        double dateTotalTime = 0.0;

        for (Session session : this.sessions) {
            if (session.getDate().equals(date)) {
                dateTotalTime += session.getDurationInHours();
            }
        }

        return dateTotalTime;
    }

    public int getNextSessionId() {
        return nextSessionId;
    }

    /*
     * EFFECTS: returns a string representation of the activity
     */
    @Override
    public String toString() {
        return "Activity Name: " + name + ", Sessions: " + sessions.size() + ", "
                + "Streak: " + streak + " days, "
                + "Total Time: " + String.format("%.2f", totalTime) + " hours";
    }

    // Credit to JsonSerializationDemo
    // Converts the activity to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("streak", streak);
        json.put("totalTime", totalTime);
        json.put("nextSessionId", nextSessionId);
        json.put("sessions", sessionsToJson());
        return json;
    }

    // EFFECTS: returns sessions in this activity as a JSON array
    private JSONArray sessionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Session session : sessions) {
            jsonArray.put(session.toJson());
        }

        return jsonArray;
    }
}
