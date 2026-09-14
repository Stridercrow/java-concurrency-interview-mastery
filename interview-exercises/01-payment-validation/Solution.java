package com.concurrency.mastery.interviewexercises.paymentvalidation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * SOLUTION — Payment Validation and Resource Management
 *
 * Key Concepts:
 * - Try-with-resources for automatic resource cleanup
 * - Custom exception hierarchy for specific validation failures
 * - AutoCloseable interface for resource management
 * - Defensive parsing with meaningful error messages
 *
 * Design Decisions:
 * 1. Each service manages its own lifecycle via close()
 * 2. Exceptions are specific (not generic) for better error handling
 * 3. Validation is split between reader (parsing) and validator (business rules)
 */

// ============================================================
// PaymentInfo (provided)
// ============================================================
class PaymentInfo {
    private final LocalDate date;
    private final double amount;
    private final String payerAccount;
    private final String receiverAccount;
    private final String purpose;

    public PaymentInfo(LocalDate date, double amount, String payerAccount,
                       String receiverAccount, String purpose) {
        this.date = date;
        this.amount = amount;
        this.payerAccount = payerAccount;
        this.receiverAccount = receiverAccount;
        this.purpose = purpose;
    }

    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getPayerAccount() { return payerAccount; }
    public String getReceiverAccount() { return receiverAccount; }
    public String getPurpose() { return purpose; }
}

// ============================================================
// Custom Exceptions
// ============================================================
class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException(String date) {
        super("Invalid date format for date: " + date);
    }
}

class InvalidAmountFormatException extends Exception {
    public InvalidAmountFormatException(String amount) {
        super("Invalid amount format for amount: " + amount);
    }
}

class MissingPaymentDetailException extends Exception {
    public MissingPaymentDetailException(String detail) {
        super("Missing payment detail: " + detail);
    }
}

class InvalidPaymentDetailsException extends Exception {
    public InvalidPaymentDetailsException(String message) {
        super("Invalid payment details: " + message);
    }
}

// ============================================================
// PaymentReaderService
// ============================================================
/**
 * Reads and parses payment details from a list of input strings.
 *
 * Implements AutoCloseable for use in try-with-resources.
 * The close() method logs when the resource is released.
 */
class PaymentReaderService implements AutoCloseable {
    private final BufferedWriter writer;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public PaymentReaderService(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * Parses the input list into a PaymentInfo object.
     *
     * Validation order matters:
     * 1. Check we have enough inputs (5 required)
     * 2. Parse date — may throw InvalidDateFormatException
     * 3. Parse amount — may throw InvalidAmountFormatException
     * 4. Remaining fields are strings, no parsing needed
     */
    public PaymentInfo read(List<String> inputs) throws Exception {
        if (inputs == null || inputs.size() < 5) {
            throw new MissingPaymentDetailException("Not enough input details provided.");
        }

        // Parse date (line 0)
        LocalDate date;
        try {
            date = LocalDate.parse(inputs.get(0), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(inputs.get(0));
        }

        // Parse amount (line 1)
        double amount;
        try {
            amount = Double.parseDouble(inputs.get(1));
        } catch (NumberFormatException e) {
            throw new InvalidAmountFormatException(inputs.get(1));
        }

        // Remaining fields are strings
        String payerAccount = inputs.get(2);
        String receiverAccount = inputs.get(3);
        String purpose = inputs.get(4);

        return new PaymentInfo(date, amount, payerAccount, receiverAccount, purpose);
    }

    /**
     * Closes the resource and logs the cleanup.
     * Called automatically by try-with-resources.
     */
    @Override
    public void close() throws IOException {
        writer.write("PaymentReaderService. Resource closed");
        writer.newLine();
    }
}

// ============================================================
// PaymentValidationService
// ============================================================
/**
 * Validates payment details and manages resources.
 *
 * Business rules enforced:
 * 1. Amount must be positive
 * 2. Payer and receiver accounts must differ and not be empty
 * 3. Purpose must be provided
 */
class PaymentValidationService implements AutoCloseable {
    private final BufferedWriter writer;

    public PaymentValidationService(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * Validates the payment object against business rules.
     *
     * Throws InvalidPaymentDetailsException for any rule violation.
     * The exception message includes the specific failure reason.
     */
    public void validate(PaymentInfo payment) throws InvalidPaymentDetailsException {
        // Rule 1: Amount must be positive
        if (payment.getAmount() <= 0) {
            throw new InvalidPaymentDetailsException(
                "Amount must be greater than zero. Provided: " + payment.getAmount());
        }

        // Rule 2: Accounts must not be empty and must differ
        if (payment.getPayerAccount() == null || payment.getPayerAccount().isEmpty() ||
            payment.getReceiverAccount() == null || payment.getReceiverAccount().isEmpty() ||
            payment.getPayerAccount().equals(payment.getReceiverAccount())) {
            throw new InvalidPaymentDetailsException(
                "Payer and receiver accounts are invalid. Payer: " +
                payment.getPayerAccount() + ", Receiver: " + payment.getReceiverAccount());
        }

        // Rule 3: Purpose must be provided
        if (payment.getPurpose() == null || payment.getPurpose().isEmpty()) {
            throw new InvalidPaymentDetailsException("Purpose of payment is missing.");
        }
    }

    @Override
    public void close() throws IOException {
        writer.write("PaymentValidationService. Resource closed");
        writer.newLine();
    }
}
