# Section 04 — Thread Pools

## Overview

Thread pools manage a fixed set of worker threads that execute submitted tasks. This section explores bounded concurrency, worker reuse, and task queuing.

---

## Exercise 12 — Fixed Thread Pool

### Objective

Observe how a fixed thread pool limits concurrency and queues excess tasks.

### Scenario

You're processing a batch of orders. Each order requires validation. You have a limited number of workers.

### Before You Code

1. What happens when all threads in a fixed pool are busy?
2. Where do new tasks wait?
3. How does worker reuse improve performance?
4. Is `newCachedThreadPool()` safe for production?

### Interview Questions

1. What is a fixed thread pool?
2. What is the task queue?
3. How do you determine the optimal pool size?
4. What happens if tasks are submitted after shutdown?
5. Why is `newCachedThreadPool()` dangerous in production?

---

## Exercise 13 — invokeAll / invokeAny

### Objective

Practice batch task execution with `invokeAll` and `invokeAny`.

### Before You Code

1. What does `invokeAll` return? When does it return?
2. What does `invokeAny` return? What happens to the other tasks?
3. When would you use each?
4. Can you use `invokeAll` with a timeout?

### Interview Questions

1. What is the difference between `submit()` and `invokeAll()`?
2. When does `invokeAll()` return?
3. What does `invokeAny()` do if multiple tasks complete simultaneously?
4. How do you handle timeout with `invokeAll()`?
