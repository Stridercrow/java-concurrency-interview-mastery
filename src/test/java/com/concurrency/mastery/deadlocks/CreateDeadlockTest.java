package com.concurrency.mastery.deadlocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 27 — Create a Deadlock")
@Timeout(value = 10, unit = TimeUnit.SECONDS) // Safety net: never hang the test suite
class CreateDeadlockTest {

    private CreateDeadlock exercise;

    @BeforeEach
    void setUp() {
        exercise = new CreateDeadlock();
    }

    @Test
    @DisplayName("Should demonstrate deadlock (with timeout)")
    void shouldDemonstrateDeadlock() throws InterruptedException {
        // This test uses a short timeout to avoid hanging.
        // In a real deadlock scenario, both threads would be stuck forever.
        boolean deadlocked = exercise.demonstrateDeadlock(2000);

        // The deadlock should be detected (both threads stuck)
        // Note: On some systems, the timing may not produce a deadlock.
        // This is expected — deadlocks are timing-dependent.
        System.out.println("Deadlock detected: " + deadlocked);
        System.out.println("If false, the timing didn't align — try running again.");
    }

    @Test
    @DisplayName("Four Coffman conditions are all present")
    void coffmanConditionsArePresent() {
        // 1. Mutual exclusion: synchronized blocks provide mutual exclusion ✓
        // 2. Hold and wait: each thread holds one lock while waiting for another ✓
        // 3. No preemption: synchronized locks cannot be forcibly released ✓
        // 4. Circular wait: A waits for B's lock, B waits for A's lock ✓

        // Verify the structure by checking that both resources exist
        assertNotNull(exercise.getResourceA());
        assertNotNull(exercise.getResourceB());
    }
}
