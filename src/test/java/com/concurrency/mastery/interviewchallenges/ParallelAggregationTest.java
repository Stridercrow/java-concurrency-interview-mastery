package com.concurrency.mastery.interviewchallenges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge 3 — Parallel Aggregation")
class ParallelAggregationTest {

    private ParallelAggregation exercise;

    @BeforeEach
    void setUp() {
        exercise = new ParallelAggregation();
    }

    @Test
    @DisplayName("Should aggregate sequentially")
    void shouldAggregateSequentially() {
        List<ParallelAggregation.Order> orders = List.of(
            new ParallelAggregation.Order("alice", 100),
            new ParallelAggregation.Order("bob", 200),
            new ParallelAggregation.Order("alice", 150)
        );

        var result = exercise.aggregateSequential(orders);

        assertEquals(450, result.totalRevenue());
        assertEquals(2, result.ordersPerCustomer().get("alice"));
        assertEquals(1, result.ordersPerCustomer().get("bob"));
    }

    @Test
    @DisplayName("Should aggregate in parallel")
    void shouldAggregateInParallel() throws Exception {
        List<ParallelAggregation.Order> orders = List.of(
            new ParallelAggregation.Order("alice", 100),
            new ParallelAggregation.Order("bob", 200),
            new ParallelAggregation.Order("alice", 150),
            new ParallelAggregation.Order("charlie", 300),
            new ParallelAggregation.Order("bob", 50)
        );

        var result = exercise.aggregate(orders, 3);

        assertEquals(800, result.totalRevenue());
        assertEquals(2, result.ordersPerCustomer().get("alice"));
        assertEquals(2, result.ordersPerCustomer().get("bob"));
        assertEquals(1, result.ordersPerCustomer().get("charlie"));
    }

    @Test
    @DisplayName("Parallel and sequential results should match")
    void parallelAndSequentialShouldMatch() {
        List<ParallelAggregation.Order> orders = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            orders.add(new ParallelAggregation.Order("customer-" + (i % 10), (i % 100) + 1));
        }

        var seqResult = exercise.aggregateSequential(orders);

        try {
            var parResult = exercise.aggregate(orders, 4);
            assertEquals(seqResult.totalRevenue(), parResult.totalRevenue());
            assertEquals(seqResult.ordersPerCustomer(), parResult.ordersPerCustomer());
        } catch (Exception e) {
            fail("Parallel aggregation should not throw: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle empty orders")
    void shouldHandleEmptyOrders() throws Exception {
        var result = exercise.aggregate(List.of(), 4);

        assertEquals(0, result.totalRevenue());
        assertTrue(result.ordersPerCustomer().isEmpty());
    }
}
