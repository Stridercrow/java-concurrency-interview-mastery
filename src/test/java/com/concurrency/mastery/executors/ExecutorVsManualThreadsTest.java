package com.concurrency.mastery.executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 08 — Executor vs Manual Threads")
class ExecutorVsManualThreadsTest {

    private ExecutorVsManualThreads exercise;

    @BeforeEach
    void setUp() {
        exercise = new ExecutorVsManualThreads();
    }

    @Test
    @DisplayName("Should process all tasks manually")
    void shouldProcessManually() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(counter::incrementAndGet);
        }

        int completed = exercise.processManually(tasks);
        assertEquals(10, completed);
        assertEquals(10, counter.get());
    }

    @Test
    @DisplayName("Should process all tasks with executor")
    void shouldProcessWithExecutor() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasks.add(counter::incrementAndGet);
        }

        int completed = exercise.processWithExecutor(tasks, 4);
        assertEquals(20, completed);
        assertEquals(20, counter.get());
    }

    @Test
    @DisplayName("Should limit concurrent threads to pool size")
    void shouldLimitConcurrentThreads() throws InterruptedException {
        AtomicInteger concurrentCount = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch allStarted = new CountDownLatch(10);

        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(() -> {
                try {
                    startLatch.await();
                    int current = concurrentCount.incrementAndGet();
                    maxConcurrent.updateAndGet(prev -> Math.max(prev, current));
                    allStarted.countDown();
                    Thread.sleep(50);
                    concurrentCount.decrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        int max = exercise.countConcurrentThreads(tasks, 3);
        assertTrue(max <= 3, "Should not exceed pool size (got " + max + ")");
    }
}
