package com.concurrency.mastery.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * SOLUTION — Exercise 11: Callable + Future
 *
 * Key Concepts:
 * - Callable<V> is like Runnable but returns a value and can throw checked exceptions
 * - Future<V> represents the pending result of a Callable
 * - Future.get() blocks until the result is available
 * - Future.get(timeout, unit) throws TimeoutException if too slow
 * - ExecutionException wraps exceptions thrown by the Callable
 * - Future.cancel() can abort a running task
 *
 * Exception Hierarchy:
 *   Callable throws Exception
 *     → ExecutionException wraps it
 *       → getCause() returns the original exception
 *
 * Common patterns:
 * - submit + get: basic result retrieval
 * - submit + get(timeout): timeout-bounded retrieval
 * - submit + cancel: abort slow tasks
 */
public class CallableFutureExerciseSolution {

    /**
     * Submit a Callable and return the Future.
     *
     * The Future is a handle to the pending result. It completes when
     * the Callable finishes execution.
     */
    public <T> Future<T> submitTask(ExecutorService executor, Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * Submit a Callable and get the result with a timeout.
     *
     * Future.get(timeout, unit) is essential for production code:
     * - Prevents indefinite blocking
     * - Allows graceful degradation when services are slow
     * - Throws TimeoutException if the task doesn't complete in time
     */
    public <T> T submitAndGet(ExecutorService executor, Callable<T> task, long timeoutMs)
            throws TimeoutException, ExecutionException, InterruptedException {
        Future<T> future = executor.submit(task);
        return future.get(timeoutMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Submit a failing Callable and extract the root cause.
     *
     * ExecutionException wraps any exception thrown by the Callable.
     * getCause() returns the original exception — this is critical for
     * proper error handling and debugging.
     */
    public String submitAndHandleException(ExecutorService executor, Callable<Void> task) {
        Future<Void> future = executor.submit(task);
        try {
            future.get();
            return null; // Should not reach here
        } catch (ExecutionException e) {
            return e.getCause().getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted";
        }
    }

    /**
     * Submit a task and immediately cancel it.
     *
     * cancel(true) attempts to interrupt the running thread.
     * cancel(false) only cancels if not yet started.
     *
     * isDone() returns true after cancellation, success, or exception.
     */
    public boolean submitAndCancel(ExecutorService executor, Callable<String> task, boolean interrupt) {
        Future<String> future = executor.submit(task);
        future.cancel(interrupt);
        return future.isCancelled();
    }
}
