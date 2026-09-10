package com.concurrency.mastery.threadpools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 12 — Fixed Thread Pool")
class FixedThreadPoolExerciseTest {

    private FixedThreadPoolExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new FixedThreadPoolExercise();
    }

    @Test
    @DisplayName("Should process all tasks")
    void shouldProcessAllTasks() throws InterruptedException {
        int completed = exercise.processTasks(20, 4);
        assertEquals(20, completed, "All tasks should be processed");
    }

    @Test
    @DisplayName("Should process tasks in parallel and collect results")
    void shouldProcessInParallel() throws InterruptedException {
        List<String> inputs = Arrays.asList("hello", "world", "java", "concurrency");
        List<String> results = exercise.processInParallel(inputs, 2);

        assertNotNull(results);
        assertEquals(4, results.size());
        assertTrue(results.contains("HELLO"));
        assertTrue(results.contains("WORLD"));
        assertTrue(results.contains("JAVA"));
        assertTrue(results.contains("CONCURRENCY"));
    }

    @Test
    @DisplayName("Should limit concurrency to pool size")
    void shouldLimitConcurrency() throws InterruptedException {
        int maxConcurrent = exercise.demonstrateBoundedConcurrency(10, 3);
        assertTrue(maxConcurrent <= 3,
            "Max concurrent tasks should not exceed pool size (got " + maxConcurrent + ")");
    }
}
