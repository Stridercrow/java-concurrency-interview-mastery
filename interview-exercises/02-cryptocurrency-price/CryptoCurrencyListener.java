package com.concurrency.mastery.interviewexercises.cryptocurrency;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CryptoCurrencyListener — PROVIDED (do not modify)
 *
 * Interface for receiving cryptocurrency price update notifications.
 */
interface CryptoCurrencyListener {
    void increase(int amount);
    void decrease(int amount);
    void updatePrice(String action, int amount, LocalDateTime currentTime);
    String getCurrencyCode();
    int getPrice();
    List<String> getCommandsHistory();
}
