package com.concurrency.mastery.interviewexercises.paymentvalidation;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * PaymentValidationService — SKELETON (implement all methods)
 *
 * Validates payment details and manages resources.
 * Implements AutoCloseable for use in try-with-resources.
 *
 * Validation rules:
 * - Amount must be > 0 → InvalidPaymentDetailsException
 * - Payer and receiver accounts must not be empty and must differ → InvalidPaymentDetailsException
 * - Purpose must not be empty → InvalidPaymentDetailsException
 */
class PaymentValidationService implements AutoCloseable {
    private final BufferedWriter writer;

    public PaymentValidationService(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * Validate the payment object against all business rules.
     *
     * Checks:
     * 1. payment.getAmount() > 0
     *    → "Amount must be greater than zero. Provided: <amount>"
     * 2. payer and receiver accounts not empty and not equal
     *    → "Payer and receiver accounts are invalid. Payer: <payer>, Receiver: <receiver>"
     * 3. purpose not empty
     *    → "Purpose of payment is missing."
     */
    public void validate(PaymentInfo payment) throws InvalidPaymentDetailsException {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Close the resource and log cleanup message.
     * Must print: "PaymentValidationService. Resource closed"
     */
    @Override
    public void close() throws IOException {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
