# Section 11 — Deadlocks

## Overview

Deadlock occurs when two or more threads each hold a resource the other needs. This section creates a deadlock and then prevents it.

---

## Exercise 27 — Create a Deadlock

### Objective

Understand the four Coffman conditions by creating a deadlock.

### Before You Code

1. What are the four conditions for deadlock?
2. How does circular wait occur?
3. Why can't you "break" a deadlock once it occurs?

### Interview Questions

1. What is a deadlock?
2. What are the four Coffman conditions?
3. How do you detect a deadlock?
4. Can the JVM recover from a deadlock?

---

## Exercise 28 — Prevent the Deadlock

### Objective

Practice three prevention strategies: lock ordering, tryLock, and reduced scope.

### Before You Code

1. How does lock ordering prevent circular wait?
2. How does tryLock break hold-and-wait?
3. When is reduced scope the best approach?

### Interview Questions

1. How do you prevent deadlock?
2. What is lock ordering?
3. How does tryLock help prevent deadlock?
4. What is lock_timeout deadlock prevention?

### Documentation to Review

- [ReentrantLock.tryLock()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/locks/ReentrantLock.html#tryLock())
