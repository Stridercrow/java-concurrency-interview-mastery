package com.concurrency.mastery.sharedstate;

/**
 * SOLUTION — Exercise 15: Shared Counter (Broken)
 *
 * Key Concepts:
 * - This class is INTENTIONALLY broken to demonstrate race conditions
 * - count++ is NOT atomic — it's three separate operations
 * - When multiple threads execute count++ concurrently, increments are lost
 * - The final count is always LESS than expected
 *
 * The Race Condition Explained:
 *
 * Thread A reads count = 5
 * Thread B reads count = 5  (same value!)
 * Thread A writes count = 6 (5 + 1)
 * Thread B writes count = 6 (5 + 1)  ← Thread B's increment is LOST
 *
 * Expected: 7 (two increments from 5)
 * Actual:   6 (one increment lost)
 *
 * The lost increments compound:
 * - 10 threads × 10,000 increments = expected 100,000
 * - Actual result is typically 80,000-95,000 (varies each run)
 *
 * Solutions (see Exercise 16):
 * 1. synchronized — blocks other threads during increment
 * 2. AtomicInteger — uses CAS for lock-free atomicity
 * 3. Avoid shared state — each thread counts locally, sum at the end
 *
 * This class is complete as-is. Its purpose is to BE the broken example.
 * Do NOT fix it — run SharedCounterBrokenTest to observe the race condition.
 */
public class SharedCounterBrokenSolution {

    private int count = 0;

    /**
     * Increment the counter. NOT thread-safe.
     *
     * The JVM executes count++ as three bytecode instructions:
     * 1. iconst_1  (load constant 1)
     * 2. iadd      (add to current value)
     * 3. istore    (store back to field)
     *
     * Between any of these instructions, another thread can interleave.
     */
    public void increment() {
        count++; // NOT atomic
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }

    /**
     * Increment n times — the race condition becomes more likely with more iterations.
     */
    public void incrementMultipleTimes(int n) {
        for (int i = 0; i < n; i++) {
            increment();
        }
    }
}
