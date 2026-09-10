package com.concurrency.mastery.executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 10 — ExecutorService Lifecycle")
class ExecutorServiceLifecycleTest {

    private ExecutorServiceLifecycle exercise;

    @BeforeEach
    void setUp() {
        exercise = new ExecutorServiceLifecycle();
    }

    @Test
    @DisplayName("Should perform graceful shutdown")
    void shouldGracefulShutdown() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger counter = new AtomicInteger(0);

        // Submit some quick tasks
        for (int i = 0; i < 5; i++) {
            executor.submit(counter::incrementAndGet);
        }

        boolean graceful = exercise.gracefulShutdown(executor, 5000);
        assertTrue(graceful, "Should shut down gracefully");
        assertEquals(5, counter.get(), "All tasks should have completed");
    }

    @Test
    @DisplayName("Should submit cancellable task")
    void shouldSubmitCancellableTask() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = exercise.submitCancellableTask(executor, () -> {
                Thread.sleep(10000);
                return "done";
            });

            assertNotNull(future);
            assertFalse(future.isCancelled());

            future.cancel(true); // interrupt
            assertTrue(future.isCancelled() || future.isDone());
        } finally {
            exercise.gracefulShutdown(executor, 1000);
        }
    }

    @Test
    @DisplayName("Should detect and handle interruption")
    void shouldHandleInterruption() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean running = new AtomicBoolean(false);

        exercise.submitInterruptibleTask(executor, running);

        // Wait for task to start
        Thread.sleep(100);
        assertTrue(running.get(), "Task should be running");

        // Interrupt the executor's thread
        executor.shutdownNow();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        assertFalse(running.get(), "Task should have stopped after interruption");
    }
}
