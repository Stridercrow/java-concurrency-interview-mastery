# Real-time Stock Price Monitoring System

**Difficulty:** Hard  
**Tags:** Java, Multithreading, ExecutorService, Thread Safety  
**Time Limit:** 40 minutes  
**Test Cases:** 15

---

## Overview

Implement a real-time stock price monitoring system that updates and logs multiple stock prices every second. The prices fluctuate based on a predefined percentage increase every second.

## Formula

```
UpdatedPrice = CurrentPrice + CurrentPrice * (percentageIncrease / 100)
```

**Important:** The price is rounded to 2 decimal places only when **logging**, NOT when updating. This preserves precision across updates.

## Architecture

```
Stock
  ├── name: String
  ├── price: double (current, unrounded)
  ├── percentageIncrease: double
  ├── updatePrice()     — applies percentage increase
  └── toString()        — "StockName: $RoundedPrice"

monitorStocks(instance, numberOfStocks, stocks) → List<String>
  ├── For each stock: stock.updatePrice()
  ├── Format: "Second X:" header + each stock on new line
  └── Return log lines
```

## Input Format

- First line: integer `n` (number of stocks)
- Next `n` lines: `<name> <initialPrice> <percentageIncrease>`
- Last line: integer `duration` (seconds to monitor)

## Classes to Implement

### Stock

| Field | Type | Description |
|-------|------|-------------|
| `name` | `String` | Stock ticker symbol |
| `price` | `double` | Current price (unrounded) |
| `percentageIncrease` | `double` | % increase per second |

| Method | Description |
|--------|-------------|
| `Stock(String name, double price, double pctIncrease)` | Constructor |
| `updatePrice()` | Apply percentage increase to current price |
| `toString()` | `"StockName: $RoundedPrice"` (2 decimal places) |

### monitorStocks()

| Parameter | Type | Description |
|-----------|------|-------------|
| `instance` | `int` | Current second number |
| `numberOfStocks` | `int` | Total stocks |
| `stocks` | `List<Stock>` | Stock objects |
| **Returns** | `List<String>` | Log lines for this second |

## Sample Cases

### Case 0
**Input:**
```
2
NFLX 250.0 3.0
DIS 180.0 1.8
4
```
**Output:**
```
Second 1:
NFLX: $257.50
DIS: $183.24
Second 2:
NFLX: $265.23
DIS: $186.54
Second 3:
NFLX: $273.18
DIS: $189.90
Second 4:
NFLX: $281.38
DIS: $193.31
```

**Calculation (NFLX, 4 seconds):**
| Second | Calculation | Unrounded | Logged |
|--------|------------|-----------|--------|
| 1 | 250 + 250×0.03 | 257.50 | $257.50 |
| 2 | 257.50 + 257.50×0.03 | 265.225 | $265.23 |
| 3 | 265.225 + 265.225×0.03 | 273.18175 | $273.18 |
| 4 | 273.18175 + 273.18175×0.03 | 281.3772025 | $281.38 |

### Case 1
**Input:**
```
3
MSFT 300.0 1.0
TSLA 400.0 2.0
GOOGL 500.0 1.5
3
```
**Output:**
```
Second 1:
MSFT: $303.00
TSLA: $408.00
GOOGL: $507.50
Second 2:
MSFT: $306.03
TSLA: $416.16
GOOGL: $515.11
Second 3:
MSFT: $309.09
TSLA: $424.48
GOOGL: $522.84
```

## Key Concepts

- **Thread Safety:** Multiple stocks updated concurrently
- **ExecutorService:** Manages thread pool for parallel stock updates
- **Decimal Precision:** Keep full precision during updates, round only for display
- **String Formatting:** `String.format("$%.2f", price)` for consistent formatting
- **Compound Growth:** Each update builds on the previous unrounded value

## Constraints

- `1 ≤ n ≤ 10`
- `1 ≤ stock price ≤ 5000`
- `0 ≤ percentage increase ≤ 100`
- `1 ≤ duration ≤ 10`
