package com.concurrency.mastery.sharedstate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Exercise 16: Shared Counter Solutions
 *
 * Three approaches compared:
 * 1. synchronized — mutual exclusion via lock
 * 2. AtomicInteger — lock-free CAS operations
 * 3. Thread-local — avoid shared state entirely
 *
 * Tradeoffs:
 * - synchronized: simple, but can cause contention
 * - AtomicInteger: faster for single variables, but CAS can spin under high contention
 * - Thread-local: best performance, but requires collecting results at the end
 */
public class SharedCounterSolutionsSolution {

    private int synchronizedCount = 0;
    private final java.util.concurrent.atomic.AtomicInteger atomicCount = new java.util.concurrent.atomic.AtomicInteger(0);

    public synchronized void incrementSynchronized() {
        synchronizedCount++;
    }

    public synchronized int getSynchronizedCount() {
        return synchronizedCount;
    }

    public void incrementAtomic() {
        atomicCount.incrementAndGet();
    }

    public int getAtomicCount() {
        return atomicCount.get();
    }

    public int incrementWithoutSharedState(int incrementsPerThread, int threadCount) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
        java.util.concurrent.atomic.AtomicInteger total = new java.util.concurrent.atomic.AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                int localCount = 0;
                for (int j = 0; j < incrementsPerThread; j++) {
                    localCount++;
                }
                total.addAndGet(localCount);
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }
        return total.get();
    }

    public void reset() {
        synchronizedCount = 0;
        atomicCount.set(0);
    }
}
