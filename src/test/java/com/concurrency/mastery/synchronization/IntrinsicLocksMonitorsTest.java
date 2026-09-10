package com.concurrency.mastery.synchronization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 20 — Intrinsic Locks / Monitors")
class IntrinsicLocksMonitorsTest {

    private IntrinsicLocksMonitors buffer;

    @BeforeEach
    void setUp() {
        buffer = new IntrinsicLocksMonitors(5);
    }

    @Test
    @DisplayName("Should put and take items")
    void shouldPutAndTake() throws InterruptedException {
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);

        assertEquals(1, buffer.take());
        assertEquals(2, buffer.take());
        assertEquals(3, buffer.take());
        assertTrue(buffer.isEmpty());
    }

    @Test
    @DisplayName("Should block when full and unblock when consumer takes")
    void shouldBlockWhenFull() throws InterruptedException {
        // Fill the buffer
        for (int i = 0; i < 5; i++) {
            buffer.put(i);
        }
        assertTrue(buffer.isFull());

        CountDownLatch producerStarted = new CountDownLatch(1);
        CountDownLatch producerFinished = new CountDownLatch(1);

        // Producer thread should block because buffer is full
        Thread producer = new Thread(() -> {
            try {
                producerStarted.countDown();
                buffer.put(99); // should block
                producerFinished.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        producerStarted.await();

        Thread.sleep(100);
        assertFalse(producerFinished.await(100, TimeUnit.MILLISECONDS),
            "Producer should be blocked (buffer is full)");

        // Consumer takes an item, unblocking the producer
        assertEquals(0, buffer.take());
        assertTrue(producerFinished.await(2, TimeUnit.SECONDS),
            "Producer should complete after consumer makes room");
        assertEquals(99, buffer.take()); // the blocked put should have succeeded
    }

    @Test
    @DisplayName("Producer-consumer should transfer all items correctly")
    void producerConsumerIntegration() throws InterruptedException {
        int itemCount = 50;
        List<Integer> consumed = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);

        // Producer
        executor.submit(() -> {
            try {
                startLatch.await();
                for (int i = 0; i < itemCount; i++) {
                    buffer.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer
        executor.submit(() -> {
            try {
                startLatch.await();
                for (int i = 0; i < itemCount; i++) {
                    consumed.add(buffer.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        startLatch.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));

        assertEquals(itemCount, consumed.size());
        // All items should be present (order may vary due to synchronization)
        for (int i = 0; i < itemCount; i++) {
            assertTrue(consumed.contains(i), "Missing item: " + i);
        }
    }
}
