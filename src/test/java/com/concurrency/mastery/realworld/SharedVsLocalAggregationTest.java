package com.concurrency.mastery.realworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 41 — Shared vs Local Aggregation")
class SharedVsLocalAggregationTest {

    private SharedVsLocalAggregation exercise;

    @BeforeEach
    void setUp() {
        exercise = new SharedVsLocalAggregation();
    }

    @Test
    @DisplayName("Should count with shared state")
    void shouldCountWithSharedState() throws Exception {
        List<String> records = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        int count = exercise.countWithSharedState(records, 4);
        assertEquals(8, count);
    }

    @Test
    @DisplayName("Should count with local state")
    void shouldCountWithLocalState() throws Exception {
        List<String> records = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        int count = exercise.countWithLocalState(records, 4);
        assertEquals(8, count);
    }

    @Test
    @DisplayName("Both approaches should produce the same result")
    void bothApproachesShouldAgree() throws Exception {
        List<String> records = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        int sharedResult = exercise.countWithSharedState(records, 3);
        int localResult = exercise.countWithLocalState(records, 3);

        assertEquals(sharedResult, localResult,
            "Both approaches should produce the same count");
        assertEquals(10, sharedResult);
    }

    @Test
    @DisplayName("Should partition correctly")
    void shouldPartitionCorrectly() {
        List<String> list = List.of("a", "b", "c", "d", "e");
        List<List<String>> partitions = exercise.partition(list, 3);

        assertEquals(3, partitions.size());
        assertEquals(2, partitions.get(0).size()); // a, b
        assertEquals(2, partitions.get(1).size()); // c, d
        assertEquals(1, partitions.get(2).size()); // e
    }
}
