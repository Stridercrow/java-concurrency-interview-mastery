package com.concurrency.mastery.threadfundamentals;

/**
 * Exercise 04 — Joining Threads
 *
 * Scenario:
 * You're building a report generator that processes data in parallel and must
 * wait for all processing threads to finish before generating the final report.
 */
public class JoiningThreads {

    /**
     * Wait for all provided threads to complete.
     * If any thread throws an InterruptedException, propagate it.
     *
     * @param threads the threads to wait for
     * @throws InterruptedException if interrupted while waiting
     */
    public void joinAll(Thread[] threads) throws InterruptedException {
        // TODO: Call join() on each thread in the array.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Wait for all provided threads to complete, but no longer than the given timeout.
     *
     * @param threads the threads to wait for
     * @param timeoutMs maximum time to wait in milliseconds
     * @return true if all threads finished within the timeout, false otherwise
     * @throws InterruptedException if interrupted while waiting
     */
    public boolean joinWithTimeout(Thread[] threads, long timeoutMs) throws InterruptedException {
        // TODO: Call join(timeout) on each thread. If any thread is still alive after
        // the timeout expires, return false. Otherwise return true.
        // Hint: Be careful about distributing timeout across multiple threads.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Join a thread and return how long it took to complete.
     *
     * @param thread the thread to join
     * @return the time in milliseconds from now until the thread completes
     * @throws InterruptedException if interrupted while waiting
     */
    public long joinAndMeasure(Thread thread) throws InterruptedException {
        // TODO: Record the start time, join the thread, record the end time,
        // and return the elapsed time in milliseconds.
        throw new UnsupportedOperationException("Implement this method");
    }
}
