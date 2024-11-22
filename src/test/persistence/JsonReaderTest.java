package persistence;

import model.Activity;
import model.ActivityTracker;
import model.Session;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        assertThrows(IOException.class, reader::read);
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

            // First activity
            Activity activity1 = activities.get(0);
            checkActivity("Running", 3, 5.0, activity1);

            List<Session> sessions1 = activity1.getSessions();
            assertEquals(2, sessions1.size());
            checkSession(2.0, 1, "2024-10-20", sessions1.get(0));
            checkSession(3.0, 2, "2024-10-21", sessions1.get(1));

            // Second activity
            Activity activity2 = activities.get(1);
            checkActivity("Reading", 1, 2.0, activity2);

            List<Session> sessions2 = activity2.getSessions();
            assertEquals(1, sessions2.size());
            checkSession(2.0, 1, "2024-10-22", sessions2.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderActivityWithNoSessions() {
        JsonReader reader = new JsonReader("./data/testReaderActivityWithNoSessions.json");
        try {
            ActivityTracker tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(1, activities.size());
            Activity activity = activities.get(0);
            checkActivity("Meditation", 0, 0.0, activity);
            assertEquals(0, activity.getSessions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMalformedJson() {
        JsonReader reader = new JsonReader("./data/testReaderMalformedJson.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderSessionMissingFields() {
        JsonReader reader = new JsonReader("./data/testReaderSessionMissingFields.json");
        assertThrows(Exception.class, reader::read);
    }

    @Test
    void testReaderSessionWithZeroDuration() {
        JsonReader reader = new JsonReader("./data/testReaderSessionWithZeroDuration.json");
        try {
            ActivityTracker tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(1, activities.size());
            Activity activity = activities.get(0);
            checkActivity("Yoga", 1, 0.0, activity);
            List<Session> sessions = activity.getSessions();
            assertEquals(1, sessions.size());
            checkSession(0.0, 1, "2023-10-25", sessions.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
