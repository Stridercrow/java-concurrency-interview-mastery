package com.concurrency.mastery.completablefuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 36 — Composition")
class CompositionExerciseTest {

    private CompositionExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new CompositionExercise();
    }

    @Test
    @DisplayName("Should compose dependent operations with thenCompose")
    void shouldComposeWithThenCompose() throws Exception {
        CompletableFuture<String> summary = exercise.getCustomerOrderSummary("alice");

        String result = summary.get(5, TimeUnit.SECONDS);
        assertTrue(result.contains("User:alice"), "Should contain user info");
        assertTrue(result.contains("Orders:"), "Should contain orders");
    }

    @Test
    @DisplayName("Should combine independent operations with thenCombine")
    void shouldCombineWithThenCombine() throws Exception {
        CompletableFuture<String> result = exercise.getCustomerWithAccount("bob");

        String data = result.get(5, TimeUnit.SECONDS);
        assertTrue(data.contains("Name:bob"), "Should contain name");
        assertTrue(data.contains("Balance:"), "Should contain balance");
    }

    @Test
    @DisplayName("Should wait for all futures with allOf")
    void shouldWaitForAllWithAllOf() throws Exception {
        List<String> items = Arrays.asList("laptop", "phone", "tablet");
        CompletableFuture<List<Integer>> prices = exercise.getAllPrices(items);

        List<Integer> result = prices.get(5, TimeUnit.SECONDS);
        assertEquals(3, result.size());
        for (Integer price : result) {
            assertTrue(price >= 0, "Price should be non-negative");
        }
    }
}
