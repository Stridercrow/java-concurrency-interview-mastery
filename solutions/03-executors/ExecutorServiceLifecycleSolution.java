package com.concurrency.mastery.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SOLUTION — Exercise 10: ExecutorService Lifecycle
 *
 * Key Concepts:
 * - Graceful shutdown: shutdown() → awaitTermination() → shutdownNow()
 * - Interruption as cooperative cancellation
 * - Executor lifecycle states: RUNNING → SHUTDOWN → TERMINATED
 */
public class ExecutorServiceLifecycleSolution {

    public boolean gracefulShutdown(ExecutorService executor, long timeoutMs) throws InterruptedException {
        executor.shutdown();
        if (executor.awaitTermination(timeoutMs, TimeUnit.MILLISECONDS)) {
            return true;
        }
        executor.shutdownNow();
        return executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    public <T> java.util.concurrent.Future<T> submitCancellableTask(
            ExecutorService executor, java.util.concurrent.Callable<T> task) {
        return executor.submit(task);
    }

    public void submitInterruptibleTask(ExecutorService executor, AtomicBoolean running) {
        executor.submit(() -> {
            running.set(true);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                running.set(false);
            }
        });
    }
}
