package com.concurrency.mastery.sharedstate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Exercise 14 — Independent Workers
 *
 * Scenario:
 * You need to count words in multiple documents. Each document is independent —
 * no shared state is needed. This exercise demonstrates that avoiding shared
 * mutable state can eliminate synchronization entirely.
 */
public class IndependentWorkers {

    /**
     * Count words in a single document. This is a pure function with no shared state.
     *
     * @param document the text to count words in
     * @return the number of words
     */
    public int countWords(String document) {
        if (document == null || document.isBlank()) return 0;
        return document.trim().split("\\s+").length;
    }

    /**
     * Process multiple documents in parallel, each as an independent task.
     * Each worker maintains its own local result — no shared mutable state.
     *
     * @param documents the list of documents
     * @param poolSize the number of threads
     * @return a list of word counts, one per document
     */
    public List<Integer> countWordsInParallel(List<String> documents, int poolSize) throws Exception {
        // TODO: Create a fixed thread pool.
        // For each document, submit a Callable that counts its words.
        // Collect all results from the Futures and return them.
        // The key insight: each task is independent — no shared mutable state.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Demonstrate the benefit of independent workers.
     * Process documents sequentially and return the total word count.
     *
     * @param documents the list of documents
     * @return total word count
     */
    public int countWordsSequential(List<String> documents) {
        // TODO: Count words in all documents sequentially and return the total.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Compare parallel and sequential processing.
     * Both should produce the same total word count.
     *
     * @param documents the list of documents
     * @param poolSize the number of threads
     * @return [sequentialTotal, parallelTotal]
     */
    public long[] compareApproaches(List<String> documents, int poolSize) throws Exception {
        // TODO: Run both sequential and parallel approaches.
        // Verify they produce the same total word count.
        // Return [sequentialTotal, parallelTotal].
        throw new UnsupportedOperationException("Implement this method");
    }
}
