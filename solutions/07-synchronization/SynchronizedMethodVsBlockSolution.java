package com.concurrency.mastery.synchronization;

/**
 * SOLUTION — Exercise 19: synchronized Method vs Block
 *
 * Key Concepts:
 * - synchronized(void foo()) locks on 'this'
 * - synchronized(lock) { ... } locks on a specific object
 * - Block-level gives more control over lock scope
 */
public class SynchronizedMethodVsBlockSolution {

    private int value = 0;
    private final Object lock = new Object();
    private String name = "default";

    public synchronized void incrementMethodLevel() {
        value++;
    }

    public synchronized int getValueMethodLevel() {
        return value;
    }

    public void incrementBlockLevel() {
        synchronized (lock) {
            value++;
        }
    }

    public int getValueBlockLevel() {
        synchronized (lock) {
            return value;
        }
    }

    public void updateNameAndValue(String newName, int increment) {
        synchronized (lock) {
            this.name = newName;
            this.value += increment;
        }
    }

    public String getName() { return name; }
    public int getValue() { return value; }
    public void reset() { value = 0; name = "default"; }
}
