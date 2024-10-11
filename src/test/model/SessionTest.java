package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
    private Session testSession;

    @BeforeEach
    void runBefore() {
        testSession = new Session(0);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}