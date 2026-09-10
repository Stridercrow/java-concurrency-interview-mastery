# Detailed Solutions

This document provides in-depth explanations for each exercise, covering the problem, naive approaches, correct solutions, tradeoffs, and interview perspective.

---

## Section 01 — Thread Fundamentals

### Exercise 01: Creating Threads

**Problem:** Create threads correctly using Thread and Runnable.

**Naive Approach:** Calling `run()` instead of `start()`.
```java
task.run(); // Executes on the calling thread — no new thread created
```

**Why it fails:** `run()` is just a method call. It executes synchronously on the current thread.

**Correct Approach:**
```java
Thread t = new Thread(task);
t.start(); // Creates a new OS thread and runs the task
```

**Key Distinction:** `start()` creates a new thread; `run()` does not.

**Interview Explanation:** "I create a Thread with the Runnable and call start(). This tells the JVM to create a new OS thread and execute the task there. If I called run() instead, it would execute on the current thread — no concurrency."

---

### Exercise 04: Joining Threads

**Problem:** Wait for threads to complete.

**Key Insight:** `join()` blocks until the thread terminates. `sleep()` blocks for a fixed duration regardless of thread state.

**Interview Explanation:** "`join()` is about waiting for completion; `sleep()` is about waiting for time. If I need to ensure a thread finishes before proceeding, I use `join()`."

---

## Section 02 — Runnable vs Callable

### Exercise 07: Choosing the Right Abstraction

**Decision Framework:**
- Need a return value? → `Callable`
- Need to throw checked exceptions? → `Callable`
- Neither? → `Runnable` (simpler)

**Interview Explanation:** "I choose Callable when the task must produce a result or throw a checked exception. For fire-and-forget tasks like logging or notifications, Runnable is simpler and more appropriate."

---

## Section 05 — Shared State

### Exercise 15: Shared Counter (Broken)

**Problem:** `counter++` is not atomic — it's three operations: read, add, write.

**Why it fails:**
```
Thread A reads: 0
Thread B reads: 0
Thread A writes: 1
Thread B writes: 1  // Lost increment!
```

**Key Concepts:**
- **Visibility:** Thread A's write may not be seen by Thread B (CPU caching)
- **Atomicity:** The read-modify-write sequence is not indivisible

**Interview Explanation:** "`count++` is actually three steps: read the current value, add one, write the new value. When two threads do this simultaneously, they can read the same value and both write the same result, losing one increment. This is a classic race condition."

---

### Exercise 16: Shared Counter Solutions

**Three Approaches:**

| Approach | Mechanism | Pros | Cons |
|----------|-----------|------|------|
| `synchronized` | Mutual exclusion | Simple, reentrant | Can cause contention |
| `AtomicInteger` | CAS (lock-free) | Faster for single variables | CAS spin under contention |
| Thread-local | No shared state | Best performance | Requires result collection |

**Interview Explanation:** "For a simple counter, AtomicInteger is usually fastest. For complex operations involving multiple variables, synchronized is simpler. The best solution is often to avoid shared state entirely through partitioning."

---

## Section 06 — Race Conditions

### Exercise 17: Bank Account Transfer

**Problem:** Transfer requires atomically debiting one account and crediting another.

**Why naive fails:** Two threads transferring between the same accounts can interleave operations, causing inconsistent state.

**Correct Approach:** Lock both accounts in consistent order (by accountId) to prevent deadlock.

**Interview Explanation:** "For transfer, I need to lock both accounts atomically. To prevent deadlock, I always acquire locks in a consistent order — for example, sorted by account ID. This eliminates circular wait."

---

### Exercise 18: Inventory Reservation

**Problem:** Atomic check-and-decrement using CAS.

**CAS Loop Pattern:**
```java
while (true) {
    int current = stock.get();
    if (current < quantity) return false;
    if (stock.compareAndSet(current, current - quantity)) return true;
    // CAS failed — retry
}
```

**Interview Explanation:** "I use a CAS loop: read the current stock, check if there's enough, then try compareAndSet. If CAS fails (another thread changed the value), I retry. This is lock-free and avoids deadlocks."

---

## Section 07 — Synchronization

### Exercise 20: Intrinsic Locks / Monitors

**Problem:** Coordinate producers and consumers using wait/notify.

**Key Rules:**
1. Always call `wait()` inside a `synchronized` block
2. Use `while` (not `if`) to check the condition — handles spurious wakeups
3. Call `notifyAll()` to wake all waiting threads

**Interview Explanation:** "`wait()` releases the lock and blocks until notified. I use `while` instead of `if` because of spurious wakeups — the thread might wake up without being notified. `notifyAll()` wakes all waiting threads so they can re-check the condition."

---

## Section 08 — Volatile and Memory Visibility

### Exercise 24: Volatile Counter

**Problem:** Why `volatile int count; count++` is not thread-safe.

**Key Distinction:**
- **Visibility:** volatile ensures writes are visible to other threads
- **Atomicity:** volatile does NOT make compound operations atomic

**Interview Explanation:** "`volatile` guarantees that every read goes to main memory and every write goes to main memory. But `count++` is still three operations — two threads can both read the same value before either writes. Volatile provides visibility but not atomicity."

---

