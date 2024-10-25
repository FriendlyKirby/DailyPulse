package persistence;

import model.ActivityTracker;
import model.Activity;
import model.Session;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// credit to JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ActivityTracker tracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Expected exception caught; test passes
        }
    }

    @Test
    void testReaderEmptyActivityTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyActivityTracker.json");
        try {
            ActivityTracker tracker = reader.read();
            assertEquals(0, tracker.getActivities().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralActivityTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralActivityTracker.json");
        try {
            ActivityTracker tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(2, activities.size());

            Activity activity1 = activities.get(0);
            checkActivity("Running", 3, 5, activity1);

            List<Session> sessions1 = activity1.getSessions();
            assertEquals(2, sessions1.size());
            checkSession(2, 1, "2024-10-20", sessions1.get(0));
            checkSession(3, 2, "2024-10-21", sessions1.get(1));

            Activity activity2 = activities.get(1);
            checkActivity("Reading", 1, 2, activity2);

            List<Session> sessions2 = activity2.getSessions();
            assertEquals(1, sessions2.size());
            checkSession(2, 1, "2024-10-22", sessions2.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
