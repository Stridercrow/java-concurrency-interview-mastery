package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SOLUTION — Exercise 31: CountDownLatch
 *
 * Key Concepts:
 * - CountDownLatch(N) blocks until N countDown() calls
 * - One-shot: cannot be reset
 * - "Wait until N events happen"
 */
public class CountDownLatchExerciseSolution {

    public void waitForAllWorkers(int workerCount, Runnable workerTask) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(workerCount);
        for (int i = 0; i < workerCount; i++) {
            new Thread(() -> {
                try {
                    workerTask.run();
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
    }

    public List<Integer> startGatePattern(int workerCount) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        List<Integer> results = new CopyOnWriteArrayList<>();

        for (int i = 0; i < workerCount; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    startGate.await(); // wait for the gate
                    results.add(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        Thread.sleep(100); // let all threads start and wait
        startGate.countDown(); // release all at once
        Thread.sleep(100); // let them finish

        return new ArrayList<>(results);
    }

    public long waitForEvents(int eventCount, long eventDelayMs) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(eventCount);
        long start = System.currentTimeMillis();

        for (int i = 0; i < eventCount; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(eventDelayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        return System.currentTimeMillis() - start;
    }
}
