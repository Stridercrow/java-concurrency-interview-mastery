package com.concurrency.mastery.sharedstate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 16 — Shared Counter Solutions
 *
 * Scenario:
 * The SharedCounterBroken exercise demonstrated that counter++ is not thread-safe.
 * Now compare three approaches to fixing it:
 * 1. synchronized
 * 2. AtomicInteger
 * 3. Avoiding shared state entirely
 *
 * Implement each approach and understand the tradeoffs.
 */
public class SharedCounterSolutions {

    // === Approach 1: synchronized ===
    private int synchronizedCount = 0;

    /**
     * Increment using synchronized. Only one thread can execute this at a time.
     */
    public synchronized void incrementSynchronized() {
        synchronizedCount++;
    }

    /**
     * Get count using synchronized.
     */
    public synchronized int getSynchronizedCount() {
        return synchronizedCount;
    }

    // === Approach 2: AtomicInteger ===
    private final AtomicInteger atomicCount = new AtomicInteger(0);

    /**
     * Increment using AtomicInteger. Uses CAS (Compare-And-Swap) instead of locking.
     */
    public void incrementAtomic() {
        atomicCount.incrementAndGet();
    }

    /**
     * Get count using AtomicInteger.
     */
    public int getAtomicCount() {
        return atomicCount.get();
    }

    // === Approach 3: Thread-local / Partitioned state ===

    /**
     * Demonstrate avoiding shared state by using thread-local computation.
     * Each thread counts independently, and results are combined at the end.
     *
     * @param incrementsPerThread how many times each thread should increment
     * @param threadCount the number of threads
     * @return the total count
     */
    public int incrementWithoutSharedState(int incrementsPerThread, int threadCount) throws InterruptedException {
        // TODO: Use an ExecutorService to run threadCount tasks.
        // Each task counts its own local counter (not shared) and returns the count.
        // Sum all returned counts at the end.
        // This demonstrates that avoiding shared state eliminates the need for synchronization.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Reset all counters.
     */
    public void reset() {
        synchronizedCount = 0;
        atomicCount.set(0);
    }
}
