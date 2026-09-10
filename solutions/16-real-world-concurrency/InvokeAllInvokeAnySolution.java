package com.concurrency.mastery.threadpools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * SOLUTION — Exercise 13: invokeAll / invokeAny
 *
 * Key Concepts:
 * - invokeAll() — submit all tasks, wait for ALL to complete, return all Futures
 * - invokeAny() — submit all tasks, return the FIRST result, cancel the rest
 *
 * invokeAll vs invokeAny:
 * ┌──────────────────┬───────────────────────────────────────────────────┐
 * │ Method           │ Behavior                                          │
 * ├──────────────────┼───────────────────────────────────────────────────┤
 * │ invokeAll()      │ Waits for ALL tasks. Returns List<Future<T>>.     │
 * │                  │ Good when you need every result.                  │
 * ├──────────────────┼───────────────────────────────────────────────────┤
 * │ invokeAny()      │ Returns FIRST completed result. Cancels others.  │
 * │                  │ Good for redundant queries (e.g., multi-source). │
 * ├──────────────────┼───────────────────────────────────────────────────┤
 * │ invokeAll(timeout│ Waits up to timeout. Returns partial results.    │
 * │ )                │ Some futures may not be done yet.                │
 * └──────────────────┴───────────────────────────────────────────────────┘
 *
 * When to use:
 * - invokeAll: "I need results from ALL data sources"
 * - invokeAny: "I just need ANY result (e.g., fastest response)"
 * - invokeAll + timeout: "I need all results but can't wait forever"
 */
public class InvokeAllInvokeAnySolution {

    /**
     * Submit all tasks and wait for ALL to complete.
     *
     * invokeAll() blocks until every task finishes.
     * Returns a List of Futures in the same order as the input tasks.
     * Each Future is guaranteed to be done (not cancelled, not failed)
     * when invokeAll returns.
     */
    public <T> List<T> getAllResults(ExecutorService executor, List<Callable<T>> tasks)
            throws InterruptedException {
        List<Future<T>> futures = executor.invokeAll(tasks);
        List<T> results = new ArrayList<>();
        for (Future<T> f : futures) {
            results.add(f.get());
        }
        return results;
    }

    /**
     * Return the FIRST result from any task.
     *
     * invokeAny() returns as soon as ONE task completes successfully.
     * All other tasks are cancelled (via Thread.interrupt()).
     * Throws ExecutionException if ALL tasks fail.
     *
     * Perfect for: querying multiple replicas, racing DNS lookups, etc.
     */
    public <T> T getFirstResult(ExecutorService executor, List<Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return executor.invokeAny(tasks);
    }

    /**
     * Submit all tasks with a timeout. Return whatever completed in time.
     *
     * invokeAll(timeout) returns a list of Futures, but some may not be done.
     * Check future.isDone() before calling future.get() to avoid blocking.
     * Incomplete futures are cancelled.
     */
    public <T> List<T> getResultsWithTimeout(ExecutorService executor, List<Callable<T>> tasks, long timeoutMs)
            throws InterruptedException, ExecutionException, TimeoutException {
        List<Future<T>> futures = executor.invokeAll(tasks, timeoutMs, TimeUnit.MILLISECONDS);
        List<T> results = new ArrayList<>();
        for (Future<T> f : futures) {
            if (f.isDone() && !f.isCancelled()) {
                results.add(f.get());
            }
        }
        return results;
    }
}
