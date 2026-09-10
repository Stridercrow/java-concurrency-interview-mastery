package com.concurrency.mastery.realworld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 41: Shared vs Local Aggregation
 *
 * Key Concepts:
 * - Shared state approach: all threads update one shared counter
 * - Local state approach: each thread counts locally, results combined at end
 * - Local state avoids contention — better performance under high concurrency
 * - Shared state is simpler but scales poorly with many threads
 *
 * Performance comparison:
 * ┌──────────────────────┬───────────────────┬───────────────────────────────┐
 * │ Metric               │ Shared (Atomic)   │ Local (partition + sum)       │
 * ├──────────────────────┼───────────────────┼───────────────────────────────┤
 * │ Contention           │ High (CAS retry)  │ None (no shared writes)       │
 * │ Scalability          │ Degrades with     │ Linear scaling                │
 * │                      │ more threads      │                               │
 * │ Code complexity      │ Simple            │ Moderate (partitioning)       │
 * │ Memory               │ O(1)              │ O(poolSize) local counters    │
 * └──────────────────────┴───────────────────┴───────────────────────────────┘
 *
 * Rule of thumb:
 * - Few threads, simple counter → shared AtomicInteger is fine
 * - Many threads, high throughput → local state + final aggregation
 */
public class SharedVsLocalAggregationSolution {

    /**
     * Approach A: All threads increment a shared AtomicInteger.
     *
     * Under high concurrency, CAS retries increase because multiple
     * threads compete for the same counter. Performance degrades
     * as thread count grows.
     */
    public int countWithSharedState(List<String> records, int poolSize) throws Exception {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (String record : records) {
                futures.add(executor.submit(() -> counter.incrementAndGet()));
            }
            for (Future<?> f : futures) {
                f.get();
            }
            return counter.get();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Approach B: Partition records, count locally, sum at the end.
     *
     * Each thread writes only to its local counter — zero contention.
     * The final sum is a single-threaded operation that's negligible.
     */
    public int countWithLocalState(List<String> records, int poolSize) throws Exception {
        List<List<String>> partitions = partition(records, poolSize);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<Integer>> futures = new ArrayList<>();
            for (List<String> chunk : partitions) {
                futures.add(executor.submit(() -> chunk.size()));
            }
            int total = 0;
            for (Future<Integer> f : futures) {
                total += f.get();
            }
            return total;
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Partition a list into approximately equal chunks.
     * Each chunk becomes an independent task — no shared mutable state.
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
