# Section 09 — Atomic Operations

## Overview

Atomic classes provide lock-free thread-safe operations on single variables using CAS (Compare-And-Swap).

---

## Exercise 25 — AtomicInteger Counter

### Objective

Practice AtomicInteger methods and the CAS loop pattern.

### Before You Code

1. What does `incrementAndGet()` do atomically?
2. What is CAS (Compare-And-Swap)?
3. When would you use AtomicInteger vs synchronized?
4. What is the CAS loop pattern?

### Interview Questions

1. What is AtomicInteger?
2. How does CAS work?
3. When is AtomicInteger preferred over synchronized?
4. What is the ABA problem?
5. What happens under high contention with CAS?

### Documentation to Review

- [AtomicInteger](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/atomic/AtomicInteger.html)
