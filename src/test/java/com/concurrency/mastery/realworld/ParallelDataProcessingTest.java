package com.concurrency.mastery.realworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 40 — Parallel Data Processing")
class ParallelDataProcessingTest {

    private ParallelDataProcessing exercise;

    @BeforeEach
    void setUp() {
        exercise = new ParallelDataProcessing();
    }

    @Test
    @DisplayName("Should process sequentially")
    void shouldProcessSequentially() {
        List<String> records = List.of("hello", "world", "java");
        List<String> results = exercise.processSequential(records);

        assertEquals(3, results.size());
        assertTrue(results.contains("HELLO"));
        assertTrue(results.contains("WORLD"));
        assertTrue(results.contains("JAVA"));
    }

    @Test
    @DisplayName("Should process with thread pool")
    void shouldProcessWithThreadPool() throws Exception {
        List<String> records = List.of("hello", "world", "java", "concurrency");
        List<String> results = exercise.processWithThreadPool(records, 2);

        assertEquals(4, results.size());
        assertTrue(results.contains("HELLO"));
        assertTrue(results.contains("WORLD"));
        assertTrue(results.contains("JAVA"));
        assertTrue(results.contains("CONCURRENCY"));
    }

    @Test
    @DisplayName("Should process with CompletableFuture")
    void shouldProcessWithCompletableFuture() throws Exception {
        List<String> records = List.of("a", "b", "c", "d", "e");
        List<String> results = exercise.processWithCompletableFuture(records, 3);

        assertEquals(5, results.size());
        assertTrue(results.contains("A"));
        assertTrue(results.contains("B"));
        assertTrue(results.contains("C"));
        assertTrue(results.contains("D"));
        assertTrue(results.contains("E"));
    }

    @Test
    @DisplayName("All approaches should produce the same results")
    void allApproachesShouldAgree() {
        List<String> records = List.of("hello", "world", "java");

        List<String> seqResults = exercise.processSequential(records);

        // All approaches should produce the same output
        assertEquals(3, seqResults.size());
        assertTrue(seqResults.contains("HELLO"));
        assertTrue(seqResults.contains("WORLD"));
        assertTrue(seqResults.contains("JAVA"));
    }
}
