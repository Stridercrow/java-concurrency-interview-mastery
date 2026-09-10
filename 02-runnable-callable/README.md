# Section 02 — Runnable vs Callable

## Overview

This section focuses on the two core task abstractions in Java concurrency: `Runnable` and `Callable`. Understanding when to use each is fundamental.

---

## Exercise 05 — Runnable Task

### Objective

Practice implementing Runnable tasks that perform work without returning a value.

### Scenario

You're building a log processing system. Each log entry is processed by a task that writes results to a shared output list.

### Before You Code

1. What is the signature of `Runnable.run()`?
2. Can a Runnable throw checked exceptions?
3. How do you get a result out of a Runnable task?

### Hints

- `Runnable.run()` returns `void`
- Use a shared collection to collect results from Runnable tasks
- Wrap checked exceptions in RuntimeException if needed

### Interview Questions

1. What is `Runnable`?
2. Can `Runnable` return a value?
3. Can `Runnable` throw checked exceptions?
4. When would you choose Runnable over Callable?

---

## Exercise 06 — Callable Task

### Objective

Practice implementing Callable tasks that return values.

### Scenario

You need to compute results concurrently — parsing, calculating, and aggregating data.

### Before You Code

1. What is the signature of `Callable.call()`?
2. What type parameter does Callable use?
3. How do you retrieve a Callable's result?
4. What happens if a Callable throws an exception?

### Hints

- `Callable<V>` returns type `V` from `call()`
- Submit Callables via `ExecutorService.submit()` to get a `Future<V>`
- `Future.get()` blocks and returns the result (or throws ExecutionException)

### Interview Questions

1. What is `Callable`?
2. How does `Callable` differ from `Runnable`?
3. What is `Future` and how do you get a result from it?
4. What exception does `Future.get()` throw if the Callable threw an exception?

---

## Exercise 07 — Choosing Runnable vs Callable

### Objective

Decide which abstraction fits different scenarios and implement accordingly.

### Scenario

You're designing task abstractions for a data processing pipeline with various task types.

### Before You Code

1. For each scenario, ask: "Does this task need to return a value?"
2. For each scenario, ask: "Does this task need to throw a checked exception?"
3. If the answer to either is yes, use Callable.

### Expected Behavior

Each scenario should use the correct abstraction based on whether it needs a return value or can throw checked exceptions.

### Interview Questions

1. Name three scenarios where Runnable is appropriate.
2. Name three scenarios where Callable is appropriate.
3. Can you wrap a Callable as a Runnable? How?
4. What is `Executors.callable(Runnable, T)` used for?
