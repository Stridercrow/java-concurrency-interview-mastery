package com.concurrency.mastery.interviewchallenges;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SOLUTION — Challenge 1: Background Worker Shutdown
 *
 * Key Concepts:
 * - Cooperative cancellation via interruption
 * - Thread lifecycle management
 * - volatile for cross-thread visibility
 *
 * Design:
 * - Use AtomicBoolean for running state
 * - Use volatile Thread reference for interrupt
 * - Always re-interrupt after catching InterruptedException
 */
public class BackgroundWorkerShutdownSolution {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile Thread workerThread;

    public void start(Runnable task) {
        workerThread = new Thread(() -> {
            running.set(true);
            try {
                while (running.get() && !Thread.currentThread().isInterrupted()) {
                    task.run();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                running.set(false);
            }
        }, "background-worker");
        workerThread.start();
    }

    public void shutdown() {
        running.set(false);
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean awaitShutdown(long timeoutMs) throws InterruptedException {
        if (workerThread != null) {
            workerThread.join(timeoutMs);
            return !workerThread.isAlive();
        }
        return true;
    }
}
