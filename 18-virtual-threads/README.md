# Section 18 — Virtual Threads

## Overview

Virtual threads (Java 21+) are lightweight threads managed by the JVM rather than the OS. They excel at I/O-heavy workloads where threads block frequently.

This is a **bonus section** — not a deep dive. Virtual threads are acknowledged as an important Java 21+ concept.

---

## Exercise 42 — Virtual Threads

### Objective

Create and use virtual threads. Compare with platform threads.

### Scenario

An I/O-heavy service makes many blocking calls. Virtual threads handle this efficiently.

### Before You Code

1. What is a virtual thread?
2. How does it differ from a platform thread?
3. When are virtual threads most useful?
4. When are they NOT useful?

### Key Concepts

| Aspect | Platform Thread | Virtual Thread |
|--------|----------------|----------------|
| Managed by | OS kernel | JVM |
| Memory overhead | ~1 MB stack | ~few KB |
| Quantity supported | Thousands | Millions |
| Blocking behavior | Ties up OS thread | Frees carrier thread |
| Best for | CPU-bound work | I/O-bound work |

### When to Use Virtual Threads

- Many concurrent I/O operations (HTTP, database, file)
- Blocking code that can't be rewritten
- Replacing thread-per-request models
- High concurrency with simple sequential code

### When NOT to Use Virtual Threads

- CPU-bound computation (no advantage over platform threads)
- Thread-local storage heavy workloads
- Native code that blocks OS threads
- When you need thread priority control

### Interview Questions

1. What is a virtual thread?
2. How does it differ from a platform thread?
3. When should you use virtual threads?
4. What is a carrier thread?
5. Do virtual threads improve CPU-bound performance?
6. What is the relationship between virtual threads and ExecutorService?
7. How do you create a virtual thread executor?

### Documentation to Review

- [Thread.ofVirtual()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html#ofVirtual())
- [Executors.newVirtualThreadPerTaskExecutor()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor())
