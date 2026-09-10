package com.concurrency.mastery.synchronization;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 20 — Intrinsic Locks / Monitors
 *
 * Scenario:
 * You're building a thread-safe bounded buffer. This exercise teaches
 * how intrinsic locks (monitors) work and how wait/notify coordinates threads.
 */
public class IntrinsicLocksMonitors {

    private final List<Integer> buffer = new ArrayList<>();
    private final int capacity;
    private final Object lock = new Object();

    public IntrinsicLocksMonitors(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Add an item to the buffer. Blocks if the buffer is full.
     * Uses wait/notify for coordination.
     *
     * TODO: Implement using synchronized and wait/notify.
     *
     * @param item the item to add
     */
    public void put(int item) throws InterruptedException {
        synchronized (lock) {
            // TODO: Wait while the buffer is full.
            // TODO: Add the item to the buffer.
            // TODO: Notify waiting consumers.
            throw new UnsupportedOperationException("Implement this method");
        }
    }

    /**
     * Remove and return an item from the buffer. Blocks if the buffer is empty.
     * Uses wait/notify for coordination.
     *
     * TODO: Implement using synchronized and wait/notify.
     *
     * @return the removed item
     */
    public int take() throws InterruptedException {
        synchronized (lock) {
            // TODO: Wait while the buffer is empty.
            // TODO: Remove and return the first item.
            // TODO: Notify waiting producers.
            throw new UnsupportedOperationException("Implement this method");
        }
    }

    /**
     * Get the current size of the buffer.
     */
    public int size() {
        synchronized (lock) {
            return buffer.size();
        }
    }

    /**
     * Check if the buffer is full.
     */
    public boolean isFull() {
        synchronized (lock) {
            return buffer.size() >= capacity;
        }
    }

    /**
     * Check if the buffer is empty.
     */
    public boolean isEmpty() {
        synchronized (lock) {
            return buffer.isEmpty();
        }
    }
}
