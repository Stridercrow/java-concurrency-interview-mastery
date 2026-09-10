package com.concurrency.mastery.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SOLUTION — Exercise 26: ReentrantLock
 *
 * Key Concepts:
 * - lock()/unlock() in try-finally
 * - tryLock() for non-blocking acquisition
 * - tryLock(timeout) for timed acquisition
 * - isLocked(), isHeldByCurrentThread(), getQueueLength()
 *
 * When to use ReentrantLock over synchronized:
 * - Need tryLock()
 * - Need timed locks
 * - Need interruptible locking
 * - Need multiple Condition variables
 */
public class ReentrantLockExerciseSolution {

    private int value = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }

    public boolean tryIncrement() {
        if (lock.tryLock()) {
            try {
                value++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }

    public boolean tryIncrementWithTimeout(long timeoutMs) throws InterruptedException {
        if (lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS)) {
            try {
                value++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }

    public boolean isLocked() { return lock.isLocked(); }
    public boolean isHeldByCurrentThread() { return lock.isHeldByCurrentThread(); }
    public int getQueueLength() { return lock.getQueueLength(); }
}
