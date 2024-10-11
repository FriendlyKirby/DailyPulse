package model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
    private Session testSession;

    @BeforeEach
    void runBefore() {
        testSession = new Session(10);
    }

    @Test
    void testConstructor() {
        assertEquals(10, testSession.getDurationInHours());
        assertEquals(1, testSession.getId());
        assertEquals(LocalDate.now(), testSession.getDate());
    }

    @Test
    void testSetId() {
        assertEquals(1, testSession.getId());
        testSession.setId(2);
        assertEquals(2, testSession.getId());
    }

    @Test
    void testToString() {
        assertEquals("Session ID: 1, Duration: 10 hours, Date: 2024-10-11",
        testSession.toString());
    }

}