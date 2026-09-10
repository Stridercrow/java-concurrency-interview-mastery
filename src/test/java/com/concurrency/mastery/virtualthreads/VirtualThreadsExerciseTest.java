package com.concurrency.mastery.virtualthreads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 42 — Virtual Threads")
class VirtualThreadsExerciseTest {

    private VirtualThreadsExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new VirtualThreadsExercise();
    }

    @Test
    @DisplayName("Should create and run a virtual thread")
    void shouldCreateVirtualThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> threadName = new AtomicReference<>();

        Thread vt = exercise.createVirtualThread(() -> {
            threadName.set(Thread.currentThread().getName());
            latch.countDown();
        });

        assertNotNull(vt);
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertNotNull(threadName.get());
    }

    @Test
    @DisplayName("Should create a named virtual thread")
    void shouldCreateNamedVirtualThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> threadName = new AtomicReference<>();

        Thread vt = exercise.createNamedVirtualThread("my-vt", () -> {
            threadName.set(Thread.currentThread().getName());
            latch.countDown();
        });

        assertNotNull(vt);
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(threadName.get().contains("my-vt"),
            "Thread name should contain the specified name");
    }

    @Test
    @DisplayName("Should run tasks with virtual threads")
    void shouldRunWithVirtualThreads() throws Exception {
        Callable<Integer> task = () -> 42;
        int result = exercise.runWithVirtualThreads(10, task);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("Should run tasks with platform threads")
    void shouldRunWithPlatformThreads() throws Exception {
        Callable<Integer> task = () -> 42;
        int result = exercise.runWithPlatformThreads(10, 2, task);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("Virtual threads can handle many concurrent blocking operations")
    void virtualThreadsCanHandleManyBlockingOps() throws Exception {
        int taskCount = 100;
        Callable<Integer> blockingTask = () -> { exercise.simulateIO(10); return 1; };

        long start = System.currentTimeMillis();
        int completed = exercise.runWithVirtualThreads(taskCount, blockingTask);
        long elapsed = System.currentTimeMillis() - start;

        assertEquals(taskCount, completed);
        // Virtual threads should handle 100 concurrent blocking ops efficiently
        assertTrue(elapsed < 10000, "Should complete in reasonable time (took " + elapsed + "ms)");
    }
}
