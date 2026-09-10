package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 32: Semaphore
 *
 * Key Concepts:
 * - Semaphore(N) limits concurrency to N permits
 * - acquire() blocks if no permits available
 * - release() returns a permit
 * - "Limit concurrency to N"
 */
public class SemaphoreExerciseSolution {

    private final Semaphore semaphore;
    private final AtomicInteger maxActiveCount = new AtomicInteger(0);
    private final AtomicInteger activeCount = new AtomicInteger(0);

    public SemaphoreExerciseSolution(int maxPermits) {
        this.semaphore = new Semaphore(maxPermits);
    }

    public void executeWithPermit(Runnable task) throws InterruptedException {
        semaphore.acquire();
        try {
            int current = activeCount.incrementAndGet();
            maxActiveCount.updateAndGet(prev -> Math.max(prev, current));
            task.run();
        } finally {
            activeCount.decrementAndGet();
            semaphore.release();
        }
    }

    public <T> List<T> runWithConcurrencyLimit(List<Callable<T>> tasks) throws InterruptedException {
        List<T> results = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(tasks.size());

        for (Callable<T> task : tasks) {
            semaphore.acquire();
            try {
                results.add(executor.submit(task).get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        }

        executor.shutdown();
        return results;
    }

    public int getMaxActiveCount() { return maxActiveCount.get(); }
    public int availablePermits() { return semaphore.availablePermits(); }
}
