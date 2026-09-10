package com.concurrency.mastery.threadfundamentals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 01 — Creating Threads")
class CreatingThreadsTest {

    private CreatingThreads exercise;

    @BeforeEach
    void setUp() {
        exercise = new CreatingThreads();
    }

    @Test
    @DisplayName("Should create and start a thread that executes the task")
    void shouldCreateAndStartThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> threadName = new AtomicReference<>();

        Thread t = exercise.createAndStartThread(() -> {
            threadName.set(Thread.currentThread().getName());
            latch.countDown();
        });

        assertNotNull(t, "The returned Thread should not be null");
        assertTrue(t.isAlive(), "Thread should be alive after start()");
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Task should complete within timeout");

        assertNotNull(threadName.get(), "The task should have executed");
    }

    @Test
    @DisplayName("Should create a thread with the correct name")
    void shouldCreateNamedThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> actualName = new AtomicReference<>();

        Thread t = exercise.createNamedThread(() -> {
            actualName.set(Thread.currentThread().getName());
            latch.countDown();
        }, "log-processor-1");

        assertNotNull(t);
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertEquals("log-processor-1", actualName.get());
    }

    @Test
    @DisplayName("Should run task on thread and wait for completion")
    void shouldRunAndWait() throws InterruptedException {
        List<String> results = Collections.synchronizedList(new ArrayList<>());

        Runnable task = () -> {
            results.add("line-1");
            results.add("line-2");
            results.add("line-3");
        };

        exercise.runAndWait(task, results);

        assertEquals(3, results.size(), "All results should be collected");
        assertTrue(results.contains("line-1"));
        assertTrue(results.contains("line-2"));
        assertTrue(results.contains("line-3"));
    }

    @Test
    @DisplayName("start() should execute on a different thread")
    void startShouldExecuteOnDifferentThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> threadName = new AtomicReference<>();

        String result = exercise.demonstrateStartVsRun(() -> {
            threadName.set(Thread.currentThread().getName());
            latch.countDown();
        }, true);

        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertNotEquals("main", threadName.get(),
            "start() should execute on a new thread, not main");
    }

    @Test
    @DisplayName("run() should execute on the calling thread")
    void runShouldExecuteOnCallingThread() {
        String result = exercise.demonstrateStartVsRun(() -> {
            // no-op, just testing thread name
        }, false);

        assertEquals(Thread.currentThread().getName(), result,
            "run() should execute on the calling (main) thread");
    }
}
