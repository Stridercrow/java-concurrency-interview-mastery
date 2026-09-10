package com.concurrency.mastery.realworld;

import java.util.concurrent.CompletableFuture;

/**
 * SOLUTION — Exercise 39: Parallel Order Processing
 *
 * Key Concepts:
 * - thenCompose for dependent operations
 * - thenCombine for independent parallel operations
 * - allOf for waiting on multiple futures
 *
 * Design:
 * 1. Customer lookup (independent, first)
 * 2. Inventory, pricing, recommendations (independent, parallel)
 * 3. Combine all results
 */
public class ParallelOrderProcessingSolution {

    public CompletableFuture<String> processOrder(String orderId, String customerId) {
        // Step 1: Lookup customer (must complete first)
        return lookupCustomer(customerId)
            .thenCompose(customer -> {
                // Steps 2-4: Independent operations in parallel
                CompletableFuture<String> inventory = checkInventory(customerId);
                CompletableFuture<String> pricing = calculatePricing(customerId);
                CompletableFuture<String> recommendations = getRecommendations(customerId);

                // Wait for all parallel operations
                return CompletableFuture.allOf(inventory, pricing, recommendations)
                    .thenApply(v -> customer + " " +
                        inventory.join() + " " +
                        pricing.join() + " " +
                        recommendations.join());
            });
    }

    // Simulated services
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
