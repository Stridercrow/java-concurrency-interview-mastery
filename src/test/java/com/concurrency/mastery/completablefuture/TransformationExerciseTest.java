package com.concurrency.mastery.completablefuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 35 — Transformation")
class TransformationExerciseTest {

    private TransformationExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new TransformationExercise();
    }

    @Test
    @DisplayName("Should apply transformation")
    void shouldApplyTransformation() throws Exception {
        CompletableFuture<String> input = CompletableFuture.completedFuture("hello");
        CompletableFuture<Integer> result = exercise.applyTransformation(input, String::length);

        assertEquals(5, result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should accept result")
    void shouldAcceptResult() throws Exception {
        AtomicReference<String> consumed = new AtomicReference<>();
        CompletableFuture<String> input = CompletableFuture.completedFuture("data");
        CompletableFuture<Void> result = exercise.acceptResult(input, consumed::set);

        result.get(5, TimeUnit.SECONDS);
        assertEquals("data", consumed.get());
    }

    @Test
    @DisplayName("Should run action after completion")
    void shouldRunAfterCompletion() throws Exception {
        AtomicBoolean ran = new AtomicBoolean(false);
        CompletableFuture<String> input = CompletableFuture.completedFuture("done");
        CompletableFuture<Void> result = exercise.runAfterCompletion(input, () -> ran.set(true));

        result.get(5, TimeUnit.SECONDS);
        assertTrue(ran.get());
    }

    @Test
    @DisplayName("Should chain transformations correctly")
    void shouldChainTransformations() throws Exception {
        CompletableFuture<Integer> input = CompletableFuture.completedFuture(5);
        CompletableFuture<String> result = exercise.chainTransformations(input);

        // 5 * 2 = 10, "10", "result 10"
        assertEquals("result 10", result.get(5, TimeUnit.SECONDS));
    }
}
