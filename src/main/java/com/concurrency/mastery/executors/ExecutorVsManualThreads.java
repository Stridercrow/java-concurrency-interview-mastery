package com.concurrency.mastery.executors;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 08 — Executor vs Manual Threads
 *
 * Scenario:
 * You need to process 100 small tasks. Creating 100 threads manually is wasteful.
 * This exercise demonstrates why ExecutorService is preferred over manual thread creation.
 */
public class ExecutorVsManualThreads {

    /**
     * Process tasks by creating a new Thread for each one.
     * This demonstrates the naive approach.
     *
     * @param tasks the tasks to process
     * @return the number of tasks completed
     */
    public int processManually(List<Runnable> tasks) throws InterruptedException {
        // TODO: For each task, create a new Thread, start it, then join() it.
        // Count the number of tasks completed.
        // This is the naive "thread per task" approach.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Process tasks using an ExecutorService with a fixed thread pool.
     * This demonstrates the proper approach.
     *
     * @param tasks the tasks to process
     * @param poolSize the number of threads in the pool
     * @return the number of tasks completed
     */
    public int processWithExecutor(List<Runnable> tasks, int poolSize) throws InterruptedException {
        // TODO: Create a fixed thread pool of the given size.
        // Submit each task, then shut down the executor and wait for termination.
        // Count the number of tasks completed.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Count how many threads were active during processing.
     * Use this to compare the number of threads created by each approach.
     *
     * @param tasks the tasks to process
     * @param poolSize the thread pool size
     * @return the maximum number of concurrently active threads
     */
    public int countConcurrentThreads(List<Runnable> tasks, int poolSize) throws InterruptedException {
        // TODO: Create a fixed thread pool and submit all tasks.
        // Track the maximum number of threads running simultaneously.
        // Hint: Use a thread-safe counter that threads update when they start/end.
        throw new UnsupportedOperationException("Implement this method");
    }
}
