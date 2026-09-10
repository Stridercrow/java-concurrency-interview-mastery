package com.concurrency.mastery.threadfundamentals;

/**
 * SOLUTION — Exercise 03: Thread Lifecycle
 *
 * Thread States:
 * - NEW: Created but not started
 * - RUNNABLE: Started and ready to run (or running)
 * - BLOCKED: Waiting to acquire a monitor lock
 * - WAITING: Waiting indefinitely (wait(), join(), park())
 * - TIMED_WAITING: Waiting with a timeout (sleep(), wait(timeout))
 * - TERMINATED: Completed execution
 */
public class ThreadLifecycleSolution {

    public Thread.State getNewThreadState() {
        Thread t = new Thread(() -> {});
        return t.getState(); // NEW
    }

    public Thread.State getSleepingThreadState(long sleepMs) throws InterruptedException {
        Thread.State observed = null;
        Thread t = new Thread(() -> {
            try { Thread.sleep(sleepMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        t.start();
        Thread.sleep(10); // give it time to start sleeping
        observed = t.getState();
        t.join();
        return observed;
    }

    public Thread.State getWaitingThreadState(Object lock) throws InterruptedException {
        Object monitor = new Object();
        Thread.State observed = null;
        Thread t = new Thread(() -> {
            synchronized (monitor) {
                try { monitor.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        t.start();
        Thread.sleep(10); // give it time to start waiting
        observed = t.getState();
        synchronized (monitor) { monitor.notify(); }
        t.join();
        return observed;
    }

    public Thread.State getTerminatedThreadState() throws InterruptedException {
        Thread t = new Thread(() -> {});
        t.start();
        t.join();
        return t.getState(); // TERMINATED
    }

    public Thread.State[] observeLifecycle() throws InterruptedException {
        Thread t = new Thread(() -> {
            try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread.State newState = t.getState(); // NEW

        t.start();
        Thread.sleep(5);
        Thread.State runningState = t.getState(); // RUNNABLE

        t.join();
        Thread.State terminatedState = t.getState(); // TERMINATED

        return new Thread.State[]{newState, runningState, terminatedState};
    }
}
