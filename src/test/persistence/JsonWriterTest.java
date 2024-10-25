package persistence;

import model.ActivityTracker;
import model.Activity;
import model.Session;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// credit to JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ActivityTracker tracker = new ActivityTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (IOException e) {
            // Expected exception caught
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
            activity.setStreak(3);
            activity.setTotalTime(5);
            activity.setNextSessionId(3);

            Session session1 = new Session(2);
            session1.setId(1);
            session1.setDate(LocalDate.parse("2023-10-20"));
            Session session2 = new Session(3);
            session2.setId(2);
            session2.setDate(LocalDate.parse("2023-10-21"));

            activity.getSessions().add(session1);
            activity.getSessions().add(session2);

            tracker.addActivity(activity);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralActivityTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralActivityTracker.json");
            tracker = reader.read();
            List<Activity> activities = tracker.getActivities();
            assertEquals(1, activities.size());
            Activity readActivity = activities.get(0);
            checkActivity("Running", 3, 5, readActivity);

            List<Session> readSessions = readActivity.getSessions();
            assertEquals(2, readSessions.size());
            checkSession(2, 1, "2023-10-20", readSessions.get(0));
            checkSession(3, 2, "2023-10-21", readSessions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
