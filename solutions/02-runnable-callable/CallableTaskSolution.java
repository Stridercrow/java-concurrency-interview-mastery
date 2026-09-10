package com.concurrency.mastery.runnablecallable;

import java.util.concurrent.Callable;

/**
 * SOLUTION — Exercise 06: Callable Task
 *
 * Key Concepts:
 * - Callable<V> returns type V from call()
 * - Can throw checked exceptions
 * - Submit via ExecutorService.submit() to get Future<V>
 */
public class CallableTaskSolution {

    public Callable<Integer> createLengthCalculator(String text) {
        return () -> text.length();
    }

    public Callable<Integer> createParser(String numberString) {
        return () -> Integer.parseInt(numberString);
    }

    public Callable<Long> createSumCalculator(int n) {
        return () -> (long) n * (n - 1) / 2;
    }

    public Callable<String> createSlowTask(long sleepMs, String message) {
        return () -> {
            Thread.sleep(sleepMs);
            return message;
        };
    }
}
