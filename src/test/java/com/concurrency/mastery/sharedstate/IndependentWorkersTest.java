package com.concurrency.mastery.sharedstate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 14 — Independent Workers")
class IndependentWorkersTest {

    private IndependentWorkers exercise;

    @BeforeEach
    void setUp() {
        exercise = new IndependentWorkers();
    }

    @Test
    @DisplayName("Should count words in a single document")
    void shouldCountWords() {
        assertEquals(3, exercise.countWords("hello world java"));
        assertEquals(0, exercise.countWords(""));
        assertEquals(0, exercise.countWords(null));
        assertEquals(1, exercise.countWords("hello"));
    }

    @Test
    @DisplayName("Should count words in parallel")
    void shouldCountWordsInParallel() throws Exception {
        List<String> documents = Arrays.asList(
            "hello world",
            "java concurrency",
            "one two three four five"
        );

        List<Integer> results = exercise.countWordsInParallel(documents, 2);

        assertEquals(3, results.size());
        assertEquals(2, results.get(0));
        assertEquals(2, results.get(1));
        assertEquals(5, results.get(2));
    }

    @Test
    @DisplayName("Should count words sequentially")
    void shouldCountWordsSequentially() {
        List<String> documents = Arrays.asList(
            "hello world",
            "java concurrency",
            "one two three four five"
        );

        int total = exercise.countWordsSequential(documents);
        assertEquals(9, total);
    }

    @Test
    @DisplayName("Parallel and sequential should produce the same result")
    void approachesShouldAgree() throws Exception {
        List<String> documents = Arrays.asList(
            "the quick brown fox",
            "jumps over the lazy dog",
            "concurrency is hard"
        );

        long[] results = exercise.compareApproaches(documents, 4);

        assertEquals(results[0], results[1],
            "Sequential and parallel totals should match");
    }
}
