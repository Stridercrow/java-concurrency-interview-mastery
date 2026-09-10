package com.concurrency.mastery.atomicoperations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 25 — AtomicInteger Counter")
class AtomicIntegerCounterTest {

    private AtomicIntegerCounter exercise;

    @BeforeEach
    void setUp() {
        exercise = new AtomicIntegerCounter();
    }

    @Test
    @DisplayName("Should get current value")
    void shouldGet() {
        assertEquals(0, exercise.get());
    }

    @Test
    @DisplayName("Should increment and get new value")
    void shouldIncrementAndGet() {
        assertEquals(1, exercise.incrementAndGet());
        assertEquals(2, exercise.incrementAndGet());
        assertEquals(3, exercise.incrementAndGet());
    }

    @Test
    @DisplayName("Should get old value then increment")
    void shouldGetAndIncrement() {
        assertEquals(0, exercise.getAndIncrement());
        assertEquals(1, exercise.getAndIncrement());
        assertEquals(2, exercise.get());
    }

    @Test
    @DisplayName("Should add delta and get new value")
    void shouldAddAndGet() {
        assertEquals(5, exercise.addAndGet(5));
        assertEquals(10, exercise.addAndGet(5));
        assertEquals(8, exercise.addAndGet(-2));
    }

    @Test
    @DisplayName("Should set value atomically")
    void shouldSet() {
        exercise.set(42);
        assertEquals(42, exercise.get());
        exercise.set(0);
        assertEquals(0, exercise.get());
    }

    @Test
    @DisplayName("CAS should succeed when expected matches")
    void casShouldSucceed() {
        exercise.set(10);
        assertTrue(exercise.compareAndSet(10, 20));
        assertEquals(20, exercise.get());
    }

    @Test
    @DisplayName("CAS should fail when expected doesn't match")
    void casShouldFail() {
        exercise.set(10);
        assertFalse(exercise.compareAndSet(5, 20));
        assertEquals(10, exercise.get()); // unchanged
    }

    @Test
    @DisplayName("CAS loop increment should be thread-safe")
    void casLoopIncrementShouldBeThreadSafe() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                exercise.casLoopIncrement(incrementsPerThread);
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, exercise.get(),
            "CAS loop should produce exact count");
    }
}
