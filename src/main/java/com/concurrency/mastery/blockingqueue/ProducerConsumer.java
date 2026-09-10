package com.concurrency.mastery.blockingqueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Exercise 30 — Producer / Consumer
 *
 * Scenario:
 * You're building an order processing pipeline. Producers create orders
 * and put them in a queue. Consumers take orders from the queue and process them.
 * The BlockingQueue handles the coordination automatically.
 */
public class ProducerConsumer {

    private final BlockingQueue<String> queue;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public ProducerConsumer(int queueCapacity) {
        this.queue = new LinkedBlockingQueue<>(queueCapacity);
    }

    /**
     * Produce an item. Blocks if the queue is full.
     *
     * @param item the item to produce
     */
    public void produce(String item) throws InterruptedException {
        // TODO: Put the item in the queue using put() (blocking)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Consume an item. Blocks if the queue is empty.
     *
     * @return the consumed item
     */
    public String consume() throws InterruptedException {
        // TODO: Take an item from the queue using take() (blocking)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Start a producer thread that generates items.
     *
     * @param prefix the prefix for generated items
     * @param count the number of items to produce
     * @return the producer thread
     */
    public Thread startProducer(String prefix, int count) {
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    produce(prefix + "-" + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer-" + prefix);
        producer.start();
        return producer;
    }

    /**
     * Start a consumer thread that processes items.
     * Collects processed items in the provided list.
     *
     * @param results list to collect processed items
     * @return the consumer thread
     */
    public Thread startConsumer(List<String> results) {
        Thread consumer = new Thread(() -> {
            try {
                while (running.get()) {
                    String item;
                    try {
                        item = queue.poll(100, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    if (item != null) {
                        results.add(item);
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");
        consumer.start();
        return consumer;
    }

    /**
     * Stop the consumer gracefully.
     */
    public void stop() {
        running.set(false);
    }

    /**
     * Get the current queue size.
     */
    public int queueSize() {
        return queue.size();
    }

    /**
     * Run a complete producer-consumer scenario and return the processed items.
     *
     * @param itemCount the number of items to produce and consume
     * @return the list of processed items
     */
    public List<String> runScenario(int itemCount) throws InterruptedException {
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        Thread producer = startProducer("item", itemCount);
        Thread consumer = startConsumer(results);

        producer.join();
        // Give consumer time to drain the queue
        consumer.join(5000);
        stop();

        return results;
    }
}
