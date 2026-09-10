# Section 03 — Executors

## Overview

This section covers task execution with `Executor` and `ExecutorService`. You'll learn why delegating task execution to an executor is better than manually creating threads, and how to manage executor lifecycle.

---

## Exercise 08 — Executor vs Manual Threads

### Objective

Understand why ExecutorService is preferred over manual thread creation.

### Scenario

You need to process 100 small tasks. Compare creating a new thread per task vs using a fixed thread pool.

### Before You Code

1. What resources does each thread consume (memory, kernel resources)?
2. What is the overhead of creating and destroying threads?
3. How does a thread pool reduce this overhead?
4. What happens if you create 10,000 threads?

### Interview Questions

1. Why is "one thread per task" a bad pattern for many tasks?
2. What is a thread pool and how does it help?
3. What determines the ideal pool size?
4. What happens to tasks when the pool is busy?

---

## Exercise 09 — ExecutorService Basics

### Objective

Practice creating executors, submitting tasks, and querying state.

### Before You Code

1. What is the difference between `execute()` and `submit()`?
2. What does `submit()` return?
3. How do you know when the executor is ready for new tasks?
4. What happens to unsubmitted tasks after shutdown?

### Interview Questions

1. What is `ExecutorService`?
2. What is the difference between `Executor` and `ExecutorService`?
3. What does `execute(Runnable)` do?
4. What does `submit(Callable)` return?

---

## Exercise 10 — ExecutorService Lifecycle

### Objective

Master graceful shutdown and interruption handling.

### Before You Code

1. What is the difference between `shutdown()` and `shutdownNow()`?
2. What happens to running tasks when you call `shutdownNow()`?
3. How does interruption work as a cancellation mechanism?
4. What is the difference between `isShutdown()` and `isTerminated()`?

### Interview Questions

1. Describe the executor lifecycle (RUNNING → SHUTDOWN → TERMINATED).
2. How do you perform a graceful shutdown?
3. What is the difference between `shutdown()` and `shutdownNow()`?
4. What exception is thrown when a task is interrupted?

---

## Exercise 11 — Callable + Future

### Objective

Practice submitting Callable tasks and retrieving results via Future.

### Before You Code

1. What does `Future.get()` do?
2. What happens if the Callable throws an exception?
3. What does `Future.cancel(true)` do?
4. What is the difference between `get()` and `get(timeout)`?

### Interview Questions

1. What is `Future`?
2. What exceptions can `Future.get()` throw?
3. What does `ExecutionException` wrap?
4. How do you cancel a running task?
5. What is the difference between `cancel(true)` and `cancel(false)`?
