package com.concurrency.mastery.synchronization;

/**
 * Exercise 19 — synchronized Method vs Block
 *
 * Scenario:
 * You're building a thread-safe data store. Compare method-level synchronization
 * with block-level synchronization to understand lock scope and granularity.
 */
public class SynchronizedMethodVsBlock {

    private int value = 0;
    private final Object lock = new Object();
    private String name = "default";

    // === Method-level synchronization ===

    /**
     * Synchronized on 'this'. The entire method body is the critical section.
     */
    public synchronized void incrementMethodLevel() {
        value++;
    }

    /**
     * Synchronized on 'this'. Returns the current value.
     */
    public synchronized int getValueMethodLevel() {
        return value;
    }

    // === Block-level synchronization ===

    /**
     * Synchronized on the 'lock' object. Only the critical section is locked.
     * TODO: Implement using a synchronized block on the lock object.
     */
    public void incrementBlockLevel() {
        // TODO: Use synchronized(lock) { ... } to protect only the critical section
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Returns the current value using block-level synchronization.
     * TODO: Implement using a synchronized block.
     */
    public int getValueBlockLevel() {
        // TODO: Use synchronized(lock) { ... } to read the value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Update name and value together atomically.
     * TODO: Implement using a synchronized block to ensure both updates happen together.
     *
     * @param newName the new name
     * @param increment the value to add
     */
    public void updateNameAndValue(String newName, int increment) {
        // TODO: Use synchronized(lock) { ... } to atomically update both fields
        throw new UnsupportedOperationException("Implement this method");
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void reset() {
        value = 0;
        name = "default";
    }
}
