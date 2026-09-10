package com.concurrency.mastery.threadpools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 12: Fixed Thread Pool
 *
 * Key Concepts:
 * - FixedThreadPool creates exactly N threads that never change
 * - Tasks are queued in a LinkedBlockingQueue when all threads are busy
 * - The pool reuses threads — no creation overhead after initial setup
 * - shutdown() + awaitTermination() ensures all tasks complete
 *
 * Thread Pool Types:
 * ┌──────────────────────────┬──────────────────────────────────────────┐
 * │ Pool Type                │ Behavior                                 │
 * ├──────────────────────────┼──────────────────────────────────────────┤
 * │ newFixedThreadPool(n)    │ n threads, tasks queue when all busy     │
 * │ newSingleThreadExecutor()│ 1 thread, sequential execution           │
 * │ newCachedThreadPool()    │ Creates threads on demand, reuses idle   │
 * │ newScheduledThreadPool() │ For delayed/periodic tasks               │
 * └──────────────────────────┴──────────────────────────────────────────┘
 *
 * Why fixed pool is usually best:
 * - Predictable resource usage
 * - Prevents thread explosion under load
 * - Good for CPU-bound: poolSize = numCPUs
 * - Good for I/O-bound: poolSize = numCPUs * 2 (or higher)
 */
public class FixedThreadPoolExerciseSolution {

    /**
     * Process tasks with a fixed thread pool and count completions.
     *
     * Key pattern:
     * 1. Create executor
     * 2. Submit tasks
     * 3. shutdown() — no new tasks accepted
     * 4. awaitTermination() — wait for all tasks to finish
     */
    public int processTasks(int taskCount, int poolSize) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> {
                counter.incrementAndGet();
                try { Thread.sleep(10); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        return counter.get();
    }

    /**
     * Process inputs in parallel and collect results via Future.get().
     *
     * Future.get() blocks until the result is available.
     * Results are collected in submission order (not completion order).
     */
    public List<String> processInParallel(List<String> inputs, int poolSize) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        try {
            List<Future<String>> futures = new ArrayList<>();
            for (String input : inputs) {
                futures.add(executor.submit(() -> input.toUpperCase()));
            }
            List<String> results = new ArrayList<>();
            for (Future<String> f : futures) {
                results.add(f.get());
            }
            return results;
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Demonstrate bounded concurrency with a fixed pool.
     *
     * Uses AtomicInteger to track concurrent task count.
     * Each thread increments on start, updates max, sleeps, then decrements.
     * The max concurrent count should never exceed poolSize.
     */
    public int demonstrateBoundedConcurrency(int taskCount, int poolSize) throws InterruptedException {
        AtomicInteger concurrentCount = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> {
                int current = concurrentCount.incrementAndGet();
                maxConcurrent.accumulateAndGet(current, Math::max);
                try { Thread.sleep(50); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                concurrentCount.decrementAndGet();
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        return maxConcurrent.get();
    }
}
