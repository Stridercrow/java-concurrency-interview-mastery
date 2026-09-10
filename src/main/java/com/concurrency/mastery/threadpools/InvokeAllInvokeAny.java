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
 * Exercise 13 — invokeAll / invokeAny
 *
 * Scenario:
 * You're building a service that queries multiple data sources concurrently.
 * You need to either wait for all results or return the fastest result.
 */
public class InvokeAllInvokeAny {

    /**
     * Submit all tasks and wait for all to complete. Return all results.
     *
     * @param executor the executor
     * @param tasks the callable tasks
     * @return list of results in the same order as the tasks
     */
    public <T> List<T> getAllResults(ExecutorService executor, List<Callable<T>> tasks)
            throws InterruptedException {
        // TODO: Use invokeAll() to submit all tasks and wait for all results.
        // Collect the results from each Future and return them.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit all tasks and return the result of the first one to complete.
     * The other tasks may be cancelled.
     *
     * @param executor the executor
     * @param tasks the callable tasks
     * @return the first result
     */
    public <T> T getFirstResult(ExecutorService executor, List<Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        // TODO: Use invokeAny() to return the first completed result.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit all tasks with a timeout. If not all complete within the timeout,
     * return only the results that are available.
     *
     * @param executor the executor
     * @param tasks the callable tasks
     * @param timeoutMs the maximum time to wait
     * @return list of completed results (may be smaller than task list)
     */
    public <T> List<T> getResultsWithTimeout(ExecutorService executor, List<Callable<T>> tasks, long timeoutMs)
            throws InterruptedException, ExecutionException, TimeoutException {
        // TODO: Use invokeAll() with a timeout.
        // Collect results from completed futures, skip incomplete ones.
        throw new UnsupportedOperationException("Implement this method");
    }
}
