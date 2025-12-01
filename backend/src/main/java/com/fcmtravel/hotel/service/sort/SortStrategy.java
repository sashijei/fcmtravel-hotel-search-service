package com.fcmtravel.hotel.service.sort;

import com.fcmtravel.hotel.domain.Hotel;

import java.util.Comparator;

/**
 * Strategy pattern
 * 
 * Strategy Interface for sorting Hotel list by dynamic field.
 *
 * Implementations:
 *  → PriceSortStrategy
 *  → RatingSortStrategy
 *  → NameSortStrategy
 *
 * Factory Decision:
 *  → SortStrategyFactory returns appropriate implementation
 *
 * Benefits:
 * - Open/Closed principle
 * - Easily extend for new sorting fields
 */
public interface SortStrategy {
    Comparator<Hotel> getComparator(String direction);
}
