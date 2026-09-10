package com.concurrency.mastery.concurrentcollections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 29 — ConcurrentHashMap")
class ConcurrentHashMapExerciseTest {

    private ConcurrentHashMapExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new ConcurrentHashMapExercise();
    }

    @Test
    @DisplayName("Should put and get prices")
    void shouldPutAndGet() {
        exercise.putPrice("SKU-001", 100);
        exercise.putPrice("SKU-002", 200);

        assertEquals(100, exercise.getPrice("SKU-001"));
        assertEquals(200, exercise.getPrice("SKU-002"));
        assertEquals(-1, exercise.getPrice("SKU-999"));
    }

    @Test
    @DisplayName("Should compute price if absent")
    void shouldComputeIfAbsent() {
        int price = exercise.getOrComputePrice("SKU-001", sku -> 150);
        assertEquals(150, price);

        // Second call should return cached value, not recompute
        int price2 = exercise.getOrComputePrice("SKU-001", sku -> 250);
        assertEquals(150, price2, "Should return cached value");
    }

    @Test
    @DisplayName("Should count word occurrences")
    void shouldCountWords() {
        String[] words = {"java", "concurrency", "java", "thread", "java", "concurrency"};
        Map<String, Integer> counts = exercise.countWords(words);

        assertEquals(3, counts.get("java"));
        assertEquals(2, counts.get("concurrency"));
        assertEquals(1, counts.get("thread"));
    }

    @Test
    @DisplayName("Should handle concurrent puts safely")
    void shouldHandleConcurrentPuts() throws InterruptedException {
        int threadCount = 8;
        int itemsPerThread = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < itemsPerThread; j++) {
                    exercise.putPrice("SKU-" + threadId + "-" + j, j);
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(threadCount * itemsPerThread, exercise.size());
    }

    @Test
    @DisplayName("Should handle concurrent countWords safely")
    void shouldHandleConcurrentCountWords() throws InterruptedException {
        String[] words = {"a", "b", "a", "c", "b", "a"};
        int threadCount = 4;
        int iterationsPerThread = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        Map<String, Integer> result = new java.util.concurrent.ConcurrentHashMap<>();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < iterationsPerThread; j++) {
                    for (String word : words) {
                        result.merge(word, 1, Integer::sum);
                    }
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(threadCount * iterationsPerThread * 2, result.get("a"));
        assertEquals(threadCount * iterationsPerThread * 2, result.get("b"));
        assertEquals(threadCount * iterationsPerThread * 1, result.get("c"));
    }
}
