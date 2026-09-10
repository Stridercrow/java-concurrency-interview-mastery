# Section 01 — Thread Fundamentals

## Overview

This section covers the absolute basics of Java threading: creating threads, understanding thread properties, observing thread lifecycle states, and coordinating threads via join().

These exercises establish the foundation for everything else in this repository.

---

## Exercise 01 — Creating Threads

### Objective

Practice creating threads using `Thread` and `Runnable`.

### Scenario

You are building a log processing system. Multiple log files need to be processed concurrently. You need to understand how to correctly create and start threads.

### Requirements

- Create a thread from a `Runnable`
- Create a thread with a specific name
- Start a thread and wait for completion
- Understand the difference between `start()` and `run()`

### Before You Code

1. What happens if you call `run()` instead of `start()`?
2. How do you give a thread a meaningful name?
3. How do you wait for a thread to finish?
4. What is `Thread.currentThread()`?

### Expected Behavior

- `createAndStartThread` creates and starts a thread that executes the given task
- `createNamedThread` creates a thread with the specified name
- `runAndWait` starts a thread and blocks until it completes
- `demonstrateStartVsRun` shows the difference between start() and run()

### Hints

- `new Thread(runnable).start()` is the basic pattern
- `thread.setName()` or the `Thread(Runnable, String)` constructor sets the name
- `thread.join()` blocks until the thread completes

### Documentation to Review

- [Thread](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html)
- [Runnable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Runnable.html)

### Interview Questions

1. What is the difference between `start()` and `run()`?
2. What happens if you call `start()` twice on the same Thread?
3. Can a thread be restarted after it finishes?
4. What does `Thread.currentThread()` return?

---

## Exercise 02 — Thread Properties

### Objective

Explore thread properties: name, ID, priority, daemon status, and alive state.

### Scenario

You're building a monitoring system where you need to inspect and configure thread properties before submitting work.

### Requirements

- Access thread name, ID, and priority
- Set a thread as a daemon thread
- Check if a thread is alive
- Query thread state

### Before You Code

1. What is the default priority of a thread?
2. What is a daemon thread?
3. What happens to daemon threads when all non-daemon threads finish?
4. What thread states exist?

### Hints

- `thread.getName()`, `thread.getId()`, `thread.getPriority()`
- `thread.setDaemon(true)` — must be called before `start()`
- `thread.getState()` returns a `Thread.State` enum value

### Documentation to Review

- [Thread](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html)
- [Thread.State](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang.Thread.State.html)

### Interview Questions

1. What is the difference between a daemon and a user thread?
2. Can you set a thread's priority after starting it?
3. What thread state is a thread in after calling `start()` but before the JVM schedules it?
4. Why might you use daemon threads for background cleanup?

---

## Exercise 03 — Thread Lifecycle

### Objective

Observe and understand all thread lifecycle states.

### Scenario

You're debugging a concurrent application and need to identify why threads are stuck. Understanding thread states is essential.

### Requirements

- Demonstrate the NEW state
- Demonstrate the RUNNABLE state
- Demonstrate the TIMED_WAITING state
- Demonstrate the WAITING state
- Demonstrate the TERMINATED state

### Before You Code

1. What transitions cause a thread to move between states?
2. What is the difference between WAITING and TIMED_WAITING?
3. What is the difference between RUNNABLE and actually running on a CPU?
4. How do you detect a blocked thread?

### Expected Behavior

The methods should return thread states at specific moments to demonstrate the lifecycle.

### Hints

- `Thread.sleep()` causes TIMED_WAITING
- `Object.wait()` causes WAITING
- A thread is TERMINATED after `run()` completes
- `thread.getState()` queries the current state

### Documentation to Review

- [Thread.State](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang.Thread.State.html)

### Interview Questions

1. List all thread states and their meanings.
2. What causes a thread to transition from RUNNABLE to BLOCKED?
3. What is the difference between BLOCKED and WAITING?
4. Can a thread return to RUNNABLE after being in WAITING state?

---

## Exercise 04 — Joining Threads

### Objective

Practice waiting for thread completion using `join()`.

### Scenario

You're building a report generator that processes data in parallel and must wait for all processing threads to finish before generating the final report.

### Requirements

- Wait for a single thread to complete
- Wait for multiple threads with a timeout
- Understand how `join()` differs from `sleep()`

### Before You Code

1. What happens if you call `join()` on a thread that hasn't started?
2. What happens if you call `join()` on a thread that has already finished?
3. What is the difference between `join()` and `sleep()`?
4. How do you avoid waiting forever?

### Expected Behavior

- `joinAll` should block until all provided threads complete
- `joinWithTimeout` should return even if threads haven't finished

### Hints

- `thread.join()` blocks until the thread terminates
- `thread.join(timeout, nanos)` has a maximum wait time
- Use a loop to join multiple threads

### Documentation to Review

- [Thread.join()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang.Thread.html#join())
- [Thread.sleep()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang.Thread.html#sleep(long))

### Interview Questions

1. What is the difference between `Thread.sleep()` and `Thread.join()`?
2. Can `join()` throw an `InterruptedException`? What should you do when it does?
3. When might you use `join()` with a timeout?
4. Is there a way to wait for any one of multiple threads without polling?
