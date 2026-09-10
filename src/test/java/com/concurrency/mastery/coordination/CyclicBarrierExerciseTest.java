package com.concurrency.mastery.coordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 33 — CyclicBarrier")
class CyclicBarrierExerciseTest {

    private CyclicBarrierExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new CyclicBarrierExercise();
    }

    @Test
    @DisplayName("Should run phased computation")
    void shouldRunPhasedComputation() throws Exception {
        List<Boolean> results = exercise.runPhasedComputation(3, 2, phase -> {
            // no-op work per phase
        });

        assertNotNull(results);
        assertEquals(3, results.size());
        for (Boolean result : results) {
            assertTrue(result, "Each worker should complete all phases");
        }
    }

    @Test
    @DisplayName("Workers should synchronize at each phase boundary")
    void workersShouldSynchronize() throws Exception {
        AtomicInteger phaseCounter = new AtomicInteger(0);
        CopyOnWriteArrayList<Integer> observedPhases = new CopyOnWriteArrayList<>();

        List<Boolean> results = exercise.runPhasedComputation(3, 3, phase -> {
            observedPhases.add(phase);
        });

        assertEquals(3, results.size());
        // Each worker should have observed phases 0, 1, 2
        assertTrue(observedPhases.size() >= 9, "Should have at least 9 phase observations");
    }

    @Test
    @DisplayName("Should track maximum phase reached")
    void shouldTrackMaxPhase() throws Exception {
        int maxPhase = exercise.runWithTracking(3, 5);
        assertEquals(4, maxPhase, "All workers should reach phase 4 (0-indexed)");
    }
}
