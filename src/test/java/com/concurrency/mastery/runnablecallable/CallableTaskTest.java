package com.concurrency.mastery.runnablecallable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 06 — Callable Task")
class CallableTaskTest {

    private CallableTask exercise;

    @BeforeEach
    void setUp() {
        exercise = new CallableTask();
    }

    @Test
    @DisplayName("Should return string length")
    void shouldReturnStringLength() throws Exception {
        Callable<Integer> calculator = exercise.createLengthCalculator("hello");
        assertEquals(5, calculator.call());
        assertEquals(0, exercise.createLengthCalculator("").call());
        assertEquals(3, exercise.createLengthCalculator("abc").call());
    }

    @Test
    @DisplayName("Should parse valid integer")
    void shouldParseValidInteger() throws Exception {
        Callable<Integer> parser = exercise.createParser("42");
        assertEquals(42, parser.call());
    }

    @Test
    @DisplayName("Should throw NumberFormatException for invalid input")
    void shouldThrowForInvalidInput() {
        Callable<Integer> parser = exercise.createParser("not a number");
        assertThrows(Exception.class, parser::call);
    }

    @Test
    @DisplayName("Should compute sum correctly")
    void shouldComputeSum() throws Exception {
        Callable<Long> calculator = exercise.createSumCalculator(10);
        assertEquals(45L, calculator.call()); // 0+1+2+...+9 = 45
        assertEquals(0L, exercise.createSumCalculator(0).call());
        assertEquals(0L, exercise.createSumCalculator(1).call());
    }

    @Test
    @DisplayName("Should sleep and return message")
    void shouldSleepAndReturnMessage() throws Exception {
        long start = System.currentTimeMillis();
        Callable<String> task = exercise.createSlowTask(100, "done");
        String result = task.call();
        long elapsed = System.currentTimeMillis() - start;

        assertEquals("done", result);
        assertTrue(elapsed >= 80, "Should have slept for at least 80ms");
    }

    @Test
    @DisplayName("Callable can be submitted to ExecutorService and result retrieved via Future")
    void callableWithFuture() throws Exception {
        var executor = Executors.newSingleThreadExecutor();
        try {
            Callable<Integer> task = exercise.createLengthCalculator("concurrency");
            Future<Integer> future = executor.submit(task);

            assertEquals(11, future.get(5, TimeUnit.SECONDS));
            assertTrue(future.isDone());
        } finally {
            executor.shutdown();
        }
    }
}
