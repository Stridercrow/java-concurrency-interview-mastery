# Section 06 — Race Conditions

## Overview

Race conditions occur when multiple threads access shared mutable state without proper coordination. This section provides realistic scenarios: banking and inventory systems.

---

## Exercise 17 — Bank Account

### Objective

Implement thread-safe deposit, withdraw, and transfer operations.

### Scenario

A banking system needs concurrent-safe operations. Transfer involves two accounts and must be atomic.

### Before You Code

1. Why is `balance += amount` not atomic?
2. What happens if two threads deposit simultaneously?
3. Why does transfer require locking both accounts?
4. How do you prevent deadlock when locking two accounts?

### Interview Questions

1. Why is `balance += amount` not thread-safe?
2. What is the transfer problem and why is it tricky?
3. How do you prevent deadlock when transferring between two accounts?
4. What is lock ordering and how does it prevent deadlock?

---

## Exercise 18 — Inventory Reservation

### Objective

Implement atomic check-and-reserve using CAS (Compare-And-Swap).

### Scenario

An e-commerce system must prevent overselling when multiple customers reserve simultaneously.

### Before You Code

1. Why is "check then act" not atomic?
2. What is a CAS loop?
3. Why is AtomicInteger appropriate here?
4. What happens if two threads try to reserve the last item?

### Interview Questions

1. What is a check-then-act race condition?
2. What is CAS (Compare-And-Swap)?
3. What is the ABA problem?
4. When would you use a CAS loop vs synchronized?
