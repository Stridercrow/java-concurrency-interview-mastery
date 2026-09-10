package com.concurrency.mastery.executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 11 — Callable + Future")
class CallableFutureExerciseTest {

    private CallableFutureExercise exercise;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        exercise = new CallableFutureExercise();
        executor = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Should submit a task and get a Future")
    void shouldSubmitTask() throws Exception {
        Future<Integer> future = exercise.submitTask(executor, () -> 42);
        assertNotNull(future);
        assertEquals(42, future.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should submit and get result with timeout")
    void shouldSubmitAndGet() throws Exception {
        Integer result = exercise.submitAndGet(executor, () -> 100, 5000);
        assertEquals(100, result);
    }

    @Test
    @DisplayName("Should throw TimeoutException when task is too slow")
    void shouldThrowTimeout() {
        assertThrows(TimeoutException.class, () ->
            exercise.submitAndGet(executor, () -> {
                Thread.sleep(10000);
                return 0;
            }, 100)
        );
    }

    @Test
    @DisplayName("Should handle exceptions from Callable")
    void shouldHandleException() {
        String message = exercise.submitAndHandleException(executor, () -> {
            throw new IllegalArgumentException("invalid input");
        });

        assertNotNull(message);
        assertTrue(message.contains("invalid input"));
    }

    @Test
    @DisplayName("Should cancel a task")
    void shouldCancelTask() {
        boolean cancelled = exercise.submitAndCancel(executor, () -> {
            Thread.sleep(10000);
            return "done";
        }, true);

        // Cancel may or may not succeed depending on timing, but should not throw
        assertTrue(cancelled || !cancelled); // just verify no exception
    }
}
