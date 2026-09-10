package com.concurrency.mastery.runnablecallable;

import java.util.concurrent.Callable;

/**
 * Exercise 06 — Callable Task
 *
 * Scenario:
 * You're building a system that needs to compute results concurrently.
 * Unlike Runnable, Callable can return a value and throw checked exceptions.
 * You need to implement Callables that compute and return results.
 */
public class CallableTask {

    /**
     * Create a Callable that returns the length of the given string.
     *
     * @param text the string to measure
     * @return a Callable<Integer> that returns the string length
     */
    public Callable<Integer> createLengthCalculator(String text) {
        // TODO: Return a Callable that computes and returns text.length()
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a Callable that parses a string to an integer.
     * If the string is not a valid integer, throw NumberFormatException.
     *
     * @param numberString the string to parse
     * @return a Callable<Integer> that parses the string
     */
    public Callable<Integer> createParser(String numberString) {
        // TODO: Return a Callable that parses numberString to an integer.
        // Let NumberFormatException propagate (Callable can throw checked exceptions).
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a Callable that computes the sum of integers from 0 to n-1.
     *
     * @param n the upper bound (exclusive)
     * @return a Callable<Long> that returns the sum
     */
    public Callable<Long> createSumCalculator(int n) {
        // TODO: Return a Callable that computes n*(n-1)/2
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a Callable that sleeps for the specified duration and then
     * returns a completion message. Demonstrates that Callable can perform
     * blocking work and return a result.
     *
     * @param sleepMs how long to sleep
     * @param message the message to return after sleeping
     * @return a Callable<String> that sleeps and returns the message
     */
    public Callable<String> createSlowTask(long sleepMs, String message) {
        // TODO: Return a Callable that sleeps for sleepMs and returns the message.
        throw new UnsupportedOperationException("Implement this method");
    }
}
