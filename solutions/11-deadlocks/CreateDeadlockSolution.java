package com.concurrency.mastery.deadlocks;

/**
 * SOLUTION — Exercise 27: Create a Deadlock
 *
 * Key Concepts:
 * - Deadlock: two or more threads permanently blocked, each waiting for the other
 * - The four Coffman conditions (ALL must be present):
 *   1. Mutual exclusion — resources can't be shared
 *   2. Hold and wait — thread holds one resource while waiting for another
 *   3. No preemption — resources can't be forcibly taken
 *   4. Circular wait — A waits for B, B waits for A
 *
 * How the deadlock happens:
 *
 *   Time    Thread-A                    Thread-B
 *   ────    ────────                    ────────
 *   t0      synchronized(resourceA)     ← acquires A
 *   t1      sleep(50ms)                 synchronized(resourceB) ← acquires B
 *   t2      tries resourceB → BLOCKS    sleep(50ms)
 *   t3      waiting for B               tries resourceA → BLOCKS
 *   t4      ⟳ circular wait ⟳
 *
 * Thread-A holds A, wants B
 * Thread-B holds B, wants A
 * Neither can proceed → deadlock
 *
 * This class is complete as-is. The deadlock is intentional.
 * The test uses timeouts to avoid hanging the test suite.
 */
public class CreateDeadlockSolution {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();
    private volatile boolean deadlockDetected = false;

    /**
     * Thread A: acquires resourceA first, then tries resourceB.
     * The sleep gives Thread B time to acquire resourceB, setting up the deadlock.
     */
    public void threadAWork() {
        synchronized (resourceA) {
            sleep(50); // ← This delay ensures Thread B acquires resourceB
            synchronized (resourceB) {
                deadlockDetected = false; // Never reached in deadlock
            }
        }
    }

    /**
     * Thread B: acquires resourceB first, then tries resourceA.
     * Opposite lock order from Thread A → circular wait → deadlock.
     */
    public void threadBWork() {
        synchronized (resourceB) {
            sleep(50); // ← This delay ensures Thread A acquires resourceA
            synchronized (resourceA) {
                deadlockDetected = false; // Never reached in deadlock
            }
        }
    }

    /**
     * Demonstrate the deadlock. Starts both threads and waits.
     * If both threads are still alive after the timeout, deadlock is confirmed.
     */
    public boolean demonstrateDeadlock(long timeoutMs) throws InterruptedException {
        Thread a = new Thread(this::threadAWork, "Thread-A");
        Thread b = new Thread(this::threadBWork, "Thread-B");

        a.start();
        b.start();

        a.join(timeoutMs);
        b.join(100);

        boolean deadlocked = a.isAlive() && b.isAlive();

        if (deadlocked) {
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
