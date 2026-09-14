# Airport Baggage Handling System

**Difficulty:** Hard  
**Tags:** Java, Multithreading, Fork-Join Framework, RecursiveTask  
**Time Limit:** 40 minutes  
**Test Cases:** 15

---

## Overview

Implement an airport baggage sorting system that efficiently categorizes items by destination using **parallel processing**. Each baggage item, identified by its name and destination, should be grouped accordingly, leveraging the **Fork-Join Framework** for parallel sorting.

## Architecture

```
BaggageSorter extends RecursiveTask<Map<String, List<BaggageItem>>>
  ├── baggageItems: List<BaggageItem>
  ├── start: int (start index)
  ├── end: int (end index)
  └── compute():
        ├── if (end - start <= 10): sort sequentially
        └── else: fork left + right, merge results
```

## Sorting Rules

| Items | Approach | Behavior |
|-------|----------|----------|
| ≤ 10 bags | Sequential | Sort by arrival order (index in list) |
| > 10 bags | Fork-Join | Split into left/right subtasks, fork both, merge right = right + left |

**Output Format:**
- Destinations sorted alphabetically
- Bags within each destination sorted by arrival time (insertion order)

## Classes to Implement

### BaggageItem

| Field | Type | Description |
|-------|------|-------------|
| `name` | `String` | Bag name, e.g., "BagA" |
| `destination` | `String` | Destination city, e.g., "Paris" |

### BaggageSorter extends `RecursiveTask<Map<String, List<BaggageItem>>>`

| Method | Description |
|--------|-------------|
| `BaggageSorter(List<BaggageItem> items, int start, int end)` | Constructor with scope |
| `sortBaggage(int numberOfBags, List<BaggageItem> items)` → `Map` | Entry point, creates ForkJoinPool |
| `compute()` → `Map` | Recursive task: sequential if ≤10, fork-join otherwise |

### compute() Logic

```
if (end - start <= 10):
    // Sequential: group by destination, maintain arrival order
    map = {}
    for i in start..end:
        bag = baggageItems[i]
        map[bag.destination].add(bag)
    return map
else:
    // Parallel: split, fork, merge
    mid = (start + end) / 2
    left  = new BaggageSorter(baggageItems, start, mid)
    right = new BaggageSorter(baggageItems, mid, end)
    left.fork()
    rightResult = right.compute()
    leftResult = left.join()
    // Merge: right = right + left
    for each destination in leftResult:
        rightResult[destination].addAll(leftResult[destination])
    return rightResult
```

## Sample Cases

### Case 0
**Input:**
```
3
2
Paris
Berlin
BagA
Paris
BagC
Berlin
BagB
Berlin
```
**Output:**
```
Sorted Baggage by Destination:
Berlin: [BagC: Berlin, BagB: Berlin]
Paris: [BagA: Paris]
```

### Case 1
**Input:**
```
4
2
Miami
Dallas
Bag1
Miami
Bag2
Dallas
Bag3
Miami
Bag4
Dallas
```
**Output:**
```
Sorted Baggage by Destination:
Dallas: [Bag2: Dallas, Bag4: Dallas]
Miami: [Bag1: Miami, Bag3: Miami]
```

## Key Concepts

- **Fork-Join Framework:** Divide-and-conquer parallelism using `ForkJoinPool`
- **RecursiveTask:** Returns a result (vs RecursiveAction which doesn't)
- **fork() / join():** fork spawns subtask, join waits for result
- **Task Decomposition:** Split large problems into smaller subtasks
- **Merge Strategy:** Combine results from subtasks (right = right + left)

## Constraints

- `1 ≤ numberOfBags ≤ 30`
- `1 ≤ numberOfDestinations ≤ 20`
- No duplicate bag or destination names
- Each bag's destination is in the provided destination list
