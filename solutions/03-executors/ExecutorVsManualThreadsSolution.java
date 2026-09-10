package com.concurrency.mastery.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 08: Executor vs Manual Threads
 *
 * Key Concepts:
 * - "Thread per task" is wasteful: OS thread creation is expensive (~1MB stack)
 * - ExecutorService reuses a fixed number of threads for many tasks
 * - Thread pools reduce context switching and memory overhead
 * - For 100 tasks with a pool of 10: only 10 threads created vs 100
 *
 * Comparison:
 * ┌──────────────────────┬───────────────────┬───────────────────────────┐
 * │ Metric               │ Manual Threads    │ ExecutorService           │
 * ├──────────────────────┼───────────────────┼───────────────────────────┤
 * │ Threads created      │ 1 per task        │ Fixed pool size           │
 * │ Memory overhead      │ High (~1MB each)  │ Low (reuses threads)      │
 * │ Context switching    │ High              │ Lower                     │
 * │ Task queuing         │ No (manual)       │ Built-in                  │
 * │ Shutdown management  │ Manual join()     │ shutdown() + awaitTerm()  │
 * └──────────────────────┴───────────────────┴───────────────────────────┘
 */
public class ExecutorVsManualThreadsSolution {

    /**
     * Process tasks by creating a new Thread for each one.
     * This is the naive "thread per task" approach.
     *
     * For N tasks, this creates N OS threads — expensive!
     */
    public int processManually(List<Runnable> tasks) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (Runnable task : tasks) {
            Thread t = new Thread(task);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        return tasks.size();
    }

    /**
     * Process tasks using a fixed thread pool.
     * Only poolSize threads are created. Tasks queue when all threads are busy.
     *
     * shutdown() + awaitTermination() ensures all tasks complete before returning.
     */
    public int processWithExecutor(List<Runnable> tasks, int poolSize) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (Runnable task : tasks) {
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(30, java.util.concurrent.TimeUnit.SECONDS);
        return tasks.size();
    }

    /**
     * Track the maximum number of concurrently active threads.
     *
     * Uses AtomicInteger for thread-safe counting and a CountDownLatch
     * to coordinate start timing. Each thread increments the counter when
     * it starts and decrements when it finishes.
     */
    public int countConcurrentThreads(List<Runnable> tasks, int poolSize) throws InterruptedException {
        AtomicInteger activeThreads = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch startLatch = new CountDownLatch(1);

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (Runnable task : tasks) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for all tasks to be submitted
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                int active = activeThreads.incrementAndGet();
                maxConcurrent.accumulateAndGet(active, Math::max);
                try {
                    task.run();
                } finally {
                    activeThreads.decrementAndGet();
                }
            });
        }

        startLatch.countDown(); // Release all tasks at once
        executor.shutdown();
        executor.awaitTermination(30, java.util.concurrent.TimeUnit.SECONDS);
        return maxConcurrent.get();
    }
}
