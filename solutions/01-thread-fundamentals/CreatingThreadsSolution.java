package com.concurrency.mastery.threadfundamentals;

import java.util.List;

/**
 * SOLUTION — Exercise 01: Creating Threads
 *
 * Key Concepts:
 * - Thread creation and lifecycle
 * - start() vs run()
 * - Thread naming
 * - join() for waiting
 *
 * Why the naive approach fails:
 * - Calling run() instead of start() executes on the calling thread
 * - Not joining means the main thread may exit before workers finish
 */
public class CreatingThreadsSolution {

    public Thread createAndStartThread(Runnable task) {
        Thread t = new Thread(task);
        t.start();
        return t;
    }

    public Thread createNamedThread(Runnable task, String name) {
        Thread t = new Thread(task, name);
        t.start();
        return t;
    }

    public List<String> runAndWait(Runnable task, List<String> results) {
        Thread t = new Thread(task);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return results;
    }

    public String demonstrateStartVsRun(Runnable task, boolean useStart) {
        if (useStart) {
            Thread t = new Thread(task);
            t.start();
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return t.getName();
        } else {
            task.run(); // runs on the calling thread
            return Thread.currentThread().getName();
        }
    }
}
