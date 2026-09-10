# Section 05 — Shared vs Non-Shared State

## Overview

This section is critical. Before reaching for synchronization, understand whether state is shared and whether it can be avoided. This section demonstrates the value of avoiding shared mutable state and introduces the race condition.

---

## Exercise 14 — Independent Workers

### Objective

Process tasks in parallel where each worker is independent — no shared state.

### Scenario

You need to count words in multiple documents. Each document is independent.

### Before You Code

1. Is any state shared between workers?
2. Can each worker maintain its own local variables?
3. What is the advantage of independent workers?

### Interview Questions

1. When can you avoid synchronization entirely?
2. What is thread confinement?
3. What is the advantage of independent workers over shared-state workers?

---

## Exercise 15 — Shared Counter (Broken)

### Objective

Observe a race condition in action.

### Scenario

Multiple threads increment a shared counter. The implementation is intentionally broken.

### Before You Code

1. What are the three steps in `count++`?
2. What happens when two threads read the same value?
3. What is a race condition?
4. How would you verify this is broken?

### Important

This exercise is intentionally broken. The test demonstrates the problem — do not try to make the test fail. The purpose is to observe the race condition.

### Interview Questions

1. What is a race condition?
2. Why is `count++` not atomic?
3. What are the three steps in `count++`?
4. Under what conditions does the race condition manifest?

---

## Exercise 16 — Shared Counter Solutions

### Objective

Compare three approaches: `synchronized`, `AtomicInteger`, and avoiding shared state.

### Before You Code

1. What are the tradeoffs of each approach?
2. When would you prefer AtomicInteger over synchronized?
3. When is avoiding shared state the best solution?
4. What is the performance impact of each approach?

### Interview Questions

1. Compare `synchronized` vs `AtomicInteger` for a simple counter.
2. What is the advantage of avoiding shared state?
3. When is `synchronized` the right choice despite alternatives?
4. What is contention and how does it relate to synchronization?
