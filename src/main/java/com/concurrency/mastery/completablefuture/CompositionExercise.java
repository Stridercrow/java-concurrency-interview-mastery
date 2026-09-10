package com.concurrency.mastery.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Exercise 36 — Composition
 *
 * Scenario:
 * You're building a customer dashboard that needs to fetch data from
 * multiple services concurrently and combine the results.
 *
 * Practice thenCompose, thenCombine, and allOf.
 */
public class CompositionExercise {

    /**
     * Chain dependent async operations using thenCompose (like flatMap).
     * If the first operation returns a CompletableFuture, thenCompose
     * flattens it into a single CompletableFuture.
     *
     * @param userId the user ID
     * @return a CompletableFuture with the user's order summary
     */
    public CompletableFuture<String> getCustomerOrderSummary(String userId) {
        // TODO: Use thenCompose to chain:
        // 1. Fetch user info (returns CompletableFuture<String>)
        // 2. Fetch orders for that user (returns CompletableFuture<String>)
        // 3. Combine into a summary string
        //
        // Simulate:
        // fetchUserInfo(userId) -> "User:alice"
        // fetchOrders(userId) -> "Orders:5"
        // Result: "User:alice Orders:5"
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Combine two independent async operations using thenCombine.
     *
     * @param customerId the customer ID
     * @return a CompletableFuture with combined customer and account data
     */
    public CompletableFuture<String> getCustomerWithAccount(String customerId) {
        // TODO: Use thenCombine to independently fetch:
        // 1. Customer name
        // 2. Account balance
        // Combine into: "Name:alice,Balance:1000"
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Wait for all futures to complete using allOf.
     *
     * @param items the items to fetch prices for
     * @return a CompletableFuture with all prices
     */
    public CompletableFuture<List<Integer>> getAllPrices(List<String> items) {
        // TODO: Create a CompletableFuture<Integer> for each item.
        // Use allOf to wait for all to complete.
        // Collect all results into a list.
        throw new UnsupportedOperationException("Implement this method");
    }

    // === Simulated async services ===

    private CompletableFuture<String> fetchUserInfo(String userId) {
        return CompletableFuture.supplyAsync(() -> "User:" + userId.toLowerCase());
    }

    private CompletableFuture<String> fetchOrders(String userId) {
        return CompletableFuture.supplyAsync(() -> "Orders:" + (userId.hashCode() % 10));
    }

    private CompletableFuture<String> fetchCustomerName(String customerId) {
        return CompletableFuture.supplyAsync(() -> "Name:" + customerId.toLowerCase());
    }

    private CompletableFuture<Integer> fetchAccountBalance(String customerId) {
        return CompletableFuture.supplyAsync(() -> Math.abs(customerId.hashCode()) % 10000);
    }

    private CompletableFuture<Integer> fetchPrice(String item) {
        return CompletableFuture.supplyAsync(() -> Math.abs(item.hashCode()) % 1000);
    }
}
