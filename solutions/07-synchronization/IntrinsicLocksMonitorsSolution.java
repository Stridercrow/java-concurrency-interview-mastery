package com.concurrency.mastery.synchronization;

import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION — Exercise 20: Intrinsic Locks / Monitors
 *
 * Key Concepts:
 * - wait() releases the lock and blocks until notified
 * - notify() wakes one waiting thread
 * - notifyAll() wakes all waiting threads
 * - Always use while(wait) loop to handle spurious wakeups
 */
public class IntrinsicLocksMonitorsSolution {

    private final List<Integer> buffer = new ArrayList<>();
    private final int capacity;
    private final Object lock = new Object();

    public IntrinsicLocksMonitorsSolution(int capacity) {
        this.capacity = capacity;
    }

    public void put(int item) throws InterruptedException {
        synchronized (lock) {
            while (buffer.size() >= capacity) {
                lock.wait();
            }
            buffer.add(item);
            lock.notifyAll();
        }
    }

    public int take() throws InterruptedException {
        synchronized (lock) {
            while (buffer.isEmpty()) {
                lock.wait();
            }
            int item = buffer.remove(0);
            lock.notifyAll();
            return item;
        }
    }

    public int size() { synchronized (lock) { return buffer.size(); } }
    public boolean isFull() { synchronized (lock) { return buffer.size() >= capacity; } }
    public boolean isEmpty() { synchronized (lock) { return buffer.isEmpty(); } }
}
