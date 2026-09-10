package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 33: CyclicBarrier
 *
 * Key Concepts:
 * - CyclicBarrier(N) blocks until N threads call await()
 * - Can be reset and reused (cyclic)
 * - "Wait until all participants reach this point"
 */
public class CyclicBarrierExerciseSolution {

    public List<Boolean> runPhasedComputation(int workerCount, int phaseCount,
                                               java.util.function.IntConsumer workerTask)
            throws InterruptedException, BrokenBarrierException {
        CyclicBarrier barrier = new CyclicBarrier(workerCount);
        List<Boolean> results = new ArrayList<>();

        Thread[] workers = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            final int workerId = i;
            workers[i] = new Thread(() -> {
                try {
                    for (int phase = 0; phase < phaseCount; phase++) {
                        workerTask.accept(phase);
                        barrier.await(); // wait for all workers to finish this phase
                    }
                    synchronized (results) {
                        results.add(true);
                    }
                } catch (Exception e) {
                    synchronized (results) {
                        results.add(false);
                    }
                }
            });
            workers[i].start();
        }

        for (Thread w : workers) {
            try { w.join(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        return results;
    }

    public int runWithTracking(int workerCount, int phaseCount) throws Exception {
        AtomicInteger maxPhase = new AtomicInteger(0);
        CyclicBarrier barrier = new CyclicBarrier(workerCount);

        Thread[] workers = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Thread(() -> {
                try {
                    for (int phase = 0; phase < phaseCount; phase++) {
                        maxPhase.updateAndGet(prev -> Math.max(prev, phase));
                        barrier.await();
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
            workers[i].start();
        }

        for (Thread w : workers) w.join(5000);
        return maxPhase.get();
    }
}
