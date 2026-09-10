package com.concurrency.mastery.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Exercise 11 — Callable + Future
 *
 * Scenario:
 * You're building a data aggregation service. You need to submit computation
 * tasks and retrieve their results, handle timeouts, and manage exceptions.
 */
public class CallableFutureExercise {

    /**
     * Submit a Callable to the executor and return the Future.
     *
     * @param executor the executor
     * @param task the callable task
     * @return the Future
     */
    public <T> Future<T> submitTask(ExecutorService executor, Callable<T> task) {
        // TODO: Submit the callable and return the Future
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit a Callable and get the result, waiting up to the specified timeout.
     *
     * @param executor the executor
     * @param task the callable task
     * @param timeoutMs the maximum time to wait
     * @return the result
     * @throws TimeoutException if the task doesn't complete in time
     * @throws ExecutionException if the task throws an exception
     * @throws InterruptedException if interrupted while waiting
     */
    public <T> T submitAndGet(ExecutorService executor, Callable<T> task, long timeoutMs)
            throws TimeoutException, ExecutionException, InterruptedException {
        // TODO: Submit the callable, wait up to timeoutMs, and return the result.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit a Callable that is expected to throw an exception.
     * Catch the ExecutionException and return the root cause message.
     *
     * @param executor the executor
     * @param task the task that will throw
     * @return the message of the root cause exception
     */
    public String submitAndHandleException(ExecutorService executor, Callable<Void> task) {
        // TODO: Submit the callable, catch ExecutionException, and return the
        // root cause's message. Use getCause() to get the original exception.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Cancel a running task.
     *
     * @param executor the executor
     * @param task the task to run (and cancel)
     * @param interrupt if true, interrupt the running task
     * @return true if the task was successfully cancelled
     */
    public boolean submitAndCancel(ExecutorService executor, Callable<String> task, boolean interrupt) {
        // TODO: Submit the task, then immediately cancel it, and return whether cancellation succeeded.
        throw new UnsupportedOperationException("Implement this method");
    }
}
