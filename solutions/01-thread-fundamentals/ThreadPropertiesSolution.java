package com.concurrency.mastery.threadfundamentals;

/**
 * SOLUTION — Exercise 02: Thread Properties
 */
public class ThreadPropertiesSolution {

    public String getName(Thread thread) {
        return thread.getName();
    }

    public long getId(Thread thread) {
        return thread.getId();
    }

    public int getPriority(Thread thread) {
        return thread.getPriority();
    }

    public Thread createDaemonThread(Runnable task, String name) {
        Thread t = new Thread(task, name);
        t.setDaemon(true);
        return t;
    }

    public boolean isAlive(Thread thread) {
        return thread.isAlive();
    }

    public Thread.State getState(Thread thread) {
        return thread.getState();
    }
}
