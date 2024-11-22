package persistence;

import model.ActivityTracker;
import org.json.JSONObject;

import java.io.*;

// Credit to JsonSerializationDemo
// Represents a writer that writes JSON representation of activity tracker to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /*
     * REQUIRES: destination is not null
     * EFFECTS: constructs writer to write to destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens writer; throws FileNotFoundException if destination file
     * cannot be opened for writing
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes JSON representation of activity tracker to file
     */
    public void write(ActivityTracker at) {
        JSONObject json = at.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
     * MODIFIES: this
     * EFFECTS: closes writer
     */
    public void close() {
        writer.close();
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
