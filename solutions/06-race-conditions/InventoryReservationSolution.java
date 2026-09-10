package com.concurrency.mastery.raceconditions;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SOLUTION — Exercise 18: Inventory Reservation
 *
 * Key Concepts:
 * - CAS (Compare-And-Swap) loop pattern
 * - Atomic check-and-decrement
 * - Why "check then act" is not atomic
 *
 * CAS Loop Pattern:
 * 1. Read current value
 * 2. Check if operation is valid
 * 3. Try compareAndSet(current, newValue)
 * 4. If CAS fails (another thread changed the value), retry
 */
public class InventoryReservationSolution {

    private final AtomicInteger stock;
    private final String itemSku;

    public InventoryReservationSolution(String itemSku, int initialStock) {
        this.itemSku = itemSku;
        this.stock = new AtomicInteger(initialStock);
    }

    public String getItemSku() { return itemSku; }
    public int getStock() { return stock.get(); }
    public String getSku() { return itemSku; }

    public boolean reserve(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        // CAS loop: keep trying until we succeed or determine we can't
        while (true) {
            int current = stock.get();
            if (current < quantity) {
                return false; // insufficient stock
            }
            if (stock.compareAndSet(current, current - quantity)) {
                return true; // reservation succeeded
            }
            // CAS failed — another thread changed the value, retry
        }
    }

    public void cancelReservation(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        stock.addAndGet(quantity);
    }
}
