package com.concurrency.mastery.interviewchallenges;

import java.util.List;
import java.util.Map;

/**
 * Challenge 3 — Parallel Aggregation
 *
 * Problem:
 * Given a list of orders, compute the total revenue and count of orders
 * per customer in parallel. You must choose the appropriate concurrency
 * approach yourself.
 *
 * Constraints:
 * - Must be thread-safe
 * - Must produce correct results regardless of thread scheduling
 * - Must use parallelism to improve performance
 * - You decide which concurrency primitive to use
 *
 * Acceptance Criteria:
 * - Correct total revenue across all orders
 * - Correct order count per customer
 * - Results match sequential computation
 * - Concurrent access is safe
 *
 * Interview Discussion:
 * - What concurrency primitive did you choose and why?
 * - How did you handle shared state?
 * - What are the alternatives?
 * - How would you scale this to millions of orders?
 */
public class ParallelAggregation {

    /**
     * Simple order record.
     */
    public record Order(String customerId, int amount) {}

    /**
     * Aggregation result.
     */
    public record AggregationResult(int totalRevenue, Map<String, Integer> ordersPerCustomer) {}

    /**
     * Aggregate orders in parallel.
     *
     * @param orders the list of orders
     * @param threadCount the number of threads to use
     * @return the aggregation result
     */
    public AggregationResult aggregate(List<Order> orders, int threadCount) throws Exception {
        // TODO: Implement parallel aggregation.
        // Choose your approach:
        // - Split orders into chunks
        // - Process each chunk in parallel
        // - Combine results
        //
        // Or use ConcurrentHashMap for shared state.
        // You decide which approach is best.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Aggregate orders sequentially (for comparison).
     *
     * @param orders the list of orders
     * @return the aggregation result
     */
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
