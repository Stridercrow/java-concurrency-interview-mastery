package com.concurrency.mastery.atomicoperations;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 25: AtomicInteger Counter
 *
 * Key Concepts:
 * - get(), incrementAndGet(), getAndIncrement()
 * - compareAndSet() — the CAS operation
 * - CAS loop pattern — foundation of lock-free algorithms
 *
 * Why CAS works:
 * - Hardware guarantees atomicity of compareAndSet
 * - If CAS fails, it means another thread changed the value
 * - Retry until successful
 */
public class AtomicIntegerCounterSolution {

    private final AtomicInteger counter = new AtomicInteger(0);

    public int get() { return counter.get(); }
    public int incrementAndGet() { return counter.incrementAndGet(); }
    public int getAndIncrement() { return counter.getAndIncrement(); }
    public int addAndGet(int delta) { return counter.addAndGet(delta); }
    public void set(int newValue) { counter.set(newValue); }
    public boolean compareAndSet(int expectedValue, int newValue) {
        return counter.compareAndSet(expectedValue, newValue);
    }

    public void casLoopIncrement(int n) {
        for (int i = 0; i < n; i++) {
            while (true) {
                int current = counter.get();
                if (counter.compareAndSet(current, current + 1)) {
                    break;
                }
                // CAS failed, retry
            }
        }
    }
}
