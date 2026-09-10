package com.concurrency.mastery.realworld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 41 — Shared vs Local Aggregation
 *
 * Scenario:
 * You need to count records in parallel. Compare:
 * A. Shared mutable result (synchronized or atomic)
 * B. Worker-local results + final aggregation
 *
 * Focus on contention and design simplicity.
 */
public class SharedVsLocalAggregation {

    /**
     * Approach A: Shared mutable counter.
     * Multiple threads increment the same shared counter.
     *
     * @param records the records to count
     * @param poolSize the thread pool size
     * @return the total count
     */
    public int countWithSharedState(List<String> records, int poolSize) throws Exception {
        // TODO: Create a fixed thread pool.
        // Use a shared AtomicInteger counter.
        // Submit tasks that each increment the counter for their assigned records.
        // Return the final count.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Approach B: Worker-local results + final aggregation.
     * Each worker counts its own local subset, then results are combined.
     *
     * @param records the records to count
     * @param poolSize the thread pool size
     * @return the total count
     */
    public int countWithLocalState(List<String> records, int poolSize) throws Exception {
        // TODO: Create a fixed thread pool.
        // Partition records into poolSize chunks.
        // Submit a Callable for each chunk that counts records locally.
        // Collect all local counts and sum them.
        // This avoids shared mutable state entirely.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Partition a list into approximately equal chunks.
     */
    public <T> List<List<T>> partition(List<T> list, int chunks) {
        List<List<T>> result = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) list.size() / chunks);
        for (int i = 0; i < list.size(); i += chunkSize) {
            result.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return result;
    }
}
