package com.concurrency.mastery.virtualthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Exercise 42: Virtual Threads (Java 21+)
 *
 * Key Concepts:
 * - Virtual threads are lightweight threads managed by the JVM, not the OS
 * - Created via Thread.ofVirtual() or Executors.newVirtualThreadPerTaskExecutor()
 * - They "mount" on carrier (platform) threads during computation
 * - They "unmount" during blocking I/O, freeing the carrier thread
 * - This allows millions of concurrent threads for I/O-bound workloads
 *
 * Virtual vs Platform Threads:
 * ┌──────────────────┬───────────────────┬───────────────────────────┐
 * │ Feature          │ Platform          │ Virtual                   │
 * ├──────────────────┼───────────────────┼───────────────────────────┤
 * │ Creation cost    │ High (OS thread)  │ Low (JVM managed)         │
 * │ Max count        │ ~10,000           │ Millions                  │
 * │ Blocking behavior│ Ties up OS thread │ Frees carrier thread      │
 * │ Best for         │ CPU-bound         │ I/O-bound (HTTP, DB, etc) │
 * │ Thread.ofVirtual │ N/A               │ .start(task)              │
 * └──────────────────┴───────────────────┴───────────────────────────┘
 *
 * IMPORTANT: Virtual threads do NOT help with CPU-bound work.
 * They shine when threads spend most time blocking (I/O, sleep, etc.)
 */
public class VirtualThreadsExerciseSolution {

    /**
     * Create and start a virtual thread using Thread.ofVirtual().
     *
     * Thread.ofVirtual() returns a Thread.Builder for virtual threads.
     * .start(runnable) creates and starts the thread in one call.
     */
    public Thread createVirtualThread(Runnable task) {
        return Thread.ofVirtual().start(task);
    }

    /**
     * Create a named virtual thread.
     *
     * .name() sets the thread name, useful for debugging and logging.
     * Virtual thread names are prefixed with "VirtualThread/" by default.
     */
    public Thread createNamedVirtualThread(String name, Runnable task) {
        return Thread.ofVirtual().name(name).start(task);
    }

    /**
     * Run tasks using virtual threads via ExecutorService.
     *
     * newVirtualThreadPerTaskExecutor() creates a new virtual thread for
     * each submitted task — no thread pool sizing needed. The JVM handles
     * multiplexing virtual threads onto a small number of carrier threads.
     */
    public int runWithVirtualThreads(int taskCount, Callable<Integer> task) throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(task));
            }
            int total = 0;
            for (Future<Integer> f : futures) {
                total += f.get();
            }
            return total;
        }
    }

    /**
     * Run tasks using platform (fixed) threads for comparison.
     *
     * The pool size limits concurrency. If poolSize < taskCount,
     * tasks queue up and wait — this is the key bottleneck for I/O work.
     */
    public int runWithPlatformThreads(int taskCount, int poolSize, Callable<Integer> task) throws Exception {
        try (ExecutorService executor = Executors.newFixedThreadPool(poolSize)) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(task));
            }
            int total = 0;
            for (Future<Integer> f : futures) {
                total += f.get();
            }
            return total;
        }
    }

    /**
     * Simulate a blocking I/O operation.
     * Virtual threads handle this efficiently; platform threads tie up resources.
     */
    public String simulateIO(long sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "result-" + Thread.currentThread().getName();
    }
}
