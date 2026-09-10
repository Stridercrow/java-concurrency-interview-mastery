package com.concurrency.mastery.executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 09 — ExecutorService Basics")
class ExecutorServiceBasicsTest {

    private ExecutorServiceBasics exercise;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        exercise = new ExecutorServiceBasics();
        executor = exercise.createFixedPool(2);
    }

    @AfterEach
    void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    @Test
    @DisplayName("Should create fixed thread pool")
    void shouldCreateFixedPool() {
        assertNotNull(executor);
        assertFalse(executor.isShutdown());
    }

    @Test
    @DisplayName("Should create single-thread executor")
    void shouldCreateSingleThread() {
        ExecutorService single = exercise.createSingleThread();
        assertNotNull(single);
        single.shutdown();
    }

    @Test
    @DisplayName("Should submit Runnable task")
    void shouldSubmitRunnable() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);
        Future<?> future = exercise.submitRunnable(executor, counter::incrementAndGet);

        assertNotNull(future);
        future.get(5, TimeUnit.SECONDS);
        assertEquals(1, counter.get());
    }

    @Test
    @DisplayName("Should submit Callable task and get result")
    void shouldSubmitCallable() throws Exception {
        Callable<String> task = () -> "hello";
        Future<String> future = exercise.submitCallable(executor, task);

        assertNotNull(future);
        assertEquals("hello", future.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should detect shutdown state")
    void shouldDetectShutdown() {
        assertFalse(exercise.isShutdown(executor));
        executor.shutdown();
        assertTrue(exercise.isShutdown(executor));
    }

    @Test
    @DisplayName("Should detect terminated state after shutdown completes")
    void shouldDetectTerminated() throws InterruptedException {
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        assertTrue(exercise.isTerminated(executor));
    }
}
