package com.concurrency.mastery.volatilememory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 23 — volatile Shutdown Flag")
class VolatileShutdownFlagTest {

    private VolatileShutdownFlag exercise;

    @BeforeEach
    void setUp() {
        exercise = new VolatileShutdownFlag();
    }

    @Test
    @DisplayName("Worker should stop promptly after shutdown with volatile")
    void workerShouldStopPromptly() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Thread worker = exercise.startWorker(counter);

        Thread.sleep(50); // let the worker run
        long shutdownTime = System.currentTimeMillis();
        exercise.shutdown();

        worker.join(2000);
        long stopTime = System.currentTimeMillis();

        assertFalse(worker.isAlive(),
            "Worker should stop after volatile shutdown flag is set");
        assertTrue(counter.get() > 0,
            "Worker should have done some work");
    }

    @Test
    @DisplayName("volatile ensures visibility: worker sees flag change")
    void volatileEnsuresVisibility() throws InterruptedException {
        // Run the test multiple times to increase confidence
        for (int i = 0; i < 10; i++) {
            exercise.reset();
            AtomicInteger counter = new AtomicInteger(0);
            Thread worker = exercise.startWorker(counter);

            Thread.sleep(10);
            exercise.shutdown();
            worker.join(1000);

            assertFalse(worker.isAlive(),
                "Run " + i + ": Worker should stop after volatile flag change");
        }
    }
}
