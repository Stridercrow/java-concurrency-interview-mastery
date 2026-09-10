package com.concurrency.mastery.raceconditions;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 18 — Inventory Reservation
 *
 * Scenario:
 * An e-commerce system has inventory items. Multiple customers may try to
 * reserve items simultaneously. The system must prevent overselling:
 * if there are 10 items in stock, two threads reserving 7 and 5 must not
 * both succeed (that would require 12 items).
 *
 * The reservation must be atomic — check and decrement must happen together.
 */
public class InventoryReservation {

    private final AtomicInteger stock;
    private final String itemSku;

    public InventoryReservation(String itemSku, int initialStock) {
        this.itemSku = itemSku;
        this.stock = new AtomicInteger(initialStock);
    }

    public String getItemSku() {
        return itemSku;
    }

    public int getStock() {
        return stock.get();
    }

    /**
     * Try to reserve the requested quantity.
     *
     * TODO: Implement this using AtomicInteger to ensure the check-and-decrement
     * is atomic. Use compareAndSet in a loop (CAS loop pattern).
     *
     * @param quantity the number of items to reserve
     * @return true if reservation succeeded, false if insufficient stock
     */
    public boolean reserve(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        // TODO: Use a CAS loop to atomically check and decrement stock.
        // Read current stock, if >= quantity, try compareAndSet.
        // If CAS fails (another thread changed the value), retry.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Cancel a reservation and return stock.
     *
     * @param quantity the number of items to return
     */
    public void cancelReservation(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        // TODO: Atomically add the quantity back to stock
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the item SKU.
     */
    public String getSku() {
        return itemSku;
    }
}
