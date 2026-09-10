# Section 12 — Concurrent Collections

## Overview

Concurrent collections provide thread-safe operations without external synchronization. `ConcurrentHashMap` is the most commonly used.

---

## Exercise 29 — ConcurrentHashMap

### Objective

Practice thread-safe map operations and atomic compound operations.

### Before You Code

1. Why is HashMap not thread-safe?
2. How does ConcurrentHashMap differ from Collections.synchronizedMap?
3. What is `computeIfAbsent`?
4. What is `merge()`?

### Interview Questions

1. Why is HashMap not thread-safe?
2. What is the difference between ConcurrentHashMap and Collections.synchronizedMap?
3. What operations are atomic in ConcurrentHashMap?
4. How does ConcurrentHashMap achieve thread safety without full locking?

### Documentation to Review

- [ConcurrentHashMap](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html)
