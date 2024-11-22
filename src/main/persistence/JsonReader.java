package persistence;

import model.Session;
import model.Activity;
import model.ActivityTracker;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

// Credit to JsonSerializationDemo
// Represents a reader that reads activity tracker from JSON data stored in file
public class JsonReader {
    private String source;

    /*
     * REQUIRES: source is not null
     * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * EFFECTS: reads activity tracker from file and returns it;
     * throws IOException if an error occurs reading data from file
     */
    public ActivityTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseActivityTracker(jsonObject);
    }

    /*
     * EFFECTS: reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /*
     * EFFECTS: parses activity tracker from JSON object and returns it
     */
    private ActivityTracker parseActivityTracker(JSONObject jsonObject) {
        ActivityTracker tracker = new ActivityTracker();
        addActivities(tracker, jsonObject);
        return tracker;
    }

    /*
     * MODIFIES: tracker
     * EFFECTS: parses activities from JSON object and adds them to activity tracker
     */
    private void addActivities(ActivityTracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(tracker, nextActivity);
        }
    }

    /*
     * MODIFIES: tracker
     * EFFECTS: parses activity from JSON object and adds it to activity tracker
     */
    private void addActivity(ActivityTracker tracker, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int streak = jsonObject.getInt("streak");
        double totalTime = jsonObject.getDouble("totalTime"); // Use getDouble()
        int nextSessionId = jsonObject.getInt("nextSessionId");

        Activity activity = new Activity(name);
        activity.setStreak(streak);
        activity.setTotalTime(totalTime);
        activity.setNextSessionId(nextSessionId);

        addSessions(activity, jsonObject);
        tracker.addActivity(activity);
    }

    /*
     * MODIFIES: activity
     * EFFECTS: parses sessions from JSON object and adds them to activity
     */
    private void addSessions(Activity activity, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessions");
        for (Object json : jsonArray) {
            JSONObject nextSession = (JSONObject) json;
            addSession(activity, nextSession);
        }
    }

    /*
     * MODIFIES: activity
     * EFFECTS: parses session from JSON object and adds it to activity
     */
    private void addSession(Activity activity, JSONObject jsonObject) {
        double durationInHours = jsonObject.getDouble("durationInHours"); // Use getDouble()
        int sessionId = jsonObject.getInt("sessionId");
        String dateString = jsonObject.getString("date");
        LocalDate date = LocalDate.parse(dateString);

        Session session = new Session(durationInHours);
        session.setId(sessionId);
        session.setDate(date);

        activity.getSessions().add(session);
    }
}
