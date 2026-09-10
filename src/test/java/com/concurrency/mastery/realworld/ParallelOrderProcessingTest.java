package com.concurrency.mastery.realworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 39 — Parallel Order Processing")
class ParallelOrderProcessingTest {

    private ParallelOrderProcessing exercise;

    @BeforeEach
    void setUp() {
        exercise = new ParallelOrderProcessing();
    }

    @Test
    @DisplayName("Should process order with all components")
    void shouldProcessOrder() throws Exception {
        CompletableFuture<String> result = exercise.processOrder("ORD-001", "CUST-001");

        String summary = result.get(10, TimeUnit.SECONDS);
        assertNotNull(summary);
        assertTrue(summary.contains("Customer:"), "Should contain customer info");
        assertTrue(summary.contains("Inventory:"), "Should contain inventory check");
        assertTrue(summary.contains("Price:"), "Should contain pricing");
        assertTrue(summary.contains("Recommendations:"), "Should contain recommendations");
    }

    @Test
    @DisplayName("Should process multiple orders concurrently")
    void shouldProcessMultipleOrders() throws Exception {
        CompletableFuture<String> order1 = exercise.processOrder("ORD-001", "CUST-001");
        CompletableFuture<String> order2 = exercise.processOrder("ORD-002", "CUST-002");
        CompletableFuture<String> order3 = exercise.processOrder("ORD-003", "CUST-003");

        CompletableFuture.allOf(order1, order2, order3).get(10, TimeUnit.SECONDS);

        assertTrue(order1.isDone());
        assertTrue(order2.isDone());
        assertTrue(order3.isDone());
    }
}
