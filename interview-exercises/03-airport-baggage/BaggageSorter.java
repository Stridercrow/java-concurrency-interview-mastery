package com.concurrency.mastery.interviewexercises.airportbaggage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * BaggageSorter — SKELETON (implement compute() and sortBaggage())
 *
 * Parallel baggage sorter using the Fork-Join Framework.
 * Extends RecursiveTask to return a Map<String, List<BaggageItem>>.
 *
 * Each task processes a sub-range [start, end) of the baggage list.
 *
 * Fork-Join rules:
 * - If (end - start) <= 10: sort sequentially (group by destination)
 * - If (end - start) > 10: split into left/right, fork both, merge results
 *
 * Merge strategy: right = right + left
 * (add all left results into the right map)
 */
class BaggageSorter extends RecursiveTask<Map<String, List<BaggageItem>>> {
    // TODO: Define THRESHOLD constant (10 items)

    // TODO: Add fields for baggageItems, start, and end

    public BaggageSorter(List<BaggageItem> baggageItems, int start, int end) {
        // TODO: Initialize fields
    }

    /**
     * Entry point for sorting.
     *
     * Steps:
     * 1. Create a ForkJoinPool
     * 2. Invoke a new BaggageSorter(baggageItems, 0, numberOfBags)
     * 3. Shutdown the pool
     * 4. Return the result
     */
    public Map<String, List<BaggageItem>> sortBaggage(int numberOfBags,
                                                       List<BaggageItem> baggageItems) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Core recursive logic.
     *
     * If (end - start) <= THRESHOLD:
     *   → Call sequentialSort()
     *
     * Else:
     *   1. Calculate mid = start + size / 2
     *   2. Create leftTask = new BaggageSorter(items, start, mid)
     *   3. Create rightTask = new BaggageSorter(items, mid, end)
     *   4. leftTask.fork()           // Execute left asynchronously
     *   5. rightResult = rightTask.compute()  // Execute right in current thread
     *   6. leftResult = leftTask.join()       // Wait for left
     *   7. mergeResults(rightResult, leftResult)  // right = right + left
     *   8. return rightResult
     */
    @Override
    protected Map<String, List<BaggageItem>> compute() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Sequential sorting: group bags by destination, maintaining arrival order.
     * Use LinkedHashMap to preserve insertion order.
     */
    private Map<String, List<BaggageItem>> sequentialSort() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Merge left results into right results.
     * For each destination in left, add all bags to the corresponding list in right.
     */
    private void mergeResults(Map<String, List<BaggageItem>> right,
                               Map<String, List<BaggageItem>> left) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
