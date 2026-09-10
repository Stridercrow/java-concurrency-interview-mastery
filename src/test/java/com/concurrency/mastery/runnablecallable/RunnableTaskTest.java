package com.concurrency.mastery.runnablecallable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 05 — Runnable Task")
class RunnableTaskTest {

    private RunnableTask exercise;

    @BeforeEach
    void setUp() {
        exercise = new RunnableTask();
    }

    @Test
    @DisplayName("Should create a Runnable that processes log lines")
    void shouldProcessLogLines() {
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        Runnable processor = exercise.createLogProcessor("hello world", results);

        assertNotNull(processor);
        processor.run();

        assertEquals(1, results.size());
        assertEquals("HELLO WORLD", results.get(0));
    }

    @Test
    @DisplayName("Should create a greeter Runnable")
    void shouldCreateGreeter() throws InterruptedException {
        List<String> output = Collections.synchronizedList(new ArrayList<>());
        Runnable greeter = exercise.createGreeter(output);
        CountDownLatch latch = new CountDownLatch(1);

        Thread t = new Thread(() -> {
            greeter.run();
            latch.countDown();
        });
        t.setName("greet-bot");
        t.start();

        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains("greet-bot"),
            "Output should contain the thread name");
    }

    @Test
    @DisplayName("Should create a counter Runnable")
    void shouldCreateCounter() throws InterruptedException {
        List<Integer> results = Collections.synchronizedList(new ArrayList<>());
        Runnable counter = exercise.createCounter(5, results);

        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            counter.run();
            latch.countDown();
        });
        t.start();

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(5, results.size());
        for (int i = 0; i < 5; i++) {
            assertTrue(results.contains(i), "Should contain " + i);
        }
    }

    @Test
    @DisplayName("Runnable has no return value — verify void execution")
    void runnableHasNoReturnValue() {
        // Runnable.run() returns void — there's no way to get a result from it directly
        Runnable task = () -> {};
        // This is why we need Callable for tasks that produce results
        assertDoesNotThrow(() -> task.run());
    }
}
