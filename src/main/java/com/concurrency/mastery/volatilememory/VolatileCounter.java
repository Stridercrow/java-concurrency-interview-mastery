package com.concurrency.mastery.volatilememory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 24 — Volatile Counter
 *
 * Scenario:
 * Demonstrate why volatile int counter; counter++ is unsafe.
 * Compare volatile, synchronized, and AtomicInteger for counter operations.
 *
 * Key lesson: volatile guarantees visibility but NOT atomicity.
 */
public class VolatileCounter {

    private volatile int volatileCount = 0;
    private int synchronizedCount = 0;
    private final AtomicInteger atomicCount = new AtomicInteger(0);
    private final Object lock = new Object();

    /**
     * Increment using volatile. This is NOT thread-safe for compound operations
     * because volatile does not provide atomicity.
     */
    public void incrementVolatile() {
        volatileCount++; // NOT atomic: read + add + write
    }

    /**
     * Increment using synchronized. This IS thread-safe.
     */
    public void incrementSynchronized() {
        synchronized (lock) {
            synchronizedCount++;
        }
    }

    /**
     * Increment using AtomicInteger. This IS thread-safe.
     */
    public void incrementAtomic() {
        atomicCount.incrementAndGet();
    }

    public int getVolatileCount() { return volatileCount; }
    public int getSynchronizedCount() { return synchronizedCount; }
    public int getAtomicCount() { return atomicCount.get(); }

    public void reset() {
        volatileCount = 0;
        synchronizedCount = 0;
        atomicCount.set(0);
    }
}
