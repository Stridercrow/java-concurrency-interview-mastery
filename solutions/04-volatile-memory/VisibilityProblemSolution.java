package com.concurrency.mastery.volatilememory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 22: Visibility Problem
 *
 * Key Concepts:
 * - The Java Memory Model (JMM) allows threads to cache variables in CPU registers
 * - Without synchronization, a thread may NEVER see changes made by other threads
 * - This is called the "visibility problem"
 *
 * Why the worker might not see the shutdown:
 * 1. The JVM/JIT compiler can hoist the read of `running` out of the loop
 * 2. The CPU cache on the worker's core may never be invalidated
 * 3. There is no happens-before relationship between the write and the read
 *
 * The counter being AtomicInteger ensures its own visibility, but the
 * boolean `running` field has no such guarantee.
 *
 * Note: This class is intentionally BROKEN to demonstrate the problem.
 * The fix is shown in VolatileShutdownFlagSolution.
 */
public class VisibilityProblemSolution {

    private boolean running = true;

    /**
     * Start a worker that loops while running is true.
     *
     * The visibility problem: the worker thread may cache `running = true`
     * in a CPU register and never re-read it from main memory, even after
     * the main thread sets it to false.
     *
     * AtomicInteger is used for the counter to ensure that the counter's
     * own visibility is not the issue — the problem is purely the boolean flag.
     */
    public Thread startWorker(AtomicInteger counter) {
        Thread worker = new Thread(() -> {
            while (running) {
                counter.incrementAndGet();
            }
        });
        worker.start();
        return worker;
    }

    /**
     * Signal the worker to stop by setting running = false.
     *
     * Due to the visibility problem, the worker thread may never see
     * this change because:
     * - There is no volatile keyword on `running`
     * - There is no synchronization block
     * - There is no happens-before edge between this write and the worker's read
     */
    public void shutdown() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void reset() {
        running = true;
    }
}
