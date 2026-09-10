package com.concurrency.mastery.deadlocks;

/**
 * Exercise 27 — Create a Deadlock
 *
 * Scenario:
 * Two threads acquire two resources in opposite order, causing a deadlock.
 *
 * IMPORTANT: This exercise intentionally creates a deadlock. The test uses
 * timeouts to avoid hanging the test suite. The deadlock code should only
 * be demonstrated manually or in isolated tests with timeouts.
 *
 * The four Coffman conditions must all be present:
 * 1. Mutual exclusion — resources cannot be shared
 * 2. Hold and wait — thread holds one resource while waiting for another
 * 3. No preemption — resources cannot be forcibly taken
 * 4. Circular wait — Thread A waits for Thread B which waits for Thread A
 */
public class CreateDeadlock {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();
    private volatile boolean deadlockDetected = false;

    /**
     * Thread A: acquires resourceA, then tries to acquire resourceB.
     */
    public void threadAWork() {
        synchronized (resourceA) {
            sleep(50); // Give thread B time to acquire resourceB
            synchronized (resourceB) {
                // This line is never reached in a deadlock
                deadlockDetected = false; // won't happen
            }
        }
    }

    /**
     * Thread B: acquires resourceB, then tries to acquire resourceA.
     * This creates a circular wait → deadlock.
     */
    public void threadBWork() {
        synchronized (resourceB) {
            sleep(50); // Give thread A time to acquire resourceA
            synchronized (resourceA) {
                // This line is never reached in a deadlock
                deadlockDetected = false; // won't happen
            }
        }
    }

    /**
     * Attempt to create a deadlock by running both threads.
     * Returns true if a deadlock was detected (both threads stuck).
     *
     * @param timeoutMs how long to wait before declaring deadlock
     * @return true if deadlock was detected
     */
    public boolean demonstrateDeadlock(long timeoutMs) throws InterruptedException {
        Thread a = new Thread(this::threadAWork, "Thread-A");
        Thread b = new Thread(this::threadBWork, "Thread-B");

        a.start();
        b.start();

        // Wait for both to finish (they won't if deadlocked)
        a.join(timeoutMs);
        b.join(100); // short follow-up

        boolean deadlocked = a.isAlive() && b.isAlive();

        if (deadlocked) {
            // Clean up — interrupt the deadlocked threads
            a.interrupt();
            b.interrupt();
            a.join(1000);
            b.join(1000);
        }

        return deadlocked;
    }

    public Object getResourceA() { return resourceA; }
    public Object getResourceB() { return resourceB; }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
