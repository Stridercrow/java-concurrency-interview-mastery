package com.concurrency.mastery.sharedstate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Exercise 14: Independent Workers
 *
 * Key Concepts:
 * - Avoiding shared mutable state eliminates the need for synchronization
 * - Each worker operates on its own data — no locks, no contention
 * - This is the "thread confinement" pattern
 * - The only coordination needed is collecting final results
 *
 * Why independent workers are better:
 * 1. No synchronization overhead
 * 2. No deadlocks possible
 * 3. Scales linearly with thread count (for I/O-bound work)
 * 4. Easier to reason about — pure functions
 *
 * When shared state IS needed:
 * - Use atomic variables for simple counters
 * - Use synchronized for complex critical sections
 * - Use concurrent collections for shared data structures
 */
public class IndependentWorkersSolution {

    /**
     * Count words in a single document. Pure function — no shared state.
     */
    public int countWords(String document) {
        if (document == null || document.isBlank()) return 0;
        return document.trim().split("\\s+").length;
    }

    /**
     * Process documents in parallel. Each task is completely independent.
     *
     * Key insight: the Callable captures only the `document` parameter (immutable)
     * and the `this` reference for calling countWords(). No shared mutable state
     * is accessed.
     */
    public List<Integer> countWordsInParallel(List<String> documents, int poolSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<Integer>> futures = new ArrayList<>();
            for (String doc : documents) {
                futures.add(executor.submit(() -> countWords(doc)));
            }
            List<Integer> results = new ArrayList<>();
            for (Future<Integer> f : futures) {
                results.add(f.get());
            }
            return results;
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Sequential processing for comparison.
     * Same logic, just no parallelism.
     */
    public int countWordsSequential(List<String> documents) {
        int total = 0;
        for (String doc : documents) {
            total += countWords(doc);
        }
        return total;
    }

    /**
     * Compare parallel and sequential approaches.
     * Both MUST produce the same total — this verifies correctness.
     */
    public long[] compareApproaches(List<String> documents, int poolSize) throws Exception {
        int sequentialTotal = countWordsSequential(documents);
        List<Integer> parallelResults = countWordsInParallel(documents, poolSize);
        int parallelTotal = parallelResults.stream().mapToInt(Integer::intValue).sum();
        return new long[]{sequentialTotal, parallelTotal};
    }
}
