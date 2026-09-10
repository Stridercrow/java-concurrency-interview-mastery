package com.concurrency.mastery.concurrentcollections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 29 — ConcurrentHashMap
 *
 * Scenario:
 * You're building a shared cache for product prices. Multiple threads read
 * and write prices concurrently. ConcurrentHashMap provides thread-safe
 * operations without locking the entire map.
 */
public class ConcurrentHashMapExercise {

    private final ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();

    /**
     * Put a price in the cache.
     *
     * @param sku the product SKU
     * @param price the price
     */
    public void putPrice(String sku, int price) {
        // TODO: Put the price in the cache
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get a price from the cache. Returns -1 if not found.
     *
     * @param sku the product SKU
     * @return the price, or -1 if not in cache
     */
    public int getPrice(String sku) {
        // TODO: Get the price from the cache, returning -1 if absent
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get a price, or compute and cache it if absent.
     * Uses ConcurrentHashMap's atomic computeIfAbsent.
     *
     * @param sku the product SKU
     * @param computePrice a function that computes the price if not cached
     * @return the price
     */
    public int getOrComputePrice(String sku, java.util.function.Function<String, Integer> computePrice) {
        // TODO: Use computeIfAbsent to atomically get or compute the price
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Count occurrences of each word in a text using ConcurrentHashMap.
     * Demonstrates thread-safe aggregation.
     *
     * @param words the words to count
     * @return a map of word → count
     */
    public Map<String, Integer> countWords(String[] words) {
        // TODO: Use ConcurrentHashMap to count word occurrences.
        // Use merge() or compute() for atomic increment.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the number of entries in the cache.
     */
    public int size() {
        // TODO: Return the cache size
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Clear the cache.
     */
    public void clear() {
        // TODO: Clear the cache
        throw new UnsupportedOperationException("Implement this method");
    }
}
