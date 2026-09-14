# Cryptocurrency Price Management System

**Difficulty:** Hard  
**Tags:** Java, MultiThread, Sorting, Synchronization, Observer Pattern  
**Time Limit:** 40 minutes  
**Test Cases:** 15

---

## Overview

Implement a system to manage cryptocurrency prices using the **Observer Design Pattern**. The system should allow for **concurrent updates** and maintain a history of all price changes for each cryptocurrency.

The system uses an **ExecutorService** to spawn multiple threads that concurrently execute price update commands.

## Architecture

```
Solution (main)
  ├── ExecutorService (concurrent command execution)
  ├── CryptoCurrencyManager
  │     ├── registerListener(listener)
  │     └── updatePrice(crypto, action, amount, time)
  └── CryptoCurrencyListenerImpl (×3: BTC, ETH, DOGECOIN)
        ├── increase(amount)
        ├── decrease(amount)
        ├── updatePrice(action, amount, time)
        ├── getCurrencyCode()
        ├── getPrice()
        └── getCommandsHistory()
```

## Input Format

- First line: integer `n` (number of commands)
- Next `n` lines: `<cryptoCurrency> <action> <amount>`
  - cryptoCurrency: `BTC`, `ETH`, or `DOGECOIN`
  - action: `increase` or `decrease`
  - amount: positive integer (1–1000)

## Classes to Implement

### CryptoCurrencyListenerImpl

| Method | Description |
|--------|-------------|
| `CryptoCurrencyListenerImpl(String cryptoCurrency)` | Initialize with currency code, price = 0 |
| `increase(int amount)` | Increase price by amount |
| `decrease(int amount)` | Decrease price by amount |
| `updatePrice(String action, int amount, LocalDateTime time)` | Update price and record with timestamp |
| `getCurrencyCode()` → `String` | Return currency code |
| `getPrice()` → `Integer` | Return current price |
| `getCommandsHistory()` → `List<String>` | Return sorted command history |

### CryptoCurrencyManager

| Method | Description |
|--------|-------------|
| `registerListener(CryptoCurrencyListener listener)` | Register a listener for notifications |
| `updatePrice(String crypto, String action, int amount, LocalDateTime time)` | Notify matching listener |

## Sample Cases

### Case 0
**Input:**
```
4
BTC increase 100
BTC increase 200
ETH increase 50
DOGECOIN increase 100
```
**Output:**
```
Current Price : BTC=300
History : [increase 100, increase 200]
Current Price : ETH=50
History : [increase 50]
Current Price : DOGECOIN=100
History : [increase 100]
```

### Case 1
**Input:**
```
4
BTC increase 100
BTC decrease 50
BTC decrease 50
DOGECOIN increase 100
```
**Output:**
```
Current Price : BTC=0
History : [increase 100, decrease 50, decrease 50]
Current Price : ETH=0
History : []
Current Price : DOGECOIN=100
History : [increase 100]
```

## Key Concepts

- **Observer Pattern:** Listeners register with a manager and receive notifications
- **ExecutorService:** Commands are executed concurrently via thread pool
- **Synchronization:** Single lock object for all mutable state — avoids redundant reentrant locking
- **Timestamped History:** Each change is recorded with a timestamp for ordering
- **Concurrent Notification:** Manager notifies the correct listener based on currency code

## Constraints

- `1 ≤ n ≤ 10`
- `0 < amount ≤ 1000`
- Valid currencies: `BTC`, `ETH`, `DOGECOIN`
- Valid actions: `increase`, `decrease`
