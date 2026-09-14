package com.concurrency.mastery.interviewexercises.paymentvalidation;

/**
 * Custom Exceptions — SKELETON (implement all four)
 *
 * Each exception class takes a specific parameter and includes it in the message.
 */

// TODO: Implement InvalidDateFormatException
// Constructor takes a String date parameter
// Message format: "Invalid date format for date: <date>"
class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException(String date) {
        // TODO: call super() with the formatted message
        super(null);
    }
}

// TODO: Implement InvalidAmountFormatException
// Constructor takes a String amount parameter
// Message format: "Invalid amount format for amount: <amount>"
class InvalidAmountFormatException extends Exception {
    public InvalidAmountFormatException(String amount) {
        // TODO: call super() with the formatted message
        super(null);
    }
}

// TODO: Implement MissingPaymentDetailException
// Constructor takes a String detail parameter
// Message format: "Missing payment detail: <detail>"
class MissingPaymentDetailException extends Exception {
    public MissingPaymentDetailException(String detail) {
        // TODO: call super() with the formatted message
        super(null);
    }
}

// TODO: Implement InvalidPaymentDetailsException
// Constructor takes a String message parameter
// Message format: "Invalid payment details: <message>"
class InvalidPaymentDetailsException extends Exception {
    public InvalidPaymentDetailsException(String message) {
        // TODO: call super() with the formatted message
        super(null);
    }
}
