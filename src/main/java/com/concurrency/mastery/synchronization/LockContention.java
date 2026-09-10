package com.concurrency.mastery.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 21 — Lock Contention
 *
 * Scenario:
 * You're measuring the impact of lock contention on performance.
 * Compare synchronized with non-synchronized approaches and observe
 * that more synchronization does not always mean better performance.
 */
public class LockContention {

    private int value = 0;
    private final AtomicInteger atomicValue = new AtomicInteger(0);
    private final Object lock = new Object();

    /**
     * Non-synchronized increment (for comparison — not thread-safe).
     * This is the fastest but NOT safe for concurrent use.
     */
    public void incrementUnsynchronized() {
        value++;
    }

    /**
     * Synchronized increment using method-level lock.
     * This locks on 'this'.
     */
    public synchronized void incrementMethodLock() {
        value++;
    }

    /**
     * Synchronized increment using block-level lock.
     * This locks on 'lock' object.
     */
    public void incrementBlockLock() {
        synchronized (lock) {
            value++;
        }
    }

    /**
     * AtomicInteger increment.
     * Uses CAS instead of locking.
     */
    public void incrementAtomic() {
        atomicValue.incrementAndGet();
    }

    public int getValue() { return value; }
    public int getAtomicValue() { return atomicValue.get(); }

    public void reset() {
        value = 0;
        atomicValue.set(0);
    }
}
