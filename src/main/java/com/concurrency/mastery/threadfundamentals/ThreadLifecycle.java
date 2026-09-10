package com.concurrency.mastery.threadfundamentals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Exercise 03 — Thread Lifecycle
 *
 * Scenario:
 * You're debugging a concurrent application and need to understand thread states.
 * This exercise asks you to observe and demonstrate thread lifecycle transitions.
 */
public class ThreadLifecycle {

    /**
     * Return the state of a newly created thread (before start()).
     * The thread should be in the NEW state.
     */
    public Thread.State getNewThreadState() {
        // TODO: Create a new Thread (not started) and return its state.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Return the state of a thread that is sleeping (via Thread.sleep).
     * The thread should be in the TIMED_WAITING state.
     *
     * @param sleepMs how long to sleep in milliseconds
     * @return the state observed while the thread is sleeping
     */
    public Thread.State getSleepingThreadState(long sleepMs) throws InterruptedException {
        // TODO: Create a thread that sleeps for sleepMs.
        // Return the state observed while it is sleeping.
        // Hint: The calling thread needs to wait briefly for the new thread to start sleeping.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Return the state of a thread that is waiting (via Object.wait).
     * The thread should be in the WAITING state.
     *
     * @param lock the object to wait on
     * @return the state observed while the thread is waiting
     */
    public Thread.State getWaitingThreadState(Object lock) throws InterruptedException {
        // TODO: Create a thread that calls lock.wait() and return its state.
        // The lock object will be provided by the test.
        // Hint: The calling thread needs to wait briefly for the new thread to start waiting.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Return the state of a terminated thread.
     * The thread should be in the TERMINATED state.
     */
    public Thread.State getTerminatedThreadState() throws InterruptedException {
        // TODO: Create a thread, start it, let it finish, and return its state.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Observe all lifecycle states in order and return them.
     * The returned list should contain: NEW, then a state indicating the thread ran,
     * then TERMINATED.
     *
     * @return an array of states observed: [NEW, running/terminated-after-run, TERMINATED]
     */
    public Thread.State[] observeLifecycle() throws InterruptedException {
        // TODO: Create a thread, observe its state at each lifecycle stage,
        // and return an array of the observed states.
        throw new UnsupportedOperationException("Implement this method");
    }
}
