# Section 13 — Blocking Queue

## Overview

`BlockingQueue` provides blocking `put()` and `take()` operations, making it ideal for producer-consumer patterns.

---

## Exercise 30 — Producer / Consumer

### Objective

Implement a producer-consumer system using BlockingQueue.

### Before You Code

1. What does `put()` do when the queue is full?
2. What does `take()` do when the queue is empty?
3. Why is BlockingQueue better than manual wait/notify?
4. How do you shut down a producer-consumer system?

### Interview Questions

1. What is a BlockingQueue?
2. What is the difference between `put()` and `add()`?
3. What is the difference between `take()` and `poll()`?
4. How do you implement graceful shutdown for producer-consumer?
5. What BlockingQueue implementations exist?

### Documentation to Review

- [BlockingQueue](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/BlockingQueue.html)
- [LinkedBlockingQueue](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/LinkedBlockingQueue.html)
