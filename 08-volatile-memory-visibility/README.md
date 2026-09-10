# Section 08 — Volatile and Memory Visibility

## Overview

This section covers the Java Memory Model's visibility guarantee and why `volatile` is necessary but not sufficient for all concurrency problems.

---

## Exercise 22 — Visibility Problem

### Objective

Demonstrate why a non-volatile flag may not be seen by other threads.

### Before You Code

1. What is the visibility problem?
2. Why does the JVM cache variables in CPU registers?
3. What is a happens-before relationship?
4. How does this relate to CPU caches?

### Interview Questions

1. What is the Java Memory Model (JMM)?
2. What is the visibility problem?
3. Why might one thread not see another thread's write?
4. What guarantees does the JMM provide?

---

## Exercise 23 — volatile Shutdown Flag

### Objective

Fix the visibility problem using volatile.

### Before You Code

1. What does volatile guarantee?
2. What does volatile NOT guarantee?
3. Why is volatile appropriate for a shutdown flag?

### Interview Questions

1. What does volatile do?
2. Does volatile make operations atomic?
3. When is volatile sufficient?
4. When is volatile NOT sufficient?

---

## Exercise 24 — Volatile Counter

### Objective

Demonstrate why volatile is not enough for compound operations.

### Before You Code

1. Why is `volatileCount++` not atomic?
2. What are the three steps in `count++`?
3. Why do synchronized and AtomicInteger work but volatile doesn't?
4. When should you use volatile vs synchronized vs AtomicInteger?

### Interview Questions

1. Why is `volatile int count; count++` not thread-safe?
2. What is the difference between visibility and atomicity?
3. Compare volatile vs synchronized vs AtomicInteger.
4. When is volatile the right choice?
