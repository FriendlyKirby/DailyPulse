package persistence;

import model.Activity;
import model.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkActivity(String name, int streak, double totalTime, Activity activity) {
        assertEquals(name, activity.getName());
        assertEquals(streak, activity.getStreak());
        assertEquals(totalTime, activity.getTotalTime(), 0.001);
    }

    protected void checkSession(double durationInHours, int sessionId, String date, Session session) {
        assertEquals(durationInHours, session.getDurationInHours(), 0.001);
        assertEquals(sessionId, session.getId());
        assertEquals(date, session.getDate().toString());
    }
}
