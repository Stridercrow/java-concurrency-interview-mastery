package com.concurrency.mastery.threadfundamentals;

/**
 * SOLUTION — Exercise 04: Joining Threads
 *
 * Key Concepts:
 * - join() blocks until the thread terminates
 * - join(timeout) has a maximum wait time
 * - join() and sleep() are fundamentally different
 */
public class JoiningThreadsSolution {

    public void joinAll(Thread[] threads) throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }
    }

    public boolean joinWithTimeout(Thread[] threads, long timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        for (Thread t : threads) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) return false;
            t.join(remaining);
            if (t.isAlive()) return false;
        }
        return true;
    }

    public long joinAndMeasure(Thread thread) throws InterruptedException {
        long start = System.currentTimeMillis();
        thread.join();
        return System.currentTimeMillis() - start;
    }
}
