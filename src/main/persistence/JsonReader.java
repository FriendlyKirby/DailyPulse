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

// credit to JsonSerializationDemo
// Represents a reader that reads activity tracker from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads activity tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ActivityTracker read() throws IOException {
        // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        // stub
    }

    // EFFECTS: parses activity tracker from JSON object and returns it
    private ActivityTracker parseActivityTracker(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: tracker
    // EFFECTS: parses activities from JSON object and adds them to activity tracker
    private void addActivities(ActivityTracker tracker, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: tracker
    // EFFECTS: parses activity from JSON object and adds it to activity tracker
    private void addActivity(ActivityTracker tracker, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: activity
    // EFFECTS: parses sessions from JSON object and adds them to activity
    private void addSessions(Activity activity, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: activity
    // EFFECTS: parses session from JSON object and adds it to activity
    private void addSession(Activity activity, JSONObject jsonObject) {
        // stub
    }

}
