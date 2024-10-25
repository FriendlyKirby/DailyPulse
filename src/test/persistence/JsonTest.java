package persistence;

import model.Session;
import model.Activity;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

// credit to JsonSerializationDemo
public class JsonTest {
    protected void checkSession(int duration, int id, String date, Session session) {
        assertEquals(duration, session.getDurationInHours());
        assertEquals(id, session.getId());
        assertEquals(LocalDate.parse(date), session.getDate());
    }

    protected void checkActivity(String name, int streak, int totalTime, Activity activity) {
        assertEquals(name, activity.getName());
        assertEquals(streak, activity.getStreak());
        assertEquals(totalTime, activity.getTotalTime());
    }
}
