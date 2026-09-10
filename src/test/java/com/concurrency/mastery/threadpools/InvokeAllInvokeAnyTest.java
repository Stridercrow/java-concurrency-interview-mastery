package com.concurrency.mastery.threadpools;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 13 — invokeAll / invokeAny")
class InvokeAllInvokeAnyTest {

    private InvokeAllInvokeAny exercise;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        exercise = new InvokeAllInvokeAny();
        executor = Executors.newFixedThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Should get all results from invokeAll")
    void shouldGetAllResults() throws InterruptedException {
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int val = i;
            tasks.add(() -> val * 10);
        }

        List<Integer> results = exercise.getAllResults(executor, tasks);
        assertEquals(5, results.size());
        assertEquals(List.of(0, 10, 20, 30, 40), results);
    }

    @Test
    @DisplayName("Should get first result from invokeAny")
    void shouldGetFirstResult() throws InterruptedException, ExecutionException {
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(() -> { Thread.sleep(500); return "slow"; });
        tasks.add(() -> { Thread.sleep(10); return "fast"; });
        tasks.add(() -> { Thread.sleep(500); return "slow2"; });

        String result = exercise.getFirstResult(executor, tasks);
        assertNotNull(result);
        // The fast task should complete first
        assertEquals("fast", result);
    }

    @Test
    @DisplayName("Should respect timeout")
    void shouldRespectTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(() -> { Thread.sleep(10); return 1; });
        tasks.add(() -> { Thread.sleep(10); return 2; });
        tasks.add(() -> { Thread.sleep(5000); return 3; }); // very slow

        List<Integer> results = exercise.getResultsWithTimeout(executor, tasks, 200);
        // At least the fast tasks should be in results
        assertNotNull(results);
        assertTrue(results.size() >= 2, "Should have at least 2 results from fast tasks");
    }
}
