# Section 17 — Interview Challenges

## Overview

These challenges test your ability to **choose the right concurrency primitive** without being told which one to use. Each challenge has a problem statement, constraints, and acceptance criteria — but no hints about which tool to use.

---

## Challenge 1 — Background Worker Shutdown

### Problem

Design a background worker that runs periodically and can be gracefully shut down.

### Constraints

- Use Thread directly (not ExecutorService)
- Must be interruptible
- Must handle InterruptedException correctly
- Must be reusable

### Before You Code

1. How do you stop a thread cooperatively?
2. What is the role of interruption?
3. How do you track worker state?

### Acceptance Criteria

- Worker starts and runs periodically
- Worker stops gracefully when shutdown() is called
- Worker is interruptible
- isRunning() returns accurate status
- Worker can be restarted after shutdown

### Interview Discussion

- Why use interruption for cancellation?
- What happens if you ignore InterruptedException?
- What is the difference between interrupt() and stop()?

---

## Challenge 2 — Thread-Safe Rate Limiter

### Problem

Design a rate limiter that allows at most N concurrent operations.

### Constraints

- Must be thread-safe
- Must support blocking and non-blocking acquisition
- Must support timeout-based acquisition

### Before You Code

1. Which concurrency primitive limits access to N?
2. What happens when all permits are taken?
3. How do you implement try-acquire?

### Acceptance Criteria

- At most N operations can run concurrently
- Extra requests block until permits are available
- tryAcquire returns false immediately if no permits
- acquireWithTimeout respects the timeout

### Interview Discussion

- What primitive did you choose and why?
- How does this compare to a thread pool?
- What are the alternatives?

---

## Challenge 3 — Parallel Aggregation

### Problem

Compute total revenue and per-customer order counts in parallel.

### Constraints

- Must be thread-safe
- Must produce correct results
- You choose the concurrency approach

### Before You Code

1. How would you partition the work?
2. How would you combine partial results?
3. What is the MapReduce pattern?
4. What are the tradeoffs of shared vs local state?

### Acceptance Criteria

- Correct total revenue
- Correct per-customer counts
- Results match sequential computation
- Concurrent access is safe

### Interview Discussion

- What approach did you choose and why?
- How would you scale to millions of orders?
- What is the MapReduce pattern?
