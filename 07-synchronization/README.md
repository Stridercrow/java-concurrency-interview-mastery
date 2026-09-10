# Section 07 — Synchronization

## Overview

This section covers `synchronized` in depth: method-level vs block-level, intrinsic locks (monitors), and the impact of lock contention on performance.

---

## Exercise 19 — synchronized Method vs Block

### Objective

Compare method-level and block-level synchronization.

### Before You Code

1. What object does `synchronized` lock on in a method?
2. What is the difference between `synchronized void foo()` and `synchronized(this) { ... }`?
3. When would you prefer block-level synchronization?
4. Can you synchronize on a different object?

### Interview Questions

1. What does `synchronized` on a method lock?
2. What does `synchronized(obj)` lock?
3. What is an intrinsic lock?
4. What is a monitor?

---

## Exercise 20 — Intrinsic Locks / Monitors

### Objective

Implement wait/notify coordination with a bounded buffer.

### Before You Code

1. What is the difference between `wait()` and `sleep()`?
2. Why must `wait()` be called inside a synchronized block?
3. What does `notify()` vs `notifyAll()` do?
4. What is the monitor pattern?

### Interview Questions

1. What is the difference between `wait()` and `sleep()`?
2. Why must you hold the lock before calling `wait()`?
3. What is a spurious wakeup?
4. Why use `while` instead of `if` with `wait()`?

---

## Exercise 21 — Lock Contention

### Objective

Measure and understand the impact of synchronization on performance.

### Before You Code

1. What is lock contention?
2. How does contention affect throughput?
3. When is AtomicInteger faster than synchronized?
4. What are the tradeoffs of each approach?

### Interview Questions

1. What is lock contention?
2. How do you reduce lock contention?
3. When would you choose AtomicInteger over synchronized?
4. What is lock striping?
