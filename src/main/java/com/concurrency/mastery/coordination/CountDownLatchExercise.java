package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Exercise 31 — CountDownLatch
 *
 * Scenario:
 * You're starting a distributed system where several worker services must
 * initialize before the main service can start accepting requests.
 * A CountDownLatch acts as a "wait until N things happen" gate.
 *
 * Mental model: "Wait until N events happen."
 */
public class CountDownLatchExercise {

    /**
     * Start N workers and wait for all of them to finish.
     *
     * @param workerCount the number of workers
     * @param workerTask each worker's task
     * @throws InterruptedException if interrupted while waiting
     */
    public void waitForAllWorkers(int workerCount, Runnable workerTask) throws InterruptedException {
        // TODO: Create a CountDownLatch with count = workerCount.
        // Start workerCount threads, each running workerTask and calling latch.countDown().
        // Wait on the main thread for the latch to reach zero.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a "start gate" pattern: workers wait for a signal to begin,
     * then all start simultaneously.
     *
     * @param workerCount the number of workers
     * @return a list of results from workers (each worker adds its index)
     */
    public List<Integer> startGatePattern(int workerCount) throws InterruptedException {
        // TODO: Create a CountDownLatch(1) as the start gate.
        // Create workerCount threads that wait on the latch before doing work.
        // After all threads are started and waiting, count down the latch to release them all.
        // Each worker adds its index to a results list.
        // Return the results list.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Count down events and return when all are complete.
     * Simulates waiting for multiple background tasks.
     *
     * @param eventCount the number of events to wait for
     * @param eventDelayMs the delay per event in milliseconds
     * @return the time in milliseconds waited
     */
    public long waitForEvents(int eventCount, long eventDelayMs) throws InterruptedException {
        // TODO: Create a CountDownLatch(eventCount).
        // Start eventCount threads, each sleeping for eventDelayMs then calling countDown().
        // Wait on the main thread and return how long it took.
        throw new UnsupportedOperationException("Implement this method");
    }
}
