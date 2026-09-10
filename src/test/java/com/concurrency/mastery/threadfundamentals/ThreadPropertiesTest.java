package com.concurrency.mastery.threadfundamentals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 02 — Thread Properties")
class ThreadPropertiesTest {

    private ThreadProperties exercise;

    @BeforeEach
    void setUp() {
        exercise = new ThreadProperties();
    }

    @Test
    @DisplayName("Should get thread name")
    void shouldGetThreadName() {
        Thread t = new Thread(() -> {}, "test-thread");
        assertEquals("test-thread", exercise.getName(t));
    }

    @Test
    @DisplayName("Should get thread ID")
    void shouldGetThreadId() {
        Thread t = new Thread(() -> {});
        assertTrue(exercise.getId(t) > 0);
    }

    @Test
    @DisplayName("Should get thread priority")
    void shouldGetThreadPriority() {
        Thread t = new Thread(() -> {});
        assertEquals(Thread.NORM_PRIORITY, exercise.getPriority(t));
    }

    @Test
    @DisplayName("Should create daemon thread")
    void shouldCreateDaemonThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = exercise.createDaemonThread(latch::countDown, "daemon-1");

        assertNotNull(t);
        assertTrue(t.isDaemon(), "Thread should be daemon");
        assertEquals("daemon-1", t.getName());
        assertFalse(t.isAlive(), "Thread should not be started yet");

        t.start();
        assertTrue(latch.await(2, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should report alive status correctly")
    void shouldReportAliveStatus() throws InterruptedException {
        Thread t = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        assertFalse(exercise.isAlive(t), "Thread should not be alive before start");

        t.start();
        assertTrue(exercise.isAlive(t), "Thread should be alive after start");

        t.join(2000);
        assertFalse(exercise.isAlive(t), "Thread should not be alive after completion");
    }

    @Test
    @DisplayName("Should get thread state")
    void shouldGetThreadState() throws InterruptedException {
        Thread t = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        assertEquals(Thread.State.NEW, exercise.getState(t));

        t.start();
        // Could be RUNNABLE or TIMED_WAITING depending on timing
        Thread.State state = exercise.getState(t);
        assertTrue(state == Thread.State.RUNNABLE || state == Thread.State.TIMED_WAITING,
            "State should be RUNNABLE or TIMED_WAITING");

        t.join(2000);
        assertEquals(Thread.State.TERMINATED, exercise.getState(t));
    }
}
