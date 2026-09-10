package com.concurrency.mastery.volatilememory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 23: volatile Shutdown Flag
 *
 * Key Concepts:
 * - Adding volatile ensures every read comes from main memory
 * - volatile creates a happens-before relationship:
 *   Write to volatile variable → happens-before → Read of that volatile variable
 * - This forces the worker thread to re-read `running` from main memory each iteration
 *
 * The fix is simple: just add volatile to the field declaration.
 *
 * volatile use cases:
 * - Status flags (shutdown, stop, done)
 * - Double-checked locking (singleton pattern)
 * - Immutable objects published safely
 *
 * volatile does NOT work for:
 * - Compound operations (count++)
 * - Check-then-act patterns (if !map.containsKey → map.put)
 */
public class VolatileShutdownFlagSolution {

    // FIX: volatile guarantees visibility across threads
    // Every read of `running` goes to main memory, not CPU cache
    private volatile boolean running = true;

    /**
     * Start a worker that loops while running is true.
     *
     * With volatile, the JVM is forbidden from hoisting the read of
     * `running` out of the loop. The worker will always see the latest
     * value written by the main thread.
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
     * Signal the worker to stop.
     *
     * The volatile write to `running` is immediately visible to all threads
     * because volatile writes are flushed to main memory.
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
