# Java Concurrency Cheatsheet

A concise reference for Java concurrency concepts. Use this to review after completing exercises.

---

## Core Concepts

### Thread

A thread is a lightweight subprocess — the smallest unit of execution that can be scheduled by the OS.

```java
Thread t = new Thread(() -> System.out.println("Hello from " + Thread.currentThread().getName()));
t.start();  // Creates a new OS thread and runs the Runnable
t.run();    // WRONG — runs on the calling thread, does NOT create a new thread
```

**Key points:**
- `start()` creates a new thread; `run()` executes on the current thread
- Every thread has a name, ID, priority, and state
- `Thread.currentThread()` returns the currently executing thread

### Runnable

A functional interface representing a task with **no return value** and **no checked exceptions**.

```java
Runnable task = () -> System.out.println("Running");
new Thread(task).start();
```

### Callable

A functional interface representing a task with a **return value** and **checked exceptions**.

```java
Callable<Integer> task = () -> { return 42; };
Future<Integer> future = executor.submit(task);
Integer result = future.get(); // blocks
```

### Runnable vs Callable

| Aspect | Runnable | Callable |
|--------|----------|----------|
| Return value | None (void) | Generic type T |
| Checked exceptions | Cannot throw | Can throw Exception |
| Submission | `execute()` or `submit()` | `submit()` only |
| Result retrieval | N/A | Via Future |
| Use case | Fire-and-forget tasks | Tasks producing a result |

---

## Task Execution

### Executor

A simple interface for executing `Runnable` tasks.

```java
Executor executor = Runnable::run; // executes on caller thread
executor.execute(() -> System.out.println("task"));
```

### ExecutorService

Extends `Executor` with lifecycle management, `Future` support, and task submission.

```java
ExecutorService executor = Executors.newFixedThreadPool(4);
Future<String> future = executor.submit(() -> "result");
```

| Method | Description |
|--------|-------------|
| `execute(Runnable)` | Fire and forget |
| `submit(Runnable/Callable)` | Returns Future |
| `invokeAll(tasks)` | Waits for all to complete |
| `invokeAny(tasks)` | Returns first completed result |
| `shutdown()` | No new tasks; finish existing |
| `shutdownNow()` | Attempt to stop all tasks |
| `isShutdown()` | True after shutdown() called |
| `isTerminated()` | True after all tasks finish |

### Thread vs ExecutorService

| Aspect | Thread | ExecutorService |
|--------|--------|-----------------|
| Task submission | Manual | Managed |
| Thread reuse | No | Yes (pools) |
| Lifecycle management | Manual | Built-in |
| Task queuing | No | Yes |
| Future support | No | Yes |
| Recommended for | Rare one-off threads | Most concurrent work |

---

## Thread Pools

| Pool Type | Use Case |
|-----------|----------|
| `newFixedThreadPool(n)` | Bounded parallelism |
| `newCachedThreadPool()` | Many short-lived tasks (dangerous in production) |
| `newSingleThreadExecutor()` | Sequential execution guarantee |
| `newScheduledThreadPool(n)` | Delayed/periodic tasks |

**Task Queue:** When all threads are busy, tasks wait in a `BlockingQueue`.

**Worker Reuse:** Threads are recycled after completing a task.

---

## Shared State

### Race Condition

Two or more threads access shared mutable state and at least one writes, without coordination.

```java
// BROKEN: race condition
int counter = 0;
// Thread A reads 0, Thread B reads 0
// Thread A writes 1, Thread B writes 1
// Expected: 2, Actual: 1
counter++;
```

`counter++` is actually three operations: read, add, write. Each is atomic alone, but the combination is not.

### Visibility

A thread may cache variable values in CPU registers or caches. Without a happens-before relationship, one thread's writes may not be visible to another.

### Atomicity

An operation completes entirely or not at all — no partial state is visible.

### Ordering

The JVM and CPU may reorder instructions for performance. Synchronization establishes happens-before relationships that prevent harmful reorderings.

### Key Distinction

