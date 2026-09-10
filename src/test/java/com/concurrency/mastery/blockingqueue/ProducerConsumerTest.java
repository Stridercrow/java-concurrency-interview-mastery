package com.concurrency.mastery.blockingqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 30 — Producer / Consumer")
class ProducerConsumerTest {

    private ProducerConsumer exercise;

    @BeforeEach
    void setUp() {
        exercise = new ProducerConsumer(10);
    }

    @Test
    @DisplayName("Should produce and consume items")
    void shouldProduceAndConsume() throws InterruptedException {
        exercise.produce("item-1");
        exercise.produce("item-2");
        exercise.produce("item-3");

        assertEquals("item-1", exercise.consume());
        assertEquals("item-2", exercise.consume());
        assertEquals("item-3", exercise.consume());
    }

    @Test
    @DisplayName("Should handle producer-consumer scenario")
    void shouldRunScenario() throws InterruptedException {
        List<String> results = exercise.runScenario(20);

        assertEquals(20, results.size(), "All items should be processed");
        for (int i = 0; i < 20; i++) {
            assertTrue(results.contains("item-" + i), "Missing item: item-" + i);
        }
    }

    @Test
    @DisplayName("Queue should block when full")
    void shouldBlockWhenFull() throws InterruptedException {
        // Fill the queue (capacity 10)
        for (int i = 0; i < 10; i++) {
            exercise.produce("item-" + i);
        }

        assertEquals(10, exercise.queueSize());

        // Producer should block because queue is full
        Thread producer = new Thread(() -> {
            try {
                exercise.produce("overflow");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();

        Thread.sleep(200);
        assertTrue(producer.isAlive(), "Producer should be blocked (queue full)");

        // Consume one item to make room
        exercise.consume();
        producer.join(2000);
        assertFalse(producer.isAlive(), "Producer should have completed");
    }

    @Test
    @DisplayName("Multiple producers and consumers should work together")
    void shouldHandleMultipleProducersAndConsumers() throws InterruptedException {
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        int producers = 3;
        int itemsPerProducer = 10;

        Thread[] producerThreads = new Thread[producers];
        for (int i = 0; i < producers; i++) {
            final int id = i;
            producerThreads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerProducer; j++) {
                        exercise.produce("p" + id + "-" + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread[] consumerThreads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            consumerThreads[i] = exercise.startConsumer(results);
        }

        for (Thread t : producerThreads) t.start();
        for (Thread t : producerThreads) t.join();

        // Give consumers time to drain
        Thread.sleep(1000);
        exercise.stop();
        for (Thread t : consumerThreads) t.join(2000);

        assertEquals(producers * itemsPerProducer, results.size(),
            "All items should be processed by consumers");
    }
}
