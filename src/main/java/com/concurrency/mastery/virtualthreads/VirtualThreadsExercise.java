package com.concurrency.mastery.virtualthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Exercise 42 — Virtual Threads (Java 21+)
 *
 * Scenario:
 * You're building an I/O-heavy service that makes many blocking calls
 * (HTTP requests, database queries). Virtual threads handle this efficiently
 * by freeing carrier threads during blocking operations.
 *
 * This exercise introduces virtual threads and compares them to platform threads.
 */
public class VirtualThreadsExercise {

    /**
     * Create and start a virtual thread.
     *
     * @param task the task to run
     * @return the virtual thread
     */
    public Thread createVirtualThread(Runnable task) {
        // TODO: Create a virtual thread using Thread.ofVirtual()
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a named virtual thread.
     *
     * @param name the thread name
     * @param task the task to run
     * @return the virtual thread
     */
    public Thread createNamedVirtualThread(String name, Runnable task) {
        // TODO: Create a virtual thread with the given name
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run tasks using virtual threads via ExecutorService.
     *
     * @param taskCount the number of tasks
     * @param task the task to run
     * @return the number of tasks completed
     */
    public int runWithVirtualThreads(int taskCount, Callable<Integer> task) throws Exception {
        // TODO: Create an ExecutorService using Executors.newVirtualThreadPerTaskExecutor()
        // Submit taskCount tasks, collect results, shut down executor.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run tasks using platform threads via ExecutorService.
     * Use this for comparison with virtual threads.
     *
     * @param taskCount the number of tasks
     * @param poolSize the number of platform threads
     * @param task the task to run
     * @return the number of tasks completed
     */
    public int runWithPlatformThreads(int taskCount, int poolSize, Callable<Integer> task) throws Exception {
        // TODO: Create a fixed thread pool and submit tasks.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Simulate a blocking I/O operation (e.g., HTTP request).
     * Virtual threads handle this efficiently; platform threads tie up resources.
     *
     * @param sleepMs the simulated I/O delay
     * @return a result string
     */
    public String simulateIO(long sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "result-" + Thread.currentThread().getName();
    }
}
