package com.concurrency.mastery.threadpools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 12 — Fixed Thread Pool
 *
 * Scenario:
 * You're processing a batch of orders. Each order needs validation, which takes
 * some time. You have a fixed number of workers and need to observe how tasks
 * are queued when all workers are busy.
 */
public class FixedThreadPoolExercise {

    /**
     * Process tasks using a fixed thread pool. Track the number of tasks completed.
     * Each task increments the counter when it starts and sleeps briefly.
     *
     * @param taskCount the number of tasks to process
     * @param poolSize the number of threads in the pool
     * @return the number of tasks completed
     */
    public int processTasks(int taskCount, int poolSize) throws InterruptedException {
        // TODO: Create a fixed thread pool of the given size.
        // Submit taskCount tasks that each increment an AtomicInteger counter.
        // Shut down the executor and wait for termination.
        // Return the final counter value.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Process tasks and collect results in order. Each task transforms
     * the input string to uppercase.
     *
     * @param inputs the strings to process
     * @param poolSize the thread pool size
     * @return a list of uppercase strings
     */
    public List<String> processInParallel(List<String> inputs, int poolSize) throws InterruptedException {
        // TODO: Create a fixed thread pool.
        // Submit each input string as a task that converts it to uppercase.
        // Collect results via Future.get().
        // Shut down the executor.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Demonstrate that a fixed pool limits concurrency.
     * Track the maximum number of concurrent task executions.
     *
     * @param taskCount the number of tasks
     * @param poolSize the pool size
     * @return the maximum number of tasks running concurrently
     */
    public int demonstrateBoundedConcurrency(int taskCount, int poolSize) throws InterruptedException {
        // TODO: Create a fixed thread pool.
        // Submit tasks that each:
        //   1. Increment a concurrent counter
        //   2. Update a max concurrent counter
        //   3. Sleep briefly
        //   4. Decrement the concurrent counter
        // Return the maximum concurrent count observed.
        throw new UnsupportedOperationException("Implement this method");
    }
}
