package com.concurrency.mastery.coordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 32 — Semaphore")
class SemaphoreExerciseTest {

    private SemaphoreExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new SemaphoreExercise(3); // max 3 concurrent
    }

    @Test
    @DisplayName("Should execute task with permit")
    void shouldExecuteWithPermit() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        exercise.executeWithPermit(counter::incrementAndGet);
        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("Should limit concurrent access to max permits")
    void shouldLimitConcurrency() throws InterruptedException {
        int taskCount = 10;
        AtomicInteger concurrent = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch allStarted = new CountDownLatch(1);

        ExecutorService executor = Executors.newFixedThreadPool(taskCount);

        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> {
                try {
                    exercise.executeWithPermit(() -> {
                        int current = concurrent.incrementAndGet();
                        maxConcurrent.updateAndGet(prev -> Math.max(prev, current));
                        try {
                            allStarted.await();
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            concurrent.decrementAndGet();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(200); // let some tasks acquire permits
        allStarted.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertTrue(exercise.getMaxActiveCount() <= 3,
            "Max concurrent should not exceed permits (got " + exercise.getMaxActiveCount() + ")");
    }

    @Test
    @DisplayName("Should run multiple tasks with concurrency limit")
    void shouldRunWithConcurrencyLimit() throws Exception {
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int val = i;
            tasks.add(() -> val * 2);
        }

        List<Integer> results = exercise.runWithConcurrencyLimit(tasks);

        assertEquals(10, results.size());
        for (int i = 0; i < 10; i++) {
            assertTrue(results.contains(i * 2));
        }
    }
}