| Concept | What it means | Tool |
|---------|---------------|------|
| Visibility | Thread A's writes are seen by Thread B | `volatile`, `synchronized`, `final` |
| Atomicity | Operation is indivisible | `Atomic*`, `synchronized`, `Lock` |
| Ordering | Instructions execute in a predictable order | `synchronized`, `volatile`, `happens-before` |
| Mutual exclusion | Only one thread accesses critical section | `synchronized`, `Lock` |
| Coordination | Threads wait for each other | Latches, barriers, queues, `CompletableFuture` |

---

## Synchronization

### synchronized

Java's built-in mechanism for mutual exclusion and establishing happens-before relationships.

```java
// Method-level: locks on 'this'
public synchronized void increment() { count++; }

// Block-level: locks on a specific object
synchronized (lockObject) {
    // critical section
}
```

**Intrinsic lock / monitor:** Every Java object has an intrinsic lock. `synchronized` acquires and releases it automatically.

**Properties:**
- Reentrant (same thread can re-acquire)
- Blocking (thread waits if lock held)
- Mutual exclusion (one thread at a time)
- Establishes happens-before (visibility guarantee)

### synchronized vs ReentrantLock

| Aspect | synchronized | ReentrantLock |
|--------|-------------|---------------|
| Acquire/release | Automatic | Manual (must use try/finally) |
| Try acquire | No | `tryLock()` — non-blocking attempt |
| Timed acquire | No | `tryLock(timeout)` |
| Interruptible | No | `lockInterruptibly()` |
| Fairness | No guarantee | Optional fairness policy |
| Condition variables | Single (wait/notify) | Multiple `Condition` objects |
| Performance (JDK 21) | Comparable (biased locking removed) | Comparable |
| Recommendation | Default choice | When you need tryLock or Conditions |

### Lock Contention

When many threads compete for the same lock, effective parallelism drops. Mitigations:

1. Reduce critical section size
2. Partition data so threads access different locks
3. Use lock-free alternatives (atomics, concurrent collections)
4. Use `tryLock()` to avoid blocking

---

## volatile

`volatile` guarantees **visibility** but NOT **atomicity**.

```java
volatile boolean running = true;

// Thread A:
while (running) { /* work */ }  // sees updates to running

// Thread B:
running = false;                 // immediately visible to Thread A
```

**What volatile does:**
- Every read goes to main memory (not CPU cache)
- Every write goes to main memory
- Prevents instruction reordering around volatile variables

**What volatile does NOT do:**
- Make compound operations atomic (`count++` is still unsafe)
- Provide mutual exclusion
- Replace `synchronized`

**volatile vs synchronized vs AtomicInteger:**

| Aspect | volatile | synchronized | AtomicInteger |
|--------|----------|-------------|---------------|
| Visibility | ✓ | ✓ | ✓ |
| Atomicity | ✗ | ✓ | ✓ |
| Mutual exclusion | ✗ | ✓ | ✗ |
| Performance | Fastest | Moderate | Fast |
| Use case | Flags, state signals | Critical sections | Single-variable atomics |

---

## Atomic Classes

`AtomicInteger`, `AtomicLong`, `AtomicBoolean`, `AtomicReference`, etc.

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();    // atomic: ++counter
counter.getAndIncrement();    // atomic: counter++
counter.compareAndSet(5, 10); // CAS: if value==5, set to 10
```

**When to use:** Single variables that need atomic read-modify-write operations.

**Under the hood:** Compare-And-Swap (CAS) — a hardware-level atomic instruction. No locks involved.

**Caveat:** CAS can fail under high contention (spins and retries). For complex compound operations, `synchronized` or `Lock` may be simpler.

---

## Lock (ReentrantLock)

```java
ReentrantLock lock = new ReentrantLock();
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();  // ALWAYS release in finally
}
```

**Key features:**
- `lock()` — blocks until acquired
- `tryLock()` — returns immediately (true/false)
- `tryLock(timeout, unit)` — waits up to timeout
- `lockInterruptibly()` — can be interrupted while waiting
- `unlock()` — must be called in finally block
- Reentrant — same thread can lock multiple times

---

## Deadlock

Two or more threads each hold a lock the other needs, and neither can proceed.

**Four conditions (Coffman conditions):**

1. **Mutual exclusion** — resources cannot be shared
2. **Hold and wait** — thread holds one resource while waiting for another
3. **No preemption** — resources cannot be forcibly taken
4. **Circular wait** — cycle of threads waiting for each other

**Prevention strategies:**
1. **Lock ordering** — always acquire locks in the same global order
2. **Lock timeout** — use `tryLock()` with a timeout
3. **Reduce lock scope** — hold locks for shorter durations
4. **Avoid nested locks** — minimize the number of locks held simultaneously

---

## Concurrent Collections

### ConcurrentHashMap

Thread-safe `Map` without locking the entire map. Uses fine-grained locking (segment locking / CAS).

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("key", 1);
map.computeIfAbsent("key", k -> expensiveLookup(k));
```

