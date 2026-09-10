package com.concurrency.mastery.threadfundamentals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exercise 01 — Creating Threads
 *
 * Scenario:
 * You are building a log processing system. Multiple log files need to be
 * processed concurrently. Each log file processing task is represented by
 * a Runnable that extracts lines matching a pattern.
 *
 * Your task:
 * Implement methods that demonstrate correct thread creation and execution.
 */
public class CreatingThreads {

    /**
     * Create and start a thread that runs the given Runnable.
     * The thread should be started (not just have run() called).
     *
     * @param task the task to run in a new thread
     * @return the started Thread
     */
    public Thread createAndStartThread(Runnable task) {
        // TODO: Create a new Thread with the given task and start it.
        // Return the thread reference.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create and start a named thread.
     *
     * @param task the task to run
     * @param name the thread name
     * @return the started Thread
     */
    public Thread createNamedThread(Runnable task, String name) {
        // TODO: Create a new Thread with the given task, set the name, and start it.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run a task on a new thread and wait for it to complete.
     * Collect any results (output strings) from the task.
     *
     * @param task a task that adds results to the provided list
     * @return the collected results (thread-safe)
     */
    public List<String> runAndWait(Runnable task, List<String> results) {
        // TODO: Create a thread, start it, then wait for it to complete using join().
        // The task will add entries to the results list.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Demonstrate the difference between start() and run().
     *
     * @param task the task
     * @return the name of the thread that executed the task
     */
    public String demonstrateStartVsRun(Runnable task, boolean useStart) {
        if (useStart) {
            // TODO: Start the task on a new thread. Return the executing thread's name.
            throw new UnsupportedOperationException("Implement this method");
        } else {
            // TODO: Call run() directly. Return the executing thread's name.
            throw new UnsupportedOperationException("Implement this method");
        }
    }
}
