package com.concurrency.mastery.blockingqueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SOLUTION — Exercise 30: Producer / Consumer
 *
 * Key Concepts:
 * - BlockingQueue.put() blocks when full
 * - BlockingQueue.take() blocks when empty
 * - Natural coordination without explicit synchronization
 */
public class ProducerConsumerSolution {

    private final BlockingQueue<String> queue;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public ProducerConsumerSolution(int queueCapacity) {
        this.queue = new LinkedBlockingQueue<>(queueCapacity);
    }

    public void produce(String item) throws InterruptedException {
        queue.put(item);
    }

    public String consume() throws InterruptedException {
        return queue.take();
    }

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

    public void stop() { running.set(false); }
    public int queueSize() { return queue.size(); }

    public List<String> runScenario(int itemCount) throws InterruptedException {
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        Thread producer = startProducer("item", itemCount);
        Thread consumer = startConsumer(results);
        producer.join();
        consumer.join(5000);
        stop();
        return results;
    }
}
