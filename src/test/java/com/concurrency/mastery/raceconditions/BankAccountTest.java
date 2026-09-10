package com.concurrency.mastery.raceconditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 17 — Bank Account")
class BankAccountTest {

    @Test
    @DisplayName("Unsafe deposit is not thread-safe")
    void unsafeDepositRaceCondition() throws InterruptedException {
        BankAccount account = new BankAccount("ACC-001", 0);
        int threadCount = 8;
        int depositsPerThread = 10_000;
        int expected = threadCount * depositsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < depositsPerThread; j++) {
                    account.depositUnsafe(1);
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        // Unsafe version likely loses some deposits
        System.out.println("Expected: " + expected + ", Actual: " + account.getBalance());
        // Don't assert failure — race conditions are timing-dependent
    }

    @Test
    @DisplayName("Thread-safe deposit should be correct")
    void threadSafeDeposit() throws InterruptedException {
        BankAccount account = new BankAccount("ACC-001", 0);
        int threadCount = 8;
        int depositsPerThread = 10_000;
        int expected = threadCount * depositsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < depositsPerThread; j++) {
                    account.deposit(1);
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, account.getBalance(),
            "Thread-safe deposit should produce exact count");
    }

    @Test
    @DisplayName("Thread-safe withdraw should be correct")
    void threadSafeWithdraw() throws InterruptedException {
        BankAccount account = new BankAccount("ACC-001", 100_000);
        int threadCount = 8;
        int withdrawalsPerThread = 10_000;
        int expected = 100_000 - (threadCount * withdrawalsPerThread);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < withdrawalsPerThread; j++) {
                    account.withdraw(1);
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, account.getBalance(),
            "Thread-safe withdraw should produce exact count");
    }

    @Test
    @DisplayName("Thread-safe transfer should preserve total balance")
    void threadSafeTransfer() throws InterruptedException {
        BankAccount accountA = new BankAccount("A", 50_000);
        BankAccount accountB = new BankAccount("B", 50_000);
        int totalInitial = accountA.getBalance() + accountB.getBalance();

        int threadCount = 4;
        int transfersPerThread = 10_000;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < transfersPerThread; j++) {
                    if (j % 2 == 0) {
                        accountA.transfer(accountB, 1);
                    } else {
                        accountB.transfer(accountA, 1);
                    }
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        int totalFinal = accountA.getBalance() + accountB.getBalance();
        assertEquals(totalInitial, totalFinal,
            "Total balance should be preserved after all transfers");
    }

    @Test
    @DisplayName("Transfer should not allow overdraft")
    void transferShouldNotAllowOverdraft() {
        BankAccount from = new BankAccount("FROM", 100);
        BankAccount to = new BankAccount("TO", 0);

        assertFalse(from.transfer(to, 200), "Transfer should fail with insufficient funds");
        assertEquals(100, from.getBalance());
        assertEquals(0, to.getBalance());
    }
}
