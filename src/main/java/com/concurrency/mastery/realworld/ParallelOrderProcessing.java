package com.concurrency.mastery.realworld;

import java.util.concurrent.CompletableFuture;

/**
 * Exercise 39 — Parallel Order Processing
 *
 * Scenario:
 * You're building an order processing service. When a customer places an order,
 * the system needs to:
 * 1. Look up customer info
 * 2. Check inventory
 * 3. Calculate pricing
 * 4. Get recommendations
 *
 * Steps 2-4 are independent and can run concurrently.
 * Step 1 must complete first (other steps depend on customer info).
 *
 * Design the concurrency strategy and implement it with CompletableFuture.
 */
public class ParallelOrderProcessing {

    /**
     * Process an order by executing independent lookups in parallel.
     *
     * @param orderId the order ID
     * @param customerId the customer ID
     * @return a CompletableFuture with the combined order summary
     */
    public CompletableFuture<String> processOrder(String orderId, String customerId) {
        // TODO: Implement this method using CompletableFuture:
        //
        // 1. First, look up customer info (depends on customerId)
        // 2. Then, in parallel:
        //    a. Check inventory (depends on customerId)
        //    b. Calculate pricing (depends on customerId)
        //    c. Get recommendations (depends on customerId)
        // 3. Wait for all parallel operations to complete
        // 4. Combine all results into a summary string
        //
        // Use thenCompose for dependent operations.
        // Use thenCombine or allOf for independent parallel operations.
        throw new UnsupportedOperationException("Implement this method");
    }

    // === Simulated services (already implemented) ===

    public CompletableFuture<String> lookupCustomer(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Customer:" + customerId);
    }

    public CompletableFuture<String> checkInventory(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Inventory:OK-" + customerId);
    }

    public CompletableFuture<String> calculatePricing(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Price:$" + (Math.abs(customerId.hashCode()) % 1000));
    }

    public CompletableFuture<String> getRecommendations(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Recommendations:3-" + customerId);
    }
}
