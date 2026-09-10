package com.concurrency.mastery.volatilememory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 22 — Visibility Problem")
class VisibilityProblemTest {

    private VisibilityProblem exercise;

    @BeforeEach
    void setUp() {
        exercise = new VisibilityProblem();
    }

    @Test
    @DisplayName("Worker should eventually see shutdown signal")
    void workerShouldEventuallyStop() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Thread worker = exercise.startWorker(counter);

        Thread.sleep(100); // let the worker run
        exercise.shutdown();

        // Wait for the worker to stop
        worker.join(2000);

        assertFalse(worker.isAlive(),
            "Worker thread should have stopped after shutdown()");

        // The counter should have been incremented (worker ran)
        assertTrue(counter.get() > 0, "Worker should have done some work");
    }

    @Test
    @DisplayName("Visibility problem: without volatile, worker may not see flag")
    void demonstratesVisibilityProblem() throws InterruptedException {
        // This test documents that without volatile, the JVM is free to
        // cache the running flag in a CPU register. On most systems with
        // modern JIT, the worker WILL see the change eventually, but the
        // JMM does not guarantee this.
        //
        // The purpose of this test is educational: it shows that the
        // VisibilityProblem class has a visibility bug that volatile would fix.

        for (int attempt = 0; attempt < 5; attempt++) {
            exercise.reset();
            AtomicInteger counter = new AtomicInteger(0);
            Thread worker = exercise.startWorker(counter);

            Thread.sleep(50);
            exercise.shutdown();
            worker.join(2000);

            assertTrue(counter.get() > 0,
                "Worker should run at least once before seeing shutdown");
            assertFalse(worker.isAlive(),
                "Worker should stop eventually (though timing is not guaranteed by JMM)");
        }
    }
}
