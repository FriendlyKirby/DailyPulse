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
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of activity tracker to file
    public void write(ActivityTracker at) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        // stub
    }
}
