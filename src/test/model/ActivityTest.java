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
        assertEquals(0, testActivity.getTotalTime());
        assertEquals(1, testActivity.getNextSessionId());
    }

    @Test
    void testSetName() {
        testActivity.setName("Drawing");
        assertEquals("Drawing", testActivity.getName());
    }

    @Test
    void testAddSession() {
        Session session1 = new Session(5);
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getSessions().size());
    }

    @Test
    void testAddMultipleSessions() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(5);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getSessions().size());
        assertEquals(5, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 1)));
    }
    
    @Test
    void testUpdateSession() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(5);
        session2.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session2);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(10, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 1)));
    }

    @Test
    void testRemoveSessionBefore() {
        Session session1 = new Session(5);
        Session session2 = new Session(10);
        testActivity.addSession(session1);
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getSessions().size());
        assertEquals(15, testActivity.getTotalTime());
        assertEquals(2, testActivity.getStreak());
        testActivity.removeSession(session1);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(10, testActivity.getTotalTime());
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testRemoveSessionAfter() {
        Session session1 = new Session(5);
        Session session2 = new Session(10);
        testActivity.addSession(session1);
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getSessions().size());
        assertEquals(15, testActivity.getTotalTime());
        assertEquals(2, testActivity.getStreak());
        testActivity.removeSession(session2);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(5, testActivity.getTotalTime());
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testRemoveMultiple() {
        Session session1 = new Session(5);
        Session session2 = new Session(10);
        testActivity.addSession(session1);
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getSessions().size());
        assertEquals(15, testActivity.getTotalTime());
        testActivity.removeSession(session1);
        testActivity.removeSession(session2);
        assertEquals(0, testActivity.getSessions().size());
        assertEquals(0, testActivity.getTotalTime());
        assertEquals(0, testActivity.getStreak());
    }

    @Test
    void testRemoveSessionNotExist() {
        Session session1 = new Session(5);
        testActivity.addSession(session1);
        Session session0 = new Session(10);
        testActivity.removeSession(session0);
        assertEquals(1, testActivity.getSessions().size());
        assertEquals(5, testActivity.getTotalTime());
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testGetSessionsOnDate() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);
        Session session3 = new Session(15);
        session3.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session3);
        // sessions from 2020/1/1
        List<Session> sessions1 = testActivity.getSessionsOnDate(LocalDate.of(2020, 1, 1));
        assertEquals(2, sessions1.size());
        assertEquals(5, sessions1.get(0).getDurationInHours());
        assertEquals(15, sessions1.get(1).getDurationInHours());
        // sessions from 2020/1/2
        List<Session> sessions2 = testActivity.getSessionsOnDate(LocalDate.of(2020, 1, 2));
        assertEquals(1, sessions2.size());
        assertEquals(10, sessions2.get(0).getDurationInHours());
    }

    @Test
    void testGetStreak() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getStreak());
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getStreak());
    }

    @Test
    void testGetStreakSameDay() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getStreak());
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session2);
        assertEquals(1, testActivity.getStreak());
    }

    @Test
    void testGetStreakBroken() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        assertEquals(1, testActivity.getStreak());
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 3));
        testActivity.addSession(session2);
        assertEquals(1, testActivity.getStreak());
        Session session3 = new Session(15);
        session3.setDate(LocalDate.of(2020, 1, 4));
        testActivity.addSession(session3);
        assertEquals(2, testActivity.getStreak());
    }

    @Test
    void testGetTotalTime() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session2);
        Session session3 = new Session(15);
        session3.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session3);
        assertEquals(25, testActivity.getTotalTime());
    }

    @Test
    void testGetDateTotalTime() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);
        Session session3 = new Session(15);
        session3.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session3);
        // sessions from 2020/1/1
        assertEquals(20, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 1)));
        // sessions from 2020/1/2
        assertEquals(10, testActivity.getDateTotalTime(LocalDate.of(2020, 1, 2)));
    }

    @Test
    void testGetNextSessionId() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        assertEquals(2, testActivity.getNextSessionId());
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session2);
        assertEquals(2, testActivity.getNextSessionId());
        Session session3 = new Session(15);
        session3.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session3);
        assertEquals(3, testActivity.getNextSessionId());
    }

    @Test
    void testActivityToString() {
        Session session1 = new Session(5);
        session1.setDate(LocalDate.of(2020, 1, 1));
        testActivity.addSession(session1);
        Session session2 = new Session(10);
        session2.setDate(LocalDate.of(2020, 1, 2));
        testActivity.addSession(session2);
        assertEquals("Activity Name: Drawing, Streak: 2 days, Total Time: 15 hours, Sessions: 2", testActivity.toString());
    }
}