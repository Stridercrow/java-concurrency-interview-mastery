package com.concurrency.mastery.interviewexercises.paymentvalidation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * PaymentReaderService — SKELETON (implement all methods)
 *
 * Reads and parses payment details to construct a PaymentInfo object.
 * Implements AutoCloseable for use in try-with-resources.
 *
 * Must handle:
 * - Minimum 5 input lines (otherwise MissingPaymentDetailException)
 * - Date in dd-MM-yyyy format (otherwise InvalidDateFormatException)
 * - Amount as valid double (otherwise InvalidAmountFormatException)
 */
class PaymentReaderService implements AutoCloseable {
    private final BufferedWriter writer;
    // TODO: Define any constants you need (e.g., DateTimeFormatter)

    public PaymentReaderService(BufferedWriter writer) {
        this.writer = writer;
        // TODO: Initialize any fields
    }

    /**
     * Parse the input list into a PaymentInfo object.
     *
     * Steps:
     * 1. Check inputs has at least 5 lines → MissingPaymentDetailException
     * 2. Parse date (index 0) as dd-MM-yyyy → InvalidDateFormatException
     * 3. Parse amount (index 1) as double → InvalidAmountFormatException
     * 4. Extract payer account (index 2)
     * 5. Extract receiver account (index 3)
     * 6. Extract purpose (index 4)
     * 7. Return new PaymentInfo(...)
     */
    public PaymentInfo read(List<String> inputs) throws Exception {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Close the resource and log cleanup message.
     * Must print: "PaymentReaderService. Resource closed"
     */
    @Override
    public void close() throws IOException {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
