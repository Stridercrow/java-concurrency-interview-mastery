package com.concurrency.mastery.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * SOLUTION — Exercise 36: Composition
 *
 * Key Concepts:
 * - thenCompose: chain dependent async operations (like flatMap)
 * - thenCombine: combine independent async operations
 * - allOf: wait for all futures to complete
 *
 * thenCompose vs thenCombine:
 * - thenCompose: result of A needed for B (dependent)
 * - thenCombine: A and B are independent, combine results
 */
public class CompositionExerciseSolution {

    public CompletableFuture<String> getCustomerOrderSummary(String userId) {
        return lookupCustomer(userId)
            .thenCompose(customer -> fetchOrders(userId)
                .thenApply(orders -> customer + " " + orders));
    }

    public CompletableFuture<String> getCustomerWithAccount(String customerId) {
        return fetchCustomerName(customerId)
            .thenCombine(fetchAccountBalance(customerId),
                (name, balance) -> name + ",Balance:" + balance);
    }

    public CompletableFuture<List<Integer>> getAllPrices(List<String> items) {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (String item : items) {
            futures.add(fetchPrice(item));
        }

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0]));

        return allDone.thenApply(v ->
            futures.stream()
                .map(CompletableFuture::join)
                .toList()
        );
    }

    // Simulated services
    private CompletableFuture<String> fetchUserInfo(String userId) {
        return CompletableFuture.supplyAsync(() -> "User:" + userId.toLowerCase());
    }
    private CompletableFuture<String> fetchOrders(String userId) {
        return CompletableFuture.supplyAsync(() -> "Orders:" + (userId.hashCode() % 10));
    }
    private CompletableFuture<String> lookupCustomer(String userId) {
        return CompletableFuture.supplyAsync(() -> "Customer:" + userId);
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
