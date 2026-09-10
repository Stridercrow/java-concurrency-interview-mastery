# Java Concurrency Interview Mastery

## Purpose

This repository develops **practical Java concurrency skills** and **interview-level reasoning**. It is an interview rehearsal laboratory, not an exhaustive `java.util.concurrent` reference.

After completing these exercises, you will be able to look at a concurrency problem and identify whether the issue is task execution, lifecycle, shared state, visibility, atomicity, mutual exclusion, coordination, cancellation, or deadlock — and choose an appropriate solution while explaining why.

## Prerequisites

This repository assumes you already know:

- Java classes and interfaces
- Exceptions (checked and unchecked)
- Collections (List, Map, Set)
- Generics
- Lambdas and functional interfaces
- Basic streams
- Basic Maven
- Basic JUnit 5

## How to Practice

```
1.  Read the exercise README
2.  Do NOT look at the solution
3.  Answer the "Before You Code" questions (write them down)
4.  Implement the TODOs in the source code
5.  Run the tests: mvn test
6.  Observe concurrency behavior
7.  Review the documentation references
8.  Compare your implementation with the solution
9.  Answer the interview questions aloud
```

## Important Principle

> **Do not reach for `synchronized` automatically.**

Before writing any synchronization, ask yourself:

| Question | Implication |
|---|---|
| Is the state shared? | If not, synchronization is unnecessary |
| Is the state mutable? | Immutable state needs no synchronization |
| Can the work be partitioned? | Independent workers avoid contention |
| Can state be made local? | Thread-local state is inherently safe |
| Is visibility required? | `volatile` may suffice |
| Is atomicity required? | `Atomic*` classes may suffice |
| Is mutual exclusion required? | `synchronized` or `Lock` is appropriate |
| Is coordination required? | Latches, barriers, queues, or `CompletableFuture` |

## Repository Structure

```
java-concurrency-interview-mastery/
├── README.md
├── CONCURRENCY-CHEATSHEET.md
├── PROGRESS.md
├── DETAILED_SOLUTIONS.md
├── pom.xml
│
├── 01-thread-fundamentals/        (exercise READMEs)
├── 02-runnable-callable/
├── 03-executors/
├── 04-thread-pools/
├── 05-shared-state/
├── 06-race-conditions/
├── 07-synchronization/
├── 08-volatile-memory-visibility/
├── 09-atomic-operations/
├── 10-locks/
├── 11-deadlocks/
├── 12-concurrent-collections/
├── 13-blocking-queue/
├── 14-coordination/
├── 15-completable-future/
├── 16-real-world-concurrency/
├── 17-interview-challenges/
├── 18-virtual-threads/
│
├── src/main/java/                  (exercise source code)
├── src/test/java/                  (exercise tests)
│
└── solutions/                      (completed solutions + explanations)
```

## Concurrency ≠ Parallelism

These are not the same thing:

- **Concurrency** is about dealing with multiple things at once — structuring a program to handle multiple in-flight activities.
- **Parallelism** is about doing multiple things at once — executing computations simultaneously.

Java concurrency skills apply regardless of whether the JVM actually runs threads in parallel.

## Key Distinctions to Remember

- `volatile` ≠ mutual exclusion
- `AtomicInteger` ≠ the right choice for all thread-safe counters
- `synchronized` ≠ "make everything thread-safe"
- More threads ≠ better performance
- Thread ≠ Task
- Executor ≠ ExecutorService
- Future ≠ CompletableFuture
- Shared state ≠ always necessary
- Thread-safe ≠ locked everywhere

## Running

```bash
# Compile and run all tests
mvn test

# Run a specific test
mvn test -Dtest=com.concurrency.mastery.threadfundamentals.Exercise01CreatingThreadsTest

# Clean build
mvn clean test
```

## License

For personal interview preparation use.
