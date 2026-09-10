package com.concurrency.mastery.runnablecallable;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * SOLUTION — Exercise 07: Choosing Runnable vs Callable
 *
 * Key Concepts:
 * - Use Runnable when: no return value needed, fire-and-forget tasks
 * - Use Callable when: need to return a value OR throw checked exceptions
 *
 * Decision Guide:
 * ┌─────────────────────┬──────────────────────────────────────────────┐
 * │ Scenario            │ Choice    │ Why                              │
 * ├─────────────────────┼───────────┼──────────────────────────────────┤
 * │ Fire-and-forget     │ Runnable  │ No return value needed           │
 * │ Compute a result    │ Callable  │ Must return value                │
 * │ May throw checked   │ Callable  │ Runnable cannot throw checked    │
 * │ exception           │           │ exceptions                       │
 * └─────────────────────┴───────────┴──────────────────────────────────┘
 */
public class ChoosingTaskTypeSolution {

    /**
     * Scenario 1: Send notification — fire-and-forget, no return value.
     * Runnable is correct because we only need a side effect (adding to list).
     */
    public Object createNotificationTask(String recipient, List<String> notifications) {
        return (Runnable) () -> notifications.add("Email sent to " + recipient);
    }

    /**
     * Scenario 2: Calculate tax — must return a double result.
     * Callable is correct because the caller needs the computed value.
     */
    public Object createTaxCalculator(double orderTotal, double taxRate) {
        return (Callable<Double>) () -> orderTotal * taxRate;
    }

    /**
     * Scenario 3: Validate user — must return a boolean result.
     * Callable is correct because the caller needs the validation result.
     */
    public Object createUserValidator(String name, int age) {
        return (Callable<Boolean>) () -> name != null && !name.isBlank() && age > 0;
    }

    /**
     * Scenario 4: Log message — fire-and-forget, no return value.
     * Runnable is correct because logging is a side effect operation.
     */
    public Object createLogger(String message, List<String> logEntries) {
        return (Runnable) () -> logEntries.add("[LOG] " + message);
    }

    /**
     * Scenario 5: Parse JSON — must return a value AND may throw checked exception.
     * Callable is the ONLY option because:
     * 1. It returns the parsed result
     * 2. It can declare checked exceptions in its signature
     *
     * Runnable cannot throw checked exceptions — this is the key differentiator.
     */
    public Object createJsonParser(String jsonString) {
        return (Callable<String>) () -> {
            if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON: must start with { and end with }");
            }
            return jsonString;
        };
    }
}
