package com.concurrency.mastery.completablefuture;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 38 — Custom Executor with CompletableFuture")
class CustomExecutorExerciseTest {

    private CustomExecutorExercise exercise;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        exercise = new CustomExecutorExercise();
        executor = exercise.createExecutor(4);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Should create executor")
    void shouldCreateExecutor() {
        assertNotNull(executor);
        assertFalse(executor.isShutdown());
    }

    @Test
    @DisplayName("Should supply on custom executor")
    void shouldSupplyOnExecutor() throws Exception {
        var result = exercise.supplyOn(executor, () -> 42);
        assertEquals(42, result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should run pipeline on custom executor")
    void shouldPipelineOnExecutor() throws Exception {
        var result = exercise.pipelineOnExecutor(executor, "alice");
        assertEquals("Hello, USER-ALICE", result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Tasks should run on executor threads, not ForkJoinPool")
    void tasksShouldRunOnExecutorThreads() throws Exception {
        AtomicReference<String> threadName = new AtomicReference<>();

        exercise.supplyOn(executor, () -> {
            threadName.set(Thread.currentThread().getName());
            return "done";
        }).get(5, TimeUnit.SECONDS);

        // Thread name should contain pool-N-thread-M pattern, not ForkJoinPool
        assertNotNull(threadName.get());
        assertFalse(threadName.get().contains("ForkJoinPool"),
            "Should run on custom executor, not common pool");
    }

    @Test
    @DisplayName("Should shut down executor gracefully")
    void shouldShutdownGracefully() throws InterruptedException {
        assertTrue(exercise.shutdownGracefully(executor, 5000));
        assertTrue(executor.isShutdown());
        assertTrue(executor.isTerminated());
    }
}
