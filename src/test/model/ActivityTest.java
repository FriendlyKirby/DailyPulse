package model;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityTest {
    private Activity testActivity;

    @BeforeEach
    void runBefore() {
        testActivity = new Activity("Coding");
    }

    @Test
    void testConstructor() {
        assertEquals("Coding", testActivity.getName());
        assertEquals(0, testActivity.getSessions().size());
        assertEquals(0, testActivity.getStreak());
        assertEquals(0.0, testActivity.getTotalTime(), 0.001);
        assertEquals(1, testActivity.getNextSessionId());
    }

    @Test
    void testSetName() {
        testActivity.setName("Drawing");
        assertEquals("Drawing", testActivity.getName());
    }

    @Test
    void testSetStreak() {
        testActivity.setStreak(0);
        assertEquals(0, testActivity.getStreak());
        testActivity.setStreak(1);
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testSetTotalTime() {
        testActivity.setTotalTime(1.0);
        assertEquals(1.0, testActivity.getTotalTime(), 0.001);
        testActivity.setTotalTime(0.0);
        assertEquals(0.0, testActivity.getTotalTime(), 0.001);
    }

    @Test
    void testSetNextSessionId() {
        testActivity.setNextSessionId(5);
        assertEquals(5, testActivity.getNextSessionId());
        testActivity.setNextSessionId(2);
        assertEquals(2, testActivity.getNextSessionId());
    }

    @Test
    void testAddSession() {
        Session session1 = new Session(5.0);
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(5.0, testActivity.getTotalTime(), 0.001);
        assertEquals(1, session1.getId());
        assertEquals(2, testActivity.getNextSessionId());
    }

    @Test
    void testAddMultipleSessions() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);

        Session session2 = new Session(10.0);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);

        assertEquals(2, testActivity.getSessions().size());
        assertEquals(15.0, testActivity.getTotalTime(), 0.001);
        assertEquals(2, testActivity.getStreak());
        assertEquals(5.0, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 1)), 0.001);
    }

    @Test
    void testAddSessionNonConsecutiveDate() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getStreak());

        Session session2 = new Session(5.0);
        session2.setDate(LocalDate.of(2020, 1, 3));
        testActivity.addSession(session2);
        assertEquals(1, testActivity.getStreak());
        assertEquals(2, testActivity.getSessions().size());
    }

    @Test
    void testRemoveSession() {
        Session session1 = new Session(5.0);
        Session session2 = new Session(10.0);
        testActivity.addSession(session1);
        testActivity.addSession(session2);

        assertEquals(2, testActivity.getSessions().size());
        assertEquals(15.0, testActivity.getTotalTime(), 0.001);

        testActivity.removeSession(session1);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(10.0, testActivity.getTotalTime(), 0.001);
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testUpdateStreak() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);

        Session session2 = new Session(5.0);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);

        Session session3 = new Session(5.0);
        session3.setDate(LocalDate.of(2020, 1, 3));
        testActivity.addSession(session3);

        assertEquals(3, testActivity.getStreak());

        testActivity.removeSession(session3);
        assertEquals(2, testActivity.getStreak());
    }

    @Test
    void testGetSessionsOnDate() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);

        Session session2 = new Session(10.0);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);

        Session session3 = new Session(15.0);
        session3.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session3);

        // sessions from 2020/1/1
        List<Session> sessions1 = testActivity.getSessionsOnDate(LocalDate.of(2020, 1, 1));
        assertEquals(2, sessions1.size());
        assertEquals(5.0, sessions1.get(0).getDurationInHours(), 0.001);
        assertEquals(15.0, sessions1.get(1).getDurationInHours(), 0.001);

        // sessions from 2020/1/2
        List<Session> sessions2 = testActivity.getSessionsOnDate(LocalDate.of(2020, 1, 2));
        assertEquals(1, sessions2.size());
        assertEquals(10.0, sessions2.get(0).getDurationInHours(), 0.001);
    }

    @Test
    void testGetDateTotalTime() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);

        Session session2 = new Session(10.0);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);

        Session session3 = new Session(15.0);
        session3.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session3);

        // sessions from 2020/1/1
        assertEquals(20.0, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 1)), 0.001);
        // sessions from 2020/1/2
        assertEquals(10.0, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 2)), 0.001);
    }

    @Test
    void testToString() {
        Session session1 = new Session(5.0);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);

        Session session2 = new Session(10.0);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);

        String expected = "Activity Name: Coding, Sessions: 2, Streak: 2 days, Total Time: 15.00 hours";
        assertEquals(expected, testActivity.toString());
    }

    @Test
    void testToStringEmpty() {
        String expected = "Activity Name: Coding, Sessions: 0, Streak: 0 days, Total Time: 0.00 hours";
        assertEquals(expected, testActivity.toString());
    }
}
