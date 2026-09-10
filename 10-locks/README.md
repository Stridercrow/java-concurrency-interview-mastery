# Section 10 — Locks

## Overview

`ReentrantLock` provides more flexibility than `synchronized`: tryLock, timed locks, interruptible locking, and multiple conditions.

---

## Exercise 26 — ReentrantLock

### Objective

Practice ReentrantLock and compare with synchronized.

### Before You Code

1. When is ReentrantLock better than synchronized?
2. What does `tryLock()` do?
3. Why must you always unlock in a `finally` block?
4. What is a reentrant lock?

### Interview Questions

1. When would you use ReentrantLock over synchronized?
2. What is `tryLock()`?
3. What is `lockInterruptibly()`?
4. What is a Condition variable?
5. Why always unlock in finally?

### Documentation to Review

- [ReentrantLock](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/locks/ReentrantLock.html)
