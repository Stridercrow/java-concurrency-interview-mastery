# Payment Validation and Resource Management

**Difficulty:** Hard  
**Tags:** Java, Exceptions, Resource Management, Try-with-Resources  
**Time Limit:** 40 minutes  
**Test Cases:** 16

---

## Overview

Implement a system to process and validate payment information. The goal is to ensure that the payment data is correct and that resources are managed (using try-with-resources) efficiently.

The system has three classes:
- **PaymentInfo** (provided) — Represents payment transaction details
- **PaymentReaderService** — Reads and parses payment details to construct a PaymentInfo object
- **PaymentValidationService** — Validates payment details and manages resources

## Input Format

The application receives a list of 5 string commands, where each line represents a specific detail of the payment:

1. Date of Payment (dd-mm-yyyy format)
2. Amount (positive number)
3. Payer Account Number
4. Receiver Account Number
5. Purpose of Payment

## Classes to Implement

### PaymentValidationService

| Method | Description |
|--------|-------------|
| `PaymentValidationService(BufferedWriter bw)` | Initializes with a writer for lifecycle logging |
| `validate(PaymentInfo payment)` | Validates payment details (see rules below) |
| `close()` | Prints "PaymentValidationService. Resource closed" |

**Validation Rules:**
- Amount must be > 0, otherwise throw `InvalidPaymentDetailsException`
- Payer and receiver accounts must not be empty and must differ, otherwise throw `InvalidPaymentDetailsException`
- Purpose must not be empty, otherwise throw `InvalidPaymentDetailsException`

### PaymentReaderService

| Method | Description |
|--------|-------------|
| `PaymentReaderService(BufferedWriter bw)` | Initializes with a writer for lifecycle logging |
| `read(List<String> inputs)` → `PaymentInfo` | Parses inputs into a PaymentInfo object |
| `close()` | Prints "PaymentReaderService. Resource closed" |

**Parsing Rules:**
- Must have at least 5 input lines, otherwise throw `MissingPaymentDetailException`
- Date must be in dd-mm-yyyy format, otherwise throw `InvalidDateFormatException`
- Amount must be a valid double, otherwise throw `InvalidAmountFormatException`

### Custom Exceptions

| Exception | Constructor | Example Message |
|-----------|------------|-----------------|
| `InvalidDateFormatException(String date)` | Takes invalid date | `"Invalid date format for date: 31-02-2024"` |
| `InvalidAmountFormatException(String amount)` | Takes invalid amount | `"Invalid amount format for amount: abc"` |
| `MissingPaymentDetailException(String detail)` | Takes missing detail | `"Missing payment detail: amount"` |
| `InvalidPaymentDetailsException(String message)` | Takes validation message | `"Invalid payment details: Amount must be greater than zero. Provided: 0"` |

## Sample Cases

### Case 0 — Invalid Date Format
**Input:**
```
2023/07/31
100.00
1234567890
0987654321
Payment for services
```
**Output:**
```
PaymentValidationService. Resource closed
PaymentReaderService. Resource closed
InvalidDateFormatException : Invalid date format for date: 2023/07/31
```

### Case 1 — Negative Amount
**Input:**
```
31-07-2023
-100.00
1234567890
0987654321
Payment for services
```
**Output:**
```
PaymentValidationService. Resource closed
PaymentReaderService. Resource closed
InvalidPaymentDetailsException : Invalid payment details: Amount must be greater than zero. Provided: -100.0
```

## Key Concepts

- **Try-with-resources:** Both services implement `AutoCloseable` and are used in try-with-resources blocks
- **Custom Exceptions:** Each validation failure has a specific exception type
- **Resource Cleanup:** Resources are closed automatically in reverse order of creation
- **Exception Propagation:** Exceptions bubble up to the caller for handling

## Constraints

- `n = 5` (exactly 5 input lines)
- `0 < length of each input ≤ 100`
