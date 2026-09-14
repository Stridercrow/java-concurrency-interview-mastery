package com.concurrency.mastery.interviewexercises.airportbaggage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * SOLUTION — Airport Baggage Handling System
 *
 * Key Concepts:
 * - Fork-Join Framework: divide-and-conquer parallelism
 * - RecursiveTask: returns a result (Map<String, List<BaggageItem>>)
 * - Task Decomposition: split when > 10 items, sequential when ≤ 10
 * - Merge Strategy: right = right + left (merge left into right)
 * - ForkJoinPool: manages worker threads and task scheduling
 *
 * Design Decisions:
 * 1. Threshold of 10 items for sequential vs parallel
 * 2. LinkedHashMap preserves insertion order (arrival time)
 * 3. Merge adds left results to right (right = right + left)
 * 4. ForkJoinPool.commonPool() used for simplicity
 */

// ============================================================
// BaggageItem
// ============================================================
class BaggageItem {
    private final String name;
    private final String destination;

    public BaggageItem(String name, String destination) {
        this.name = name;
        this.destination = destination;
    }

    public String getName() { return name; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return name + ": " + destination;
    }
}

// ============================================================
// BaggageSorter
// ============================================================
/**
 * Parallel baggage sorter using the Fork-Join Framework.
 *
 * Extends RecursiveTask to return a Map<String, List<BaggageItem>>.
 * Each task processes a sub-range [start, end) of the baggage list.
 *
 * Fork-Join Pattern:
 * 1. Base case (≤ 10 items): sort sequentially
 * 2. Recursive case (> 10 items): fork left and right subtasks
 * 3. Merge: combine left results into right results
 */
class BaggageSorter extends RecursiveTask<Map<String, List<BaggageItem>>> {
    private static final int THRESHOLD = 10;

    private final List<BaggageItem> baggageItems;
    private final int start;
    private final int end;

    public BaggageSorter(List<BaggageItem> baggageItems, int start, int end) {
        this.baggageItems = baggageItems;
        this.start = start;
        this.end = end;
    }

    /**
     * Entry point for sorting. Creates a ForkJoinPool and invokes the task.
     */
    public Map<String, List<BaggageItem>> sortBaggage(int numberOfBags,
                                                       List<BaggageItem> baggageItems) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            return pool.invoke(new BaggageSorter(baggageItems, 0, numberOfBags));
        } finally {
            pool.shutdown();
        }
    }

    /**
     * Core recursive logic:
     * - Small task (≤ 10 items): sequential grouping by destination
     * - Large task (> 10 items): fork left and right, merge results
     *
     * Merge strategy: right = right + left
     * This means we add all left results into the right map.
     */
    @Override
    protected Map<String, List<BaggageItem>> compute() {
        int size = end - start;

        // Base case: sequential sorting for small tasks
        if (size <= THRESHOLD) {
            return sequentialSort();
        }

        // Recursive case: split and fork
        int mid = start + size / 2;
        BaggageSorter leftTask = new BaggageSorter(baggageItems, start, mid);
        BaggageSorter rightTask = new BaggageSorter(baggageItems, mid, end);

        leftTask.fork(); // Execute left asynchronously
        Map<String, List<BaggageItem>> rightResult = rightTask.compute(); // Compute right in current thread
        Map<String, List<BaggageItem>> leftResult = leftTask.join(); // Wait for left

        // Merge: right = right + left
        mergeResults(rightResult, leftResult);

        return rightResult;
    }

    /**
     * Sequential sorting: group bags by destination, maintaining arrival order.
     * Uses LinkedHashMap to preserve insertion order.
     */
    private Map<String, List<BaggageItem>> sequentialSort() {
        Map<String, List<BaggageItem>> result = new LinkedHashMap<>();
        for (int i = start; i < end; i++) {
            BaggageItem bag = baggageItems.get(i);
            result.computeIfAbsent(bag.getDestination(), k -> new ArrayList<>()).add(bag);
        }
        return result;
    }

    /**
     * Merge left results into right results.
     * For each destination in left, add all bags to the corresponding list in right.
     */
    private void mergeResults(Map<String, List<BaggageItem>> right,
                               Map<String, List<BaggageItem>> left) {
        for (Map.Entry<String, List<BaggageItem>> entry : left.entrySet()) {
            right.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                 .addAll(entry.getValue());
        }
    }
}
