package com.concurrency.mastery.interviewchallenges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge 1 — Background Worker Shutdown")
class BackgroundWorkerShutdownTest {

    private BackgroundWorkerShutdown exercise;

    @BeforeEach
    void setUp() {
        exercise = new BackgroundWorkerShutdown();
    }

    @Test
    @DisplayName("Should start and run periodically")
    void shouldStartAndRun() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        exercise.start(counter::incrementAndGet);

        assertTrue(exercise.isRunning());
        Thread.sleep(350); // let it run a few iterations

        exercise.shutdown();
        assertTrue(exercise.awaitShutdown(2000));

        int count = counter.get();
        assertTrue(count >= 2, "Should have run at least 2 times (ran " + count + " times)");
    }

    @Test
    @DisplayName("Should stop gracefully on shutdown")
    void shouldStopGracefully() throws InterruptedException {
        exercise.start(() -> {
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        assertTrue(exercise.isRunning());

        exercise.shutdown();
        assertTrue(exercise.awaitShutdown(2000), "Worker should stop within timeout");
        assertFalse(exercise.isRunning());
    }

    @Test
    @DisplayName("Should be interruptible")
    void shouldBeInterruptible() throws InterruptedException {
        CountDownLatch running = new CountDownLatch(1);
        exercise.start(() -> {
            running.countDown();
            try { Thread.sleep(10000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        running.await(2, TimeUnit.SECONDS);
        exercise.shutdown();
        assertTrue(exercise.awaitShutdown(2000));
    }

    @Test
    @DisplayName("Should be reusable after shutdown")
    void shouldBeReusable() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);

        exercise.start(counter::incrementAndGet);
        Thread.sleep(200);
        exercise.shutdown();
        exercise.awaitShutdown(2000);

        int countAfterFirst = counter.get();

        exercise.start(counter::incrementAndGet);
        Thread.sleep(200);
        exercise.shutdown();
        exercise.awaitShutdown(2000);

        assertTrue(counter.get() > countAfterFirst,
            "Worker should run again after restart");
    }
}
