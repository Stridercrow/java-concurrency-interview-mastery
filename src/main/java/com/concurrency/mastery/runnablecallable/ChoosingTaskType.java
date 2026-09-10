package com.concurrency.mastery.runnablecallable;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Exercise 07 — Choosing Runnable vs Callable
 *
 * Scenario:
 * You are designing concurrent task abstractions for a data processing pipeline.
 * For each scenario below, determine whether Runnable or Callable is appropriate
 * and implement the appropriate task.
 */
public class ChoosingTaskType {

    /**
     * Scenario 1: Send a notification email. No return value needed.
     * The task should add "Email sent to <recipient>" to the notifications list.
     *
     * @param recipient the email recipient
     * @param notifications list to record the action
     * @return the appropriate task type (Runnable or Callable)
     */
    public Object createNotificationTask(String recipient, List<String> notifications) {
        // TODO: Decide: Runnable or Callable? Implement accordingly.
        // Return type is Object to allow either. In a real system, use the correct interface.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Scenario 2: Calculate the tax for an order. Must return a double result.
     * Tax = orderTotal * taxRate.
     *
     * @param orderTotal the order total
     * @param taxRate the tax rate (e.g., 0.08 for 8%)
     * @return the appropriate task type
     */
    public Object createTaxCalculator(double orderTotal, double taxRate) {
        // TODO: Decide: Runnable or Callable? Implement accordingly.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Scenario 3: Validate a user record. Must return a boolean indicating validity.
     * A user is valid if their name is not null and not blank, and age > 0.
     *
     * @param name the user's name
     * @param age the user's age
     * @return the appropriate task type
     */
    public Object createUserValidator(String name, int age) {
        // TODO: Decide: Runnable or Callable? Implement accordingly.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Scenario 4: Log a message to a file. No return value needed.
     * The task should add "[LOG] <message>" to the log entries list.
     *
     * @param message the message to log
     * @param logEntries list to append to
     * @return the appropriate task type
     */
    public Object createLogger(String message, List<String> logEntries) {
        // TODO: Decide: Runnable or Callable? Implement accordingly.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Scenario 5: Parse a JSON string into an object. Must return the parsed object
     * OR throw an exception if parsing fails. Checked exception must be declared.
     *
     * @param jsonString the JSON string to parse
     * @return the appropriate task type
     */
    public Object createJsonParser(String jsonString) {
        // TODO: Decide: Runnable or Callable? Implement accordingly.
        // For this exercise, "parsing" just means validating that the string
        // starts with "{" and ends with "}" — return the string if valid, throw otherwise.
        throw new UnsupportedOperationException("Implement this method");
    }
}