**Key difference from Collections.synchronizedMap:**
- `ConcurrentHashMap` allows concurrent reads and writes
- `synchronizedMap` locks the entire map for each operation

---

## BlockingQueue

A `Queue` that blocks threads on `put()` (when full) and `take()` (when empty).

```java
BlockingQueue<Task> queue = new LinkedBlockingQueue<>(100);

// Producer
queue.put(task);  // blocks if queue is full

// Consumer
Task task = queue.take();  // blocks if queue is empty
```

**Implementations:** `ArrayBlockingQueue` (bounded), `LinkedBlockingQueue` (optionally bounded), `SynchronousQueue` (zero capacity).

**Use case:** Producer-consumer pattern, thread pool task queues.

---

## Coordination Primitives

### CountDownLatch

A countdown that blocks until the count reaches zero. **Cannot be reset.**

```java
CountDownLatch latch = new CountDownLatch(3);
// In worker threads:
latch.countDown();  // decrements count
// In waiting thread:
latch.await();      // blocks until count == 0
```

**Mental model:** "Wait until N events happen."

### Semaphore

Controls access to a limited number of permits.

```java
Semaphore semaphore = new Semaphore(3);  // 3 permits
semaphore.acquire();  // blocks if no permits available
try {
    // access limited resource
} finally {
    semaphore.release();
}
```

**Mental model:** "Limit concurrency to N."

### CyclicBarrier

A barrier that blocks until all participants arrive. **Can be reset and reused.**

```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All arrived"));
// In worker threads:
barrier.await();  // blocks until all 3 threads call await()
```

**Mental model:** "Wait until all participants reach this point."

### Comparison

| Aspect | CountDownLatch | Semaphore | CyclicBarrier |
|--------|---------------|-----------|---------------|
| Purpose | Wait for N events | Limit N concurrent accesses | Sync at phase boundary |
| Reusable | No (one-shot) | Yes | Yes (cycles) |
| Who counts down / acquires | Event producers | Resource consumers | Participants |
| Who waits | Consumer(s) | Permits | All participants |
| Callback on release | No | No | Yes (barrier action) |
| Mental model | "Wait until done" | "Limit concurrency" | "All must be ready" |

---

## CompletableFuture

Asynchronous composition without blocking.

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchData())        // async computation
    .thenApply(data -> transform(data))    // transform result
    .thenAccept(result -> save(result))    // consume result
    .exceptionally(ex -> handleError(ex)); // handle errors
