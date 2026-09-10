package com.concurrency.mastery.realworld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Exercise 40: Parallel Data Processing
 *
 * Key Concepts:
 * - Sequential: simple, predictable, but slow for large datasets
 * - Thread pool: good balance of concurrency and resource control
 * - CompletableFuture: composable async pipelines
 *
 * Why NOT thread-per-item:
 * - Creating 10,000 threads for 10,000 records wastes memory (~1MB each)
 * - OS context switching overhead overwhelms actual work
 * - Thread pools reuse a fixed set of threads → efficient
 *
 * Processing strategies:
 * ┌──────────────────────┬────────────────────────────────────────────┐
 * │ Strategy             │ When to use                                │
 * ├──────────────────────┼────────────────────────────────────────────┤
 * │ Sequential           │ Small datasets, simple logic               │
 * │ Thread pool          │ Large datasets, CPU/IO bound work          │
 * │ CompletableFuture    │ Complex async pipelines, dependent steps   │
 * │ Virtual threads      │ I/O-heavy, many blocking operations        │
 * └──────────────────────┴────────────────────────────────────────────┘
 */
public class ParallelDataProcessingSolution {

    /**
     * Process records sequentially. Simple and correct, but single-threaded.
     */
    public List<String> processSequential(List<String> records) {
        List<String> results = new ArrayList<>();
        for (String record : records) {
            results.add(processRecord(record));
        }
        return results;
    }

    /**
     * Process records using a fixed thread pool.
     *
     * Each record is submitted as an independent Callable.
     * Future.get() collects results in submission order.
     * The pool limits concurrency to poolSize threads.
     */
    public List<String> processWithThreadPool(List<String> records, int poolSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for (String record : records) {
                futures.add(executor.submit(() -> processRecord(record)));
            }
            List<String> results = new ArrayList<>();
            for (Future<String> f : futures) {
                results.add(f.get());
            }
            return results;
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Process records using CompletableFuture for async composition.
     *
     * CompletableFuture.supplyAsync starts each task asynchronously.
     * allOf() waits for all futures to complete.
     * Then we collect results in order.
     *
     * This pattern is useful when you need to compose multiple async steps.
     */
    public List<String> processWithCompletableFuture(List<String> records, int poolSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<CompletableFuture<String>> futures = new ArrayList<>();
            for (String record : records) {
                futures.add(CompletableFuture.supplyAsync(() -> processRecord(record), executor));
            }

            // Wait for all to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Collect results in order
            List<String> results = new ArrayList<>();
            for (CompletableFuture<String> f : futures) {
                results.add(f.join());
            }
            return results;
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Simulate record processing (e.g., validation, transformation).
     */
    public String processRecord(String record) {
        return record.toUpperCase();
    }
}
