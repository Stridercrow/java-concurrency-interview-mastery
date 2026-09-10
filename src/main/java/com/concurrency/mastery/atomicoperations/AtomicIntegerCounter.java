package com.concurrency.mastery.atomicoperations;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 25 — AtomicInteger Counter
 *
 * Scenario:
 * You're building a thread-safe counter for a high-throughput metrics system.
 * AtomicInteger provides lock-free atomic operations using CAS (Compare-And-Swap).
 *
 * Practice the key AtomicInteger methods and understand when they're appropriate.
 */
public class AtomicIntegerCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Get the current value.
     */
    public int get() {
        // TODO: Return the current value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Atomically increment by 1 and return the new value.
     */
    public int incrementAndGet() {
        // TODO: Atomically increment and return the new value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Atomically increment by 1 and return the old value.
     */
    public int getAndIncrement() {
        // TODO: Atomically return the old value and then increment
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Atomically add the given value and return the new value.
     *
     * @param delta the value to add
     */
    public int addAndGet(int delta) {
        // TODO: Atomically add delta and return the new value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Atomically set to the given value.
     *
     * @param newValue the new value
     */
    public void set(int newValue) {
        // TODO: Atomically set the value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Atomically set to newValue if the current value == expectedValue.
     * This is the CAS (Compare-And-Swap) operation.
     *
     * @param expectedValue the expected current value
     * @param newValue the new value to set
     * @return true if the value was updated, false if the current value didn't match
     */
    public boolean compareAndSet(int expectedValue, int newValue) {
        // TODO: Perform CAS and return whether it succeeded
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Increment the counter n times using compareAndSet in a loop.
     * This demonstrates the CAS loop pattern — the foundation of lock-free algorithms.
     *
     * @param n the number of times to increment
     */
    public void casLoopIncrement(int n) {
        // TODO: Implement a CAS loop that increments the counter n times.
        // Pattern:
        //   1. Read current value
        //   2. Try compareAndSet(current, current + 1)
        //   3. If it fails (another thread changed the value), retry
        throw new UnsupportedOperationException("Implement this method");
    }
}
