package com.concurrency.mastery.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Exercise 26 — ReentrantLock
 *
 * Scenario:
 * You need more control over locking than synchronized provides.
 * ReentrantLock offers tryLock(), timed locks, and multiple conditions.
 *
 * Compare with synchronized and understand when ReentrantLock is the better choice.
 */
public class ReentrantLockExercise {

    private int value = 0;
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Increment using ReentrantLock.
     * TODO: Acquire the lock, increment, release in finally.
     */
    public void increment() {
        // TODO: lock.lock() in try block, increment value, unlock in finally
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the current value.
     */
    public int getValue() {
        // TODO: Acquire lock, read value, release in finally
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Try to increment without blocking. Returns immediately.
     *
     * @return true if the lock was acquired and increment succeeded
     */
    public boolean tryIncrement() {
        // TODO: Use tryLock() to attempt to acquire the lock.
        // If acquired, increment and return true. Otherwise return false.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Try to increment with a timeout.
     *
     * @param timeoutMs the maximum time to wait for the lock
     * @return true if the lock was acquired and increment succeeded
     */
    public boolean tryIncrementWithTimeout(long timeoutMs) throws InterruptedException {
        // TODO: Use tryLock(timeout, unit) to attempt to acquire the lock.
        // If acquired within timeout, increment and return true. Otherwise return false.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the lock is currently held by any thread.
     */
    public boolean isLocked() {
        // TODO: Return whether the lock is held
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the lock is held by the current thread.
     */
    public boolean isHeldByCurrentThread() {
        // TODO: Return whether the current thread holds the lock
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the number of threads waiting to acquire the lock.
     */
    public int getQueueLength() {
        // TODO: Return the number of waiting threads
        throw new UnsupportedOperationException("Implement this method");
    }
}
