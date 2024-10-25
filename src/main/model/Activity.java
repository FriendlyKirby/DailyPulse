package model;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

// Represents an activity having a name, list of sessions, streaks (in days), and total time
public class Activity {
    private String name; // activity name
    private List<Session> sessions; // list of sessions of activity
    private int streak = 0; // the current streak in days of activity
    private int totalTime = 0; // the total time in hours on activity
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
     * EFFECTS: sets the name of the activity
     */
    public void setStreak(int streak) {
        this.streak = streak;
    }

    /*
     * REQUIRES: totalTime >= 0
     * MODIFIES: this
     * EFFECTS: sets the totalTime of the activity
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /*
     * REQUIRES: new session must have a different date to previous ones
     * MODIFIES: this
     * EFFECTS: adds new session to activity if date has changed;
     * updates totalTime;
     * adds 1 to streak if day is +1;
     * assigns the nextSessionId as the session id;
     * updates the nextSessionId;
     */
    public void addSession(Session session) {
        if (!this.sessions.isEmpty()) {
            Session latestSession = this.sessions.get(sessions.size() - 1);

            if (session.getDate().equals(latestSession.getDate())) {
                latestSession.addDuration(session.getDurationInHours());
                totalTime += session.getDurationInHours();

            } else if (session.getDate().equals(latestSession.getDate().plusDays(1))) {
                session.setId(nextSessionId);
                nextSessionId++;
                this.sessions.add(session);
                totalTime += session.getDurationInHours();
                streak++;

            } else {
                session.setId(nextSessionId);
                nextSessionId++;
                this.sessions.add(session);
                totalTime += session.getDurationInHours();
            }
        } else {
            addSessionElseCase(session);
        }
    }

    /*
     * REQUIRES: List<Session> sessions != empty
     * MODIFIES: this
     * EFFECTS: sets session id as nextSessionId;
     * increases nextSessionId by 1;
     * adds this session to sessions;
     * increases total time by sesion time;
     * increases streak by 1
     */
    private void addSessionElseCase(Session session) {
        session.setId(nextSessionId);
        nextSessionId++;
        this.sessions.add(session);
        totalTime += session.getDurationInHours();
        streak++;
    }

    /*
     * REQUIRES: List<Session> sessions != empty
     * MODIFIES: this
     * EFFECTS: removes session from List<Session> sessions;
     * updates total time;
     * calls updateStreak();
     */
    public void removeSession(Session session) {
        if (this.sessions.contains(session)) {
            this.sessions.remove(session);
            totalTime -= session.getDurationInHours();
            updateStreak();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: if previous days were consequtive, streak - 1;
     * else no change.
     */
    private void updateStreak() {
        if (sessions.size() > 1) {
            LocalDate latestSessionDay = this.sessions.get(sessions.size() - 1).getDate();
            LocalDate secondLatestSessionDay = this.sessions.get(sessions.size() - 2).getDate();

            if (latestSessionDay.equals(secondLatestSessionDay.plusDays(1))) {
                streak -= 1;
            }
        } else if (sessions.size() == 1) {
            streak = 1;
        } else {
            streak = 0;
        }
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

    public int getTotalTime() {
        return totalTime;
    }

    /*
     * EFFECTS: returns the total time of sessions from a sepcific date
     */
    public int getDateTotalTime(LocalDate date) {
        int dateTotalTime = 0;

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
                + "Total Time: " + totalTime + " hours";
    }
}