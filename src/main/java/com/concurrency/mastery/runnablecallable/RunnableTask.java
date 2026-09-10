package com.concurrency.mastery.runnablecallable;

import java.util.List;
import java.util.ArrayList;

/**
 * Exercise 05 — Runnable Task
 *
 * Scenario:
 * You're building a system that processes log entries. Each log entry needs
 * to be validated and categorized. Since no return value is needed (the task
 * writes results to a shared output), Runnable is the right choice.
 */
public class RunnableTask {

    /**
     * Create a Runnable that, when executed, adds the processed log line
     * to the results list.
     *
     * The processing simply converts the line to uppercase.
     *
     * @param line the log line to process
     * @param results the list to add the processed line to
     * @return a Runnable that processes the line
     */
    public Runnable createLogProcessor(String line, List<String> results) {
        // TODO: Return a Runnable that converts the line to uppercase and adds it to results.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a Runnable that prints "Hello from <threadName>" to the given list.
     *
     * @param output list to collect the output strings
     * @return a Runnable that adds a greeting
     */
    public Runnable createGreeter(List<String> output) {
        // TODO: Return a Runnable that adds "Hello from <thread name>" to the output list.
        // Use Thread.currentThread().getName() to get the thread name.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a Runnable that counts from 0 to n-1, adding each number to the list.
     * This Runnable should handle InterruptedException gracefully by re-interrupting
     * the current thread.
     *
     * @param n the count limit
     * @param results list to collect numbers
     * @return a Runnable that counts
     */
    public Runnable createCounter(int n, List<Integer> results) {
        // TODO: Return a Runnable that counts from 0 to n-1.
        // For each number, add it to results and then sleep for 1ms.
        // If interrupted, re-interrupt the current thread and break.
        throw new UnsupportedOperationException("Implement this method");
    }
}
