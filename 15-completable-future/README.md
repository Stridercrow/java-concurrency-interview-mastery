# Section 15 — CompletableFuture

## Overview

`CompletableFuture` enables asynchronous composition — chaining, combining, and transforming async operations without blocking. This section covers the essential methods for interviews.

---

## Exercise 34 — supplyAsync

### Objective

Start asynchronous computations that produce values.

### Before You Code

1. What does `supplyAsync()` do?
2. What thread does the task run on by default?
3. How do you specify a custom executor?
4. What is the difference between `supplyAsync()` and `runAsync()`?

### Interview Questions

1. What is `CompletableFuture`?
2. What thread does `supplyAsync()` use by default?
3. How do you control which thread pool runs the task?
4. What is `ForkJoinPool.commonPool()`?

---

## Exercise 35 — Transformation

### Objective

Chain transformations on async results.

### Before You Code

1. What is `thenApply`? How does it differ from `thenAccept`?
2. What is `thenRun`?
3. Are transformations applied on the same thread?

### Interview Questions

1. What is the difference between `thenApply`, `thenAccept`, and `thenRun`?
2. Do transformations block the calling thread?
3. On what thread do transformations run?

---

## Exercise 36 — Composition

### Objective

Combine independent and dependent async operations.

### Before You Code

1. What is `thenCompose`? When do you use it?
2. What is `thenCombine`? When do you use it?
3. What does `allOf` do?
4. What is the difference between `thenCompose` and `thenCombine`?

### Interview Questions

1. What is the difference between `thenCompose` and `thenCombine`?
2. When would you use `allOf`?
3. What does `anyOf` do?
4. How do you collect results from multiple futures?

---

## Exercise 37 — Exception Handling

### Objective

Handle async failures gracefully.

### Before You Code

1. What does `exceptionally` do?
2. What does `handle` do?
3. What does `whenComplete` do?
4. How do exceptions propagate through a chain?

### Interview Questions

1. How do exceptions propagate in CompletableFuture chains?
2. What is the difference between `exceptionally`, `handle`, and `whenComplete`?
3. Can you recover from an exception in a CompletableFuture chain?
4. What happens if you don't handle an exception?

---

## Exercise 38 — Custom Executor

### Objective

Control which thread pool executes async tasks.

### Before You Code

1. Why use a custom executor instead of the common pool?
2. What happens if you use the common pool for blocking I/O?
3. How do you shut down the executor after use?

### Interview Questions

1. Why shouldn't you use `ForkJoinPool.commonPool()` for blocking I/O?
2. How do you create a custom executor for CompletableFuture?
3. How do you ensure executor cleanup?
