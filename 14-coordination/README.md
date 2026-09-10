# Section 14 — Coordination

## Overview

Coordination primitives allow threads to wait for each other: CountDownLatch (wait for N events), Semaphore (limit N concurrent), and CyclicBarrier (sync at phase boundary).

---

## Exercise 31 — CountDownLatch

### Objective

Wait for multiple events to complete.

### Before You Code

1. What does CountDownLatch(N) do?
2. Can a CountDownLatch be reset?
3. What is the "start gate" pattern?

### Interview Questions

1. What is CountDownLatch?
2. When would you use CountDownLatch?
3. Can CountDownLatch be reused?
4. What is the difference between await() and await(timeout)?

---

## Exercise 32 — Semaphore

### Objective

Limit concurrent access to a resource.

### Before You Code

1. What does acquire() do when no permits are available?
2. What does release() do?
3. When is Semaphore appropriate?

### Interview Questions

1. What is a Semaphore?
2. How does it differ from a Lock?
3. When would you use Semaphore vs Lock?
4. What is a fair semaphore?

---

## Exercise 33 — CyclicBarrier

### Objective

Synchronize threads at a phase boundary.

### Before You Code

1. What is the difference between CyclicBarrier and CountDownLatch?
2. Can CyclicBarrier be reset and reused?
3. What is a barrier action?

### Interview Questions

1. What is CyclicBarrier?
2. How does it differ from CountDownLatch?
3. When would you use CyclicBarrier?
4. What happens if one thread doesn't reach the barrier?

### Documentation to Review

- [CountDownLatch](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CountDownLatch.html)
- [Semaphore](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/Semaphore.html)
- [CyclicBarrier](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CyclicBarrier.html)
