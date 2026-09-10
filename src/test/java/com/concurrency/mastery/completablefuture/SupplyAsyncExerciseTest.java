package com.concurrency.mastery.completablefuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 34 — supplyAsync()")
class SupplyAsyncExerciseTest {

    private SupplyAsyncExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new SupplyAsyncExercise();
    }

    @Test
    @DisplayName("Should supply a value asynchronously")
    void shouldSupplyAsync() throws Exception {
        CompletableFuture<String> future = exercise.supplyAsync(() -> "hello");

        assertNotNull(future);
        assertEquals("hello", future.get(5, TimeUnit.SECONDS));
        assertTrue(future.isDone());
    }

    @Test
    @DisplayName("Should supply on a specific executor")
    void shouldSupplyOnExecutor() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            CompletableFuture<Integer> future = exercise.supplyAsyncOnExecutor(() -> 42, executor);

            assertEquals(42, future.get(5, TimeUnit.SECONDS));
        } finally {
            executor.shutdown();
        }
    }

    @Test
    @DisplayName("Should supply and get with timeout")
    void shouldSupplyAndGet() throws Exception {
        String result = exercise.supplyAndGet(() -> "result", 5000);
        assertEquals("result", result);
    }

    @Test
    @DisplayName("Should complete asynchronously without blocking calling thread")
    void shouldNotBlockCallingThread() throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<String> future = exercise.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "done";
        });

        long elapsed = System.currentTimeMillis() - start;
        assertTrue(elapsed < 100, "supplyAsync should not block the calling thread (elapsed: " + elapsed + "ms)");

        assertEquals("done", future.get(5, TimeUnit.SECONDS));
    }
}