```

### Key Methods

| Method | Purpose |
|--------|---------|
| `supplyAsync(supplier)` | Start async computation returning a value |
| `runAsync(runnable)` | Start async computation with no return value |
| `thenApply(fn)` | Transform result (like map) |
| `thenAccept(consumer)` | Consume result (like forEach) |
| `thenRun(runnable)` | Run after completion, ignoring result |
| `thenCompose(fn)` | Chain dependent async operations (like flatMap) |
| `thenCombine(other, fn)` | Combine two independent futures |
| `allOf(futures)` | Wait for all to complete |
| `anyOf(futures)` | Wait for any to complete |
| `exceptionally(fn)` | Handle errors |
| `handle(fn)` | Handle both success and error |
| `whenComplete(action)` | Run action after completion (success or error) |
| `join()` | Get result (unchecked exception) |
| `get(timeout, unit)` | Get result with timeout (checked exception) |

### thenCompose vs thenCombine

| Aspect | thenCompose | thenCombine |
|--------|-------------|-------------|
| Purpose | Chain dependent operations | Combine independent operations |
| Analogy | flatMap | zip |
| Returns | Single future from function | Single future from BiFunction |
| Use when | Result of A needed for B | A and B are independent |

### Custom Executor

By default, `CompletableFuture` runs tasks on the `ForkJoinPool.commonPool()`. Pass an `Executor` to control where tasks execute:

```java
ExecutorService myPool = Executors.newFixedThreadPool(4);
CompletableFuture.supplyAsync(() -> compute(), myPool);
```

---

## Virtual Threads (Java 21+)

Lightweight threads managed by the JVM, not the OS.

```java
// Create
Thread vt = Thread.ofVirtual().name("vt-1").start(() -> work());

// With ExecutorService
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> work());
}
```

**Platform threads vs virtual threads:**

| Aspect | Platform Thread | Virtual Thread |
|--------|----------------|----------------|
| Managed by | OS kernel | JVM |
| Memory overhead | ~1 MB stack | ~few KB |
| Quantity | Thousands | Millions |
| Blocking | Ties up OS thread | Frees carrier thread |
| Best for | CPU-bound work | I/O-bound work |

**When to use virtual threads:**
- Many concurrent I/O operations (HTTP, database, file)
- Blocking code that can't be rewritten
- Replacing thread-per-request models

---

## Decision Tree

```
CONCURRENCY PROBLEM
        |
        v
Is work independent? No shared state?
        |
   YES -→ Avoid shared mutable state
        |   (use local variables, immutable objects)
        |
   NO
        |
        v
Is state immutable?
        |
   YES -→ No synchronization needed
        |
   NO
        |
        v
Is state read-only after initialization?
        |
   YES -→ Make it final or use volatile
        |
   NO
        |
        v
Need a simple flag / visibility only?
        |
   YES -→ volatile
        |
   NO
        |
        v
Need atomic read-modify-write on one variable?
        |
   YES -→ Atomic* (AtomicInteger, etc.)
        |
   NO
        |
        v
Need mutual exclusion?
        |
   YES
        |
   +-- Simple critical section? -→ synchronized
   |
   +-- Need tryLock / timeout / interrupt? -→ ReentrantLock
        |
   NO
        |
        v
Need coordination between threads?
        |
   +-- Wait for N events? -→ CountDownLatch
   +-- Limit N concurrent accesses? -→ Semaphore
   +-- All threads sync at a point? -→ CyclicBarrier
   +-- Producer/consumer? -→ BlockingQueue
   +-- Async composition? -→ CompletableFuture
        |
        v
Need managed task execution?
        |
   +-- Fire and forget? -→ Executor.execute()
   +-- Need result? -→ ExecutorService.submit() + Future
   +-- Need async composition? -→ CompletableFuture
   +-- Need ordered execution? -→ Single-thread executor
```

**First ask: "Can shared mutable state be eliminated?"**

---

## Common Interview Mistakes

1. **Using `synchronized` when `volatile` suffices** — don't lock what only needs visibility
2. **Using `volatile` for compound operations** — `volatile int count; count++` is not atomic
3. **Not handling interruption** — always check `Thread.currentThread().isInterrupted()` in loops
4. **Forgetting `finally` with ReentrantLock** — always unlock in finally
5. **Calling `run()` instead of `start()`** — run() executes on the current thread
6. **Assuming more threads = better performance** — thread creation and context switching have overhead
7. **Using `wait()/notify()` without holding the lock** — IllegalMonitorStateException
8. **Creating threads directly when an Executor is appropriate** — manage threads, don't create them
9. **Not considering thread pool sizing** — too many threads causes contention, too few wastes parallelism
10. **Ignoring `Future.get()` blocking** — it blocks the calling thread; consider timeouts
