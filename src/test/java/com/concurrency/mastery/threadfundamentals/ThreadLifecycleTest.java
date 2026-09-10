package com.concurrency.mastery.threadfundamentals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 03 — Thread Lifecycle")
class ThreadLifecycleTest {

    private ThreadLifecycle exercise;

    @BeforeEach
    void setUp() {
        exercise = new ThreadLifecycle();
    }

    @Test
    @DisplayName("Should observe NEW state")
    void shouldObserveNewState() {
        assertEquals(Thread.State.NEW, exercise.getNewThreadState());
    }

    @Test
    @DisplayName("Should observe TIMED_WAITING state when thread is sleeping")
    void shouldObserveTimedWaitingState() throws InterruptedException {
        Thread.State state = exercise.getSleepingThreadState(500);
        assertEquals(Thread.State.TIMED_WAITING, state,
            "A sleeping thread should be in TIMED_WAITING state");
    }

    @Test
    @DisplayName("Should observe WAITING state when thread is waiting")
    void shouldObserveWaitingState() throws InterruptedException {
        Object lock = new Object();
        Thread.State state = exercise.getWaitingThreadState(lock);
        assertEquals(Thread.State.WAITING, state,
            "A thread in Object.wait() should be in WAITING state");
    }

    @Test
    @DisplayName("Should observe TERMINATED state after thread completes")
    void shouldObserveTerminatedState() throws InterruptedException {
        assertEquals(Thread.State.TERMINATED, exercise.getTerminatedThreadState());
    }

    @Test
    @DisplayName("Should observe lifecycle states in order")
    void shouldObserveLifecycle() throws InterruptedException {
        Thread.State[] states = exercise.observeLifecycle();
        assertNotNull(states);
        assertTrue(states.length >= 2, "Should observe at least NEW and TERMINATED");

        assertEquals(Thread.State.NEW, states[0], "First state should be NEW");
        assertEquals(Thread.State.TERMINATED, states[states.length - 1],
            "Last state should be TERMINATED");
    }
}
