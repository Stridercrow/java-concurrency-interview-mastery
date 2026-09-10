package com.concurrency.mastery.runnablecallable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 07 — Choosing Runnable vs Callable")
class ChoosingTaskTypeTest {

    private ChoosingTaskType exercise;

    @BeforeEach
    void setUp() {
        exercise = new ChoosingTaskType();
    }

    @Test
    @DisplayName("Notification task should be Runnable (no return value)")
    void notificationTaskShouldBeRunnable() {
        List<String> notifications = new ArrayList<>();
        Object task = exercise.createNotificationTask("alice@example.com", notifications);

        assertInstanceOf(Runnable.class, task, "No return value needed → Runnable");
        ((Runnable) task).run();
        assertEquals(1, notifications.size());
        assertTrue(notifications.get(0).contains("alice@example.com"));
    }

    @Test
    @DisplayName("Tax calculator should be Callable (returns a value)")
    void taxCalculatorShouldBeCallable() throws Exception {
        Object task = exercise.createTaxCalculator(100.0, 0.08);

        assertInstanceOf(Callable.class, task, "Must return a value → Callable");
        double result = (double) ((Callable<?>) task).call();
        assertEquals(8.0, result, 0.001);
    }

    @Test
    @DisplayName("User validator should be Callable (returns a boolean)")
    void userValidatorShouldBeCallable() throws Exception {
        Object validTask = exercise.createUserValidator("Alice", 30);
        Object invalidTask = exercise.createUserValidator(null, 0);

        assertInstanceOf(Callable.class, validTask, "Must return a boolean → Callable");
        assertTrue((boolean) ((Callable<?>) validTask).call());
        assertFalse((boolean) ((Callable<?>) invalidTask).call());
    }

    @Test
    @DisplayName("Logger should be Runnable (no return value)")
    void loggerShouldBeRunnable() {
        List<String> logEntries = new ArrayList<>();
        Object task = exercise.createLogger("User logged in", logEntries);

        assertInstanceOf(Runnable.class, task, "No return value needed → Runnable");
        ((Runnable) task).run();
        assertEquals(1, logEntries.size());
        assertTrue(logEntries.get(0).contains("User logged in"));
    }

    @Test
    @DisplayName("JSON parser should be Callable (returns value or throws)")
    void jsonParserShouldBeCallable() throws Exception {
        Object validTask = exercise.createJsonParser("{\"key\":\"value\"}");
        Object invalidTask = exercise.createJsonParser("not json");

        assertInstanceOf(Callable.class, validTask, "Must return value or throw → Callable");

        // Valid JSON (starts with {, ends with })
        String result = (String) ((Callable<?>) validTask).call();
        assertEquals("{\"key\":\"value\"}", result);

        // Invalid JSON
        assertThrows(Exception.class, () -> ((Callable<?>) invalidTask).call());
    }
}
