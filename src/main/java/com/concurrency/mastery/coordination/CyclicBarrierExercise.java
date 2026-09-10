package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Exercise 33 — CyclicBarrier
 *
 * Scenario:
 * You're simulating a phased computation where multiple workers process data
 * in phases. Each phase must complete before the next phase starts.
 * A CyclicBarrier synchronizes all participants at a phase boundary.
 *
 * Mental model: "Wait until all participants reach this point."
 */
public class CyclicBarrierExercise {

    /**
     * Run a phased computation with multiple workers.
     * Each worker performs phases, and all workers must complete a phase
     * before any can start the next.
     *
     * @param workerCount the number of workers
     * @param phaseCount the number of phases
     * @param workerTask what each worker does per phase (receives phase number)
     * @return a list of completion markers (one per worker)
     */
    public List<Boolean> runPhasedComputation(int workerCount, int phaseCount,
                                               java.util.function.IntConsumer workerTask)
            throws InterruptedException, BrokenBarrierException {
        // TODO: Create a CyclicBarrier(workerCount).
        // For each worker:
        //   For each phase:
        //     Run the workerTask for this phase
        //     Call barrier.await() to synchronize with other workers
        // Collect and return completion status for each worker.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run multiple phases and track which phase each worker has reached.
     *
     * @param workerCount the number of workers
     * @param phaseCount the number of phases
     * @return the maximum phase reached by any worker
     */
    public int runWithTracking(int workerCount, int phaseCount) throws Exception {
        // TODO: Run a phased computation where each worker tracks which phase it reached.
        // Return the maximum phase number reached by any worker.
        throw new UnsupportedOperationException("Implement this method");
    }
}
