package com.concurrency.mastery.deadlocks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Exercise 28 — Prevent the Deadlock
 *
 * Scenario:
 * The CreateDeadlock exercise showed how deadlock occurs.
 * Now prevent it using various strategies:
 * 1. Lock ordering — always acquire locks in the same order
 * 2. tryLock — use non-blocking lock acquisition with timeout
 * 3. Reduce lock scope — minimize nested locking
 */
public class PreventDeadlock {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();
    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    /**
     * Strategy 1: Lock Ordering
     * Always acquire resourceA before resourceB, regardless of which
     * thread is running. This prevents circular wait.
     *
     * @param operation the operation to perform while holding both locks
     */
    public void safeWithLockOrdering(Runnable operation) {
        // TODO: Acquire resourceA, then resourceB, run the operation, release both.
        // Both threads must acquire in the same order: A then B.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Strategy 2: tryLock with timeout
     * Try to acquire both locks. If can't get the second lock, release the first and retry.
     *
     * @param operation the operation to perform while holding both locks
     * @return true if the operation was performed, false if it couldn't acquire both locks
     */
    public boolean safeWithTryLock(Runnable operation) throws InterruptedException {
        // TODO: Use tryLock() on both ReentrantLocks.
        // If both acquired, run operation and release both.
        // If second lock fails, release the first and retry.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Strategy 3: Reduce lock scope
     * Instead of holding both locks simultaneously, perform each operation
     * with its own lock, and coordinate at a higher level.
     *
     * @param operationA work protected by lockA
     * @param operationB work protected by lockB
     */
    public void safeWithReducedScope(Runnable operationA, Runnable operationB) {
        // TODO: Execute operationA under lockA, then operationB under lockB.
        // Never hold both locks simultaneously.
        throw new UnsupportedOperationException("Implement this method");
    }
}
