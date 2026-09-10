package com.concurrency.mastery.concurrentcollections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SOLUTION — Exercise 29: ConcurrentHashMap
 *
 * Key Concepts:
 * - Thread-safe map operations without full locking
 * - computeIfAbsent for atomic get-or-compute
 * - merge() for atomic aggregation
 *
 * Why ConcurrentHashMap over Collections.synchronizedMap:
 * - Concurrent reads and writes
 * - Fine-grained locking (per-bucket)
 * - Atomic compound operations
 */
public class ConcurrentHashMapSolution {

    private final ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();

    public void putPrice(String sku, int price) {
        cache.put(sku, price);
    }

    public int getPrice(String sku) {
        return cache.getOrDefault(sku, -1);
    }

    public int getOrComputePrice(String sku, java.util.function.Function<String, Integer> computePrice) {
        return cache.computeIfAbsent(sku, computePrice);
    }

    public Map<String, Integer> countWords(String[] words) {
        ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();
        for (String word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        return counts;
    }

    public int size() { return cache.size(); }
    public void clear() { cache.clear(); }
}
