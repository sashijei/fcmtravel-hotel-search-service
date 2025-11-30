package com.fcmtravel.hotel.service.sort;

import com.fcmtravel.hotel.domain.Hotel;

import java.util.Comparator;

/**
 * Sorting strategy for arranging hotels by price per night.
 *
 * Usage:
 *  ascending = lowest price first
 *  descending = highest price first
 *
 * Selected By:
 *  → SortStrategyFactory when request sortBy = "price"
 */
public class PriceSortStrategy implements SortStrategy {
    @Override
    public Comparator<Hotel> getComparator(String direction) {
        Comparator<Hotel> comparator =
                Comparator.comparingDouble(Hotel::getFinalPricePerNight);
        return "desc".equalsIgnoreCase(direction) ? comparator.reversed() : comparator;
    }
}
