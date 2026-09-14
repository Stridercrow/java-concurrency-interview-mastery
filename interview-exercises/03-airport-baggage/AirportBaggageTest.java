package com.concurrency.mastery.interviewexercises.airportbaggage;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Airport Baggage Handling System.
 *
 * Run these tests against your skeleton implementation to validate correctness.
 */
class AirportBaggageTest {

    // ============================================================
    // BaggageItem Tests
    // ============================================================

    @Test
    void testBaggageItemCreation() {
        BaggageItem bag = new BaggageItem("BagA", "Paris");
        assertEquals("BagA", bag.getName());
        assertEquals("Paris", bag.getDestination());
    }

    @Test
    void testBaggageItemToString() {
        BaggageItem bag = new BaggageItem("BagA", "Paris");
        assertEquals("BagA: Paris", bag.toString());
    }

    // ============================================================
    // Sequential Sorting (≤ 10 bags)
    // ============================================================

    @Test
    void testSequentialSortSingleDestination() {
        List<BaggageItem> bags = Arrays.asList(
            new BaggageItem("BagA", "Berlin"),
            new BaggageItem("BagB", "Berlin"),
            new BaggageItem("BagC", "Berlin")
        );

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("Berlin"));
        assertEquals(3, result.get("Berlin").size());
        // Verify arrival order preserved
        assertEquals("BagA", result.get("Berlin").get(0).getName());
        assertEquals("BagB", result.get("Berlin").get(1).getName());
        assertEquals("BagC", result.get("Berlin").get(2).getName());
    }

    @Test
    void testSequentialSortMultipleDestinations() {
        List<BaggageItem> bags = Arrays.asList(
            new BaggageItem("BagA", "Paris"),
            new BaggageItem("BagC", "Berlin"),
            new BaggageItem("BagB", "Berlin")
        );

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(2, result.size());
        // Berlin bags in arrival order
        assertEquals(Arrays.asList("BagC", "BagB"),
            result.get("Berlin").stream().map(BaggageItem::getName).toList());
        // Paris bags
        assertEquals(Arrays.asList("BagA"),
            result.get("Paris").stream().map(BaggageItem::getName).toList());
    }

    @Test
    void testSequentialSortSampleCase0() {
        List<BaggageItem> bags = Arrays.asList(
            new BaggageItem("Bag1", "Miami"),
            new BaggageItem("Bag2", "Dallas"),
            new BaggageItem("Bag3", "Miami"),
            new BaggageItem("Bag4", "Dallas")
        );

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(2, result.size());
        assertEquals(Arrays.asList("Bag2", "Bag4"),
            result.get("Dallas").stream().map(BaggageItem::getName).toList());
        assertEquals(Arrays.asList("Bag1", "Bag3"),
            result.get("Miami").stream().map(BaggageItem::getName).toList());
    }

    @Test
    void testSequentialSortSampleCase1() {
        List<BaggageItem> bags = Arrays.asList(
            new BaggageItem("BagX", "Tokyo"),
            new BaggageItem("BagY", "Kyoto"),
            new BaggageItem("BagZ", "Tokyo")
        );

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(2, result.size());
        assertEquals(Arrays.asList("BagY"),
            result.get("Kyoto").stream().map(BaggageItem::getName).toList());
        assertEquals(Arrays.asList("BagX", "BagZ"),
            result.get("Tokyo").stream().map(BaggageItem::getName).toList());
    }

    // ============================================================
    // Parallel Sorting (> 10 bags)
    // ============================================================

    @Test
    void testParallelSortMoreThan10Bags() {
        List<BaggageItem> bags = new ArrayList<>();
        // Create 15 bags across 3 destinations
        for (int i = 0; i < 5; i++) bags.add(new BaggageItem("A" + i, "Berlin"));
        for (int i = 0; i < 5; i++) bags.add(new BaggageItem("B" + i, "Paris"));
        for (int i = 0; i < 5; i++) bags.add(new BaggageItem("C" + i, "London"));

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(3, result.size());
        assertEquals(5, result.get("Berlin").size());
        assertEquals(5, result.get("Paris").size());
        assertEquals(5, result.get("London").size());
    }

    @Test
    void testParallelSortPreservesArrivalOrder() {
        List<BaggageItem> bags = new ArrayList<>();
        // 12 bags to trigger parallel sorting
        bags.add(new BaggageItem("First", "NYC"));
        for (int i = 1; i < 11; i++) {
            bags.add(new BaggageItem("Mid" + i, "LA"));
        }
        bags.add(new BaggageItem("Last", "NYC"));

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        // NYC bags should be in arrival order: First, Last
        assertEquals(Arrays.asList("First", "Last"),
            result.get("NYC").stream().map(BaggageItem::getName).toList());
    }

    // ============================================================
    // Edge Cases
    // ============================================================

    @Test
    void testSingleBag() {
        List<BaggageItem> bags = Arrays.asList(
            new BaggageItem("OnlyBag", "Rome")
        );

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(1, result.size());
        assertEquals("OnlyBag", result.get("Rome").get(0).getName());
    }

    @Test
    void testExactly10BagsSequential() {
        List<BaggageItem> bags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bags.add(new BaggageItem("Bag" + i, "Destination"));
        }

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(10, result.get("Destination").size());
    }

    @Test
    void test11BagsTriggersParallel() {
        List<BaggageItem> bags = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            bags.add(new BaggageItem("Bag" + i, "Destination"));
        }

        BaggageSorter sorter = new BaggageSorter(bags, 0, bags.size());
        Map<String, List<BaggageItem>> result = sorter.sortBaggage(bags.size(), bags);

        assertEquals(11, result.get("Destination").size());
    }
}
