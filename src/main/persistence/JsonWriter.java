package persistence;

import model.ActivityTracker;
import org.json.JSONObject;

import java.io.*;

// credit to JsonSerializationDemo
// Represents a writer that writes JSON representation of activity tracker to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Methods will be implemented here...
}
