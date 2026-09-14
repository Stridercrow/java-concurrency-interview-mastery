package com.concurrency.mastery.interviewexercises.paymentvalidation;

import java.time.LocalDate;

/**
 * PaymentInfo — PROVIDED (do not modify)
 *
 * Represents the details of a payment transaction.
 */
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
