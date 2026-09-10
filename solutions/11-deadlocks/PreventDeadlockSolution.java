package com.concurrency.mastery.deadlocks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * SOLUTION — Exercise 28: Prevent the Deadlock
 *
 * Key Concepts:
 * - Lock ordering: always acquire locks in the same global order
 * - tryLock: non-blocking acquisition with timeout
 * - Reduce lock scope: hold locks for shorter durations
 *
 * Prevention strategies:
 * 1. Lock ordering — eliminates circular wait
 * 2. tryLock with timeout — breaks hold-and-wait
 * 3. Reduced scope — minimizes lock contention
 */
public class PreventDeadlockSolution {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();
    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    public void safeWithLockOrdering(Runnable operation) {
        // Always acquire A before B — consistent ordering
        synchronized (resourceA) {
            synchronized (resourceB) {
                operation.run();
            }
        }
    }

    public boolean safeWithTryLock(Runnable operation) throws InterruptedException {
        while (true) {
            if (lockA.tryLock()) {
                try {
                    if (lockB.tryLock()) {
                        try {
                            operation.run();
                            return true;
                        } finally {
                            lockB.unlock();
                        }
                    }
                } finally {
                    lockA.unlock();
                }
            }
            Thread.sleep(1); // back off briefly
        }
    }

    public void safeWithReducedScope(Runnable operationA, Runnable operationB) {
        // Never hold both locks simultaneously
        lockA.lock();
        try {
            operationA.run();
        } finally {
            lockA.unlock();
        }

        lockB.lock();
        try {
            operationB.run();
        } finally {
            lockB.unlock();
        }
    }
}
