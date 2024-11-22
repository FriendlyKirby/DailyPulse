package persistence;

import model.Activity;
import model.ActivityTracker;
import model.Session;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ActivityTracker tracker = new ActivityTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            assertThrows(FileNotFoundException.class, writer::open);
        } catch (Exception e) {
            fail("An unexpected exception was thrown");
        }
    }

    @Test
    void testWriterEmptyActivityTracker() {
        try {
            ActivityTracker tracker = new ActivityTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyActivityTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyActivityTracker.json");
            tracker = reader.read();
            assertEquals(0, tracker.getActivities().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralActivityTracker() {
        try {
            ActivityTracker tracker = new ActivityTracker();

            Activity activity = new Activity("Running");
            tracker.addActivity(activity);

            // Use addSession() to ensure proper handling
            Session session1 = new Session(2.0);
            session1.setDate(LocalDate.parse("2023-10-20"));
            activity.addSession(session1);

            Session session2 = new Session(3.0);
            session2.setDate(LocalDate.parse("2023-10-21"));
            activity.addSession(session2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralActivityTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            // Read back the data and verify
            JsonReader reader = new JsonReader("./data/testWriterGeneralActivityTracker.json");
            tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(1, activities.size());
            Activity readActivity = activities.get(0);
            checkActivity("Running", 2, 5.0, readActivity);

            List<Session> readSessions = readActivity.getSessions();
            assertEquals(2, readSessions.size());
            checkSession(2.0, 1, "2023-10-20", readSessions.get(0));
            checkSession(3.0, 2, "2023-10-21", readSessions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterActivityWithNoSessions() {
        try {
            ActivityTracker tracker = new ActivityTracker();
            Activity activity = new Activity("Meditation");
            tracker.addActivity(activity);

            JsonWriter writer = new JsonWriter("./data/testWriterActivityWithNoSessions.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterActivityWithNoSessions.json");
            tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(1, activities.size());
            Activity readActivity = activities.get(0);
            checkActivity("Meditation", 0, 0.0, readActivity);
            assertEquals(0, readActivity.getSessions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterCloseWithoutOpen() {
        JsonWriter writer = new JsonWriter("./data/testWriterCloseWithoutOpen.json");
        assertThrows(Exception.class, writer::close);
    }
}
