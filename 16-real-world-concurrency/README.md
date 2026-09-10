# Section 16 — Real-World Concurrency

## Overview

Apply all concepts learned so far to realistic scenarios: order processing, data aggregation, and performance comparison.

---

## Exercise 39 — Parallel Order Processing

### Objective

Design and implement a concurrent order processing pipeline.

### Scenario

An order requires customer lookup, inventory check, pricing, and recommendations. Some steps are independent and can run in parallel.

### Before You Code

1. Which operations are independent?
2. Which operations depend on others?
3. How do you combine results from parallel operations?
4. What happens if one operation fails?

### Interview Questions

1. How would you design a concurrent order processing system?
2. Which operations can be parallelized?
3. How do you handle partial failures?
4. How do you minimize latency?

---

## Exercise 40 — Parallel Data Processing

### Objective

Compare sequential, thread-per-item, and fixed thread pool approaches.

### Before You Code

1. Why is thread-per-item bad for large datasets?
2. What is the overhead of creating a thread?
3. How does a fixed thread pool avoid this overhead?
4. How does CompletableFuture compare to ExecutorService?

### Interview Questions

1. Why is unbounded thread creation dangerous?
2. What determines the optimal thread pool size?
3. When would you use CompletableFuture vs ExecutorService?
4. How do you choose the right parallelism strategy?

---

## Exercise 41 — Shared vs Local Aggregation

### Objective

Compare shared mutable state vs partitioned local state for aggregation.

### Before You Code

1. What is the contention impact of shared counters?
2. How does partitioning reduce contention?
3. Which approach is simpler?
4. Which approach scales better?

### Interview Questions

1. What is the MapReduce pattern?
2. How does partitioning reduce contention?
3. When is shared state acceptable for aggregation?
4. What are the tradeoffs of each approach?
