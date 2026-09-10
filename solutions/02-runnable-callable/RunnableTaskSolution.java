package com.concurrency.mastery.runnablecallable;

import java.util.List;

/**
 * SOLUTION — Exercise 05: Runnable Task
 *
 * Key Concepts:
 * - Runnable.run() returns void
 * - No checked exceptions from Runnable
 * - Use shared collections to collect results
 */
public class RunnableTaskSolution {

    public Runnable createLogProcessor(String line, List<String> results) {
        return () -> results.add(line.toUpperCase());
    }

    public Runnable createGreeter(List<String> output) {
        return () -> output.add("Hello from " + Thread.currentThread().getName());
    }

    public Runnable createCounter(int n, List<Integer> results) {
        return () -> {
            for (int i = 0; i < n; i++) {
                results.add(i);
                try { Thread.sleep(1); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };
    }
}
