package com.concurrency.mastery.coordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 31 — CountDownLatch")
class CountDownLatchExerciseTest {

    private CountDownLatchExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new CountDownLatchExercise();
    }

    @Test
    @DisplayName("Should wait for all workers to finish")
    void shouldWaitForAllWorkers() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);

        exercise.waitForAllWorkers(5, counter::incrementAndGet);

        assertEquals(5, counter.get(), "All workers should have completed");
    }

    @Test
    @DisplayName("Start gate should release all workers simultaneously")
    void startGateShouldReleaseAll() throws InterruptedException {
        List<Integer> results = exercise.startGatePattern(10);

        assertNotNull(results);
        assertEquals(10, results.size());
        for (int i = 0; i < 10; i++) {
            assertTrue(results.contains(i), "Missing worker index: " + i);
        }
    }

    @Test
    @DisplayName("Should wait for all events to complete")
    void shouldWaitForEvents() throws InterruptedException {
        long elapsed = exercise.waitForEvents(5, 50);

        assertTrue(elapsed >= 30, "Should wait at least ~50ms (measured: " + elapsed + "ms)");
        assertTrue(elapsed < 5000, "Should not wait unreasonably long");
    }
}
