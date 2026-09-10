package com.concurrency.mastery.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 21: Lock Contention
 *
 * Key Concepts:
 * - Lock contention occurs when multiple threads compete for the same lock
 * - More synchronization = more contention = worse performance
 * - AtomicInteger uses CAS (lock-free) instead of blocking
 * - Method-level vs block-level synchronized: same performance, different scope
 *
 * Performance comparison (typical):
 * ┌──────────────────────┬───────────┬────────────────────────────────────┐
 * │ Approach             │ Relative  │ Why                                │
 * │                      │ Speed     │                                    │
 * ├──────────────────────┼───────────┼────────────────────────────────────┤
 * │ Unsynchronized       │ 1x (fast)│ No lock overhead, but NOT safe     │
 * │ AtomicInteger (CAS)  │ ~2-3x    │ Lock-free, hardware-level atomic   │
 * │ synchronized (block) │ ~5-10x   │ JVM monitor enter/exit overhead    │
 * │ synchronized (method)│ ~5-10x   │ Same as block, just different lock │
 * └──────────────────────┴───────────┴────────────────────────────────────┘
 *
 * Key insight: synchronized is NOT slow — it's just slower than no lock.
 * For simple counters, AtomicInteger wins. For complex critical sections,
 * synchronized is often clearer and equally fast.
 */
public class LockContentionSolution {

    private int value = 0;
    private final AtomicInteger atomicValue = new AtomicInteger(0);
    private final Object lock = new Object();

    /**
     * Unsynchronized increment — fastest but NOT thread-safe.
     * Used as a baseline to measure lock overhead.
     */
    public void incrementUnsynchronized() {
        value++;
    }

    /**
     * Method-level synchronized — locks on 'this'.
     * Only one thread can execute any synchronized method on this instance.
     */
    public synchronized void incrementMethodLock() {
        value++;
    }

    /**
     * Block-level synchronized — locks on 'lock' object.
     * Only one thread can enter this block at a time.
     * Same performance as method-level, but scoped to the critical section.
     */
    public void incrementBlockLock() {
        synchronized (lock) {
            value++;
        }
    }

    /**
     * AtomicInteger increment — uses CAS (Compare-And-Swap).
     *
     * CAS is a single CPU instruction (CMPXCHG on x86) that:
     * 1. Reads current value
     * 2. Compares to expected value
     * 3. If equal, atomically writes new value
     * 4. If not equal (race lost), retries in a loop
     *
     * No thread ever blocks — the losing thread just retries immediately.
     * This is "optimistic concurrency" vs synchronized's "pessimistic concurrency."
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
