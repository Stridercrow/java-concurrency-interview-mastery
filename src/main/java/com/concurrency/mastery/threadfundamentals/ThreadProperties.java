package com.concurrency.mastery.threadfundamentals;

/**
 * Exercise 02 — Thread Properties
 *
 * Scenario:
 * You're building a monitoring system where you need to inspect and configure
 * thread properties before submitting work.
 */
public class ThreadProperties {

    /**
     * Get the name of the given thread.
     */
    public String getName(Thread thread) {
        // TODO: Return the thread's name
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the ID of the given thread.
     */
    public long getId(Thread thread) {
        // TODO: Return the thread's ID
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the priority of the given thread.
     */
    public int getPriority(Thread thread) {
        // TODO: Return the thread's priority
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a daemon thread with the given task and name.
     * The thread must be set as daemon BEFORE starting.
     *
     * @param task the task to run
     * @param name the thread name
     * @return the daemon thread (not started)
     */
    public Thread createDaemonThread(Runnable task, String name) {
        // TODO: Create a Thread, set it as daemon, set the name, but do NOT start it.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the given thread is alive.
     */
    public boolean isAlive(Thread thread) {
        // TODO: Return whether the thread is alive
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the current state of the given thread.
     */
    public Thread.State getState(Thread thread) {
        // TODO: Return the thread's current state
        throw new UnsupportedOperationException("Implement this method");
    }
}
