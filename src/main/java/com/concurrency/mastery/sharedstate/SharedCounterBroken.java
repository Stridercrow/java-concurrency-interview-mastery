package com.concurrency.mastery.sharedstate;

/**
 * Exercise 15 — Shared Counter (Broken)
 *
 * Scenario:
 * Multiple threads share a counter and increment it concurrently.
 * This implementation is intentionally broken — it has a race condition.
 *
 * The test will demonstrate the race condition by running the counter
 * from multiple threads. The final count should be predictable but won't be.
 *
 * Do NOT fix this class yet — Exercise 16 asks you to compare solutions.
 */
public class SharedCounterBroken {

    private int count = 0;

    /**
     * Increment the counter. This method is NOT thread-safe.
     *
     * The count++ operation is actually three steps:
     * 1. Read current value
     * 2. Add 1
     * 3. Write new value
     *
     * When multiple threads do this concurrently, they can read the same
     * value and overwrite each other's increments.
     */
    public void increment() {
        count++; // This is NOT atomic
    }

    /**
     * Get the current count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Reset the counter to zero.
     */
    public void reset() {
        count = 0;
    }

    /**
     * Increment the counter n times.
     * Intentionally NOT synchronized — to demonstrate the race condition.
     */
    public void incrementMultipleTimes(int n) {
        for (int i = 0; i < n; i++) {
            increment();
        }
    }
}