## Section 10 — Locks

### Exercise 26: ReentrantLock

**When to use ReentrantLock over synchronized:**
1. Need `tryLock()` — non-blocking attempt
2. Need timed locks — `tryLock(timeout)`
3. Need interruptible locking — `lockInterruptibly()`
4. Need multiple conditions — `newCondition()`

**Interview Explanation:** "ReentrantLock gives me tryLock() which synchronized doesn't. If I need to attempt to acquire a lock without blocking, or if I need a timeout, ReentrantLock is the right choice. Otherwise, synchronized is simpler."

---

## Section 11 — Deadlocks

### Exercise 27: Create a Deadlock

**Four Coffman Conditions:**
1. Mutual exclusion — synchronized blocks provide this
2. Hold and wait — each thread holds one lock while waiting for another
3. No preemption — synchronized locks cannot be forcibly released
4. Circular wait — Thread A waits for B's lock, B waits for A's lock

### Exercise 28: Prevent the Deadlock

**Three Prevention Strategies:**

1. **Lock Ordering:** Always acquire locks in the same global order
```java
// Always lock A before B
synchronized (lockA) {
    synchronized (lockB) { ... }
}
```

2. **tryLock with timeout:** Non-blocking acquisition
```java
if (lockA.tryLock()) {
    try {
        if (lockB.tryLock()) {
            try { ... } finally { lockB.unlock(); }
        }
    } finally { lockA.unlock(); }
}
```

3. **Reduce lock scope:** Never hold both locks simultaneously

**Interview Explanation:** "I prevent deadlock by eliminating circular wait. The simplest approach is lock ordering — always acquire locks in the same order. If that's not possible, I use tryLock with a timeout so threads don't block indefinitely."

---

## Section 12 — Concurrent Collections

### Exercise 29: ConcurrentHashMap

**Why ConcurrentHashMap:**
- Thread-safe without locking the entire map
- Fine-grained locking (per-bucket or CAS)
- Atomic compound operations: `computeIfAbsent`, `merge`

**Interview Explanation:** "ConcurrentHashMap allows concurrent reads and writes without locking the entire map. For atomic operations like get-or-compute, I use computeIfAbsent which is lock-free for the common case."

---

## Section 13 — Blocking Queue

### Exercise 30: Producer / Consumer

**Key Insight:** BlockingQueue handles coordination automatically — `put()` blocks when full, `take()` blocks when empty. No explicit synchronization needed.

**Interview Explanation:** "BlockingQueue is the ideal primitive for producer-consumer. `put()` blocks the producer when the queue is full, and `take()` blocks the consumer when empty. The queue handles all the coordination — I don't need wait/notify or locks."

---

## Section 14 — Coordination

### CountDownLatch vs Semaphore vs CyclicBarrier

| Aspect | CountDownLatch | Semaphore | CyclicBarrier |
|--------|---------------|-----------|---------------|
| Purpose | Wait for N events | Limit N concurrent accesses | Sync at phase boundary |
| Reusable | No | Yes | Yes |
| Mental model | "Wait until done" | "Limit concurrency" | "All must be ready" |

**Interview Explanation:**
- **CountDownLatch:** "I use this when I need to wait for N things to happen — like waiting for all services to initialize."
- **Semaphore:** "I use this when I need to limit concurrency — like a connection pool with max 10 connections."
- **CyclicBarrier:** "I use this when multiple threads need to reach a phase boundary before continuing — like a multi-stage computation."

---

## Section 15 — CompletableFuture

### Exercise 36: Composition

**thenCompose vs thenCombine:**
- `thenCompose`: Chain dependent operations (result of A needed for B)
- `thenCombine`: Combine independent operations (A and B are independent)

**Interview Explanation:** "`thenCompose` is like flatMap — it chains dependent async operations. `thenCombine` is like zip — it combines independent operations. I use thenCompose when the second operation depends on the first result, and thenCombine when they're independent."

---

## Section 17 — Interview Challenges

### Key Principle: Choose the Right Primitive

The challenges test your ability to **select** the appropriate concurrency tool:

| Challenge | Key Primitive | Why |
|-----------|--------------|-----|
| Background Worker | Thread + AtomicBoolean | Direct thread control, cooperative cancellation |
| Rate Limiter | Semaphore | "Limit to N concurrent" pattern |
| Parallel Aggregation | ExecutorService + ConcurrentHashMap | Partition-process-combine pattern |

**Interview Discussion Points:**
1. Why did you choose this primitive over alternatives?
2. What are the tradeoffs?
3. How would you scale this?
4. What could go wrong under high load?

---

## Key Takeaways

1. **First ask: Can shared mutable state be eliminated?**
2. **volatile ≠ atomicity** — volatile ensures visibility, not atomicity
3. **synchronized ≠ always correct** — sometimes volatile or Atomic* is sufficient
4. **Lock ordering prevents deadlock** — always acquire locks in consistent order
5. **CompletableFuture enables async composition** — thenCompose for dependent, thenCombine for independent
6. **BlockingQueue handles producer-consumer coordination** — no manual synchronization needed
7. **Semaphore limits concurrency** — perfect for resource pools
8. **CountDownLatch waits for events, CyclicBarrier syncs phases** — choose based on reusability
