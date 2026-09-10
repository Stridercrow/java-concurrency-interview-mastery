package com.concurrency.mastery.realworld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Exercise 40 — Parallel Data Processing
 *
 * Scenario:
 * You need to process a large list of records. Compare:
 * 1. Sequential processing
 * 2. Thread-per-item (bad)
 * 3. Fixed thread pool (good)
 *
 * Understand why unbounded thread creation is dangerous.
 */
public class ParallelDataProcessing {

    /**
     * Process records sequentially.
     *
     * @param records the records to process
     * @return the processed results
     */
    public List<String> processSequential(List<String> records) {
        // TODO: Process each record sequentially and collect results
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Process records using a fixed thread pool.
     *
     * @param records the records to process
     * @param poolSize the thread pool size
     * @return the processed results
     */
    public List<String> processWithThreadPool(List<String> records, int poolSize) throws Exception {
        // TODO: Create a fixed thread pool, submit each record as a Callable,
        // collect results via Future.get(), shut down the pool.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Process records using CompletableFuture for async composition.
     *
     * @param records the records to process
     * @param poolSize the thread pool size
     * @return the processed results
     */
    public List<String> processWithCompletableFuture(List<String> records, int poolSize) throws Exception {
        // TODO: Create a fixed thread pool, use CompletableFuture.supplyAsync for each record,
        // wait for all to complete using allOf, collect results.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Simulate record processing (e.g., validation, transformation).
     *
     * @param record the record to process
     * @return the processed record
     */
    public String processRecord(String record) {
        // Simulate some processing work
        return record.toUpperCase();
    }
}
