package com.concurrency.mastery.interviewchallenges;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Challenge 3: Parallel Aggregation
 *
 * Key Concepts:
 * - MapReduce pattern: partition, process locally, combine
 * - ConcurrentHashMap for shared aggregation
 * - AtomicLong for thread-safe counters
 *
 * Approach: Partition records into chunks, process each chunk
 * independently, then combine results. This minimizes contention.
 */
public class ParallelAggregationSolution {

    public record Order(String customerId, int amount) {}
    public record AggregationResult(int totalRevenue, Map<String, Integer> ordersPerCustomer) {}

    public AggregationResult aggregate(List<Order> orders, int threadCount) throws Exception {
        // Partition orders into chunks
        int chunkSize = (int) Math.ceil((double) orders.size() / threadCount);
        List<Future<AggregationResult>> futures = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < orders.size(); i += chunkSize) {
            List<Order> chunk = orders.subList(i, Math.min(i + chunkSize, orders.size()));
            futures.add(executor.submit(() -> aggregateSequential(chunk)));
        }

        // Combine results
        int totalRevenue = 0;
        Map<String, Integer> combined = new ConcurrentHashMap<>();

        for (Future<AggregationResult> future : futures) {
            AggregationResult partial = future.get();
            totalRevenue += partial.totalRevenue();
            partial.ordersPerCustomer().forEach((k, v) -> combined.merge(k, v, Integer::sum));
        }

        executor.shutdown();
        return new AggregationResult(totalRevenue, combined);
    }

    public AggregationResult aggregateSequential(List<Order> orders) {
        int totalRevenue = 0;
        java.util.Map<String, Integer> ordersPerCustomer = new java.util.HashMap<>();

        for (Order order : orders) {
            totalRevenue += order.amount();
            ordersPerCustomer.merge(order.customerId(), 1, Integer::sum);
        }

        return new AggregationResult(totalRevenue, ordersPerCustomer);
    }
}
