package com.concurrency.mastery.volatilememory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 24: Volatile Counter
 *
 * Key Concepts:
 * - volatile guarantees VISIBILITY but NOT ATOMICITY
 * - counter++ is a compound operation (read + add + write) — volatile doesn't help
 * - synchronized makes the entire compound operation atomic
 * - AtomicInteger uses CAS (Compare-And-Swap) for lock-free atomicity
 *
 * ┌──────────────────┬───────────┬────────────┬──────────────┐
 * │ Approach         │ Thread-   │ Overhead   │ Use When     │
 * │                  │ Safe      │            │              │
 * ├──────────────────┼───────────┼────────────┼──────────────┤
 * │ volatile int     │ NO        │ Lowest     │ Single write │
 * │                  │           │            │ multiple read│
 * ├──────────────────┼───────────┼────────────┼──────────────┤
 * │ synchronized     │ YES       │ Higher     │ Complex      │
 * │                  │           │ (blocking) │ critical     │
 * │                  │           │            │ sections     │
 * ├──────────────────┼───────────┼────────────┼──────────────┤
 * │ AtomicInteger    │ YES       │ Medium     │ Simple       │
 * │                  │           │ (CAS)      │ counters     │
 * └──────────────────┴───────────┴────────────┴──────────────┘
 *
 * Volatile is perfect for: flags,状态 indicators, double-checked locking
 * Volatile is NOT for: compound operations (++, --, check-then-act)
 */
public class VolatileCounterSolution {

    private volatile int volatileCount = 0;
    private int synchronizedCount = 0;
    private final AtomicInteger atomicCount = new AtomicInteger(0);
    private final Object lock = new Object();

    /**
     * Increment using volatile. NOT thread-safe for compound operations.
     *
     * volatile++ expands to:
     * 1. Read volatileCount from main memory → local cache
     * 2. Add 1 to local value
     * 3. Write local value back to main memory
     *
     * Two threads can both read the same value (e.g., 5), both compute 6,
     * and both write 6 — losing one increment.
     */
    public void incrementVolatile() {
        volatileCount++;
    }

    /**
     * Increment using synchronized. Thread-safe because the entire
     * read-modify-write is atomic under the lock.
     */
    public void incrementSynchronized() {
        synchronized (lock) {
            synchronizedCount++;
        }
    }

    /**
     * Increment using AtomicInteger. Thread-safe via CAS (Compare-And-Swap).
     *
     * CAS is a CPU instruction that atomically:
     * 1. Reads the current value
     * 2. Compares it to expected value
     * 3. If equal, writes the new value
     * 4. If not equal (another thread changed it), retries
     *
     * This is "optimistic locking" — no thread blocking, just retry.
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
