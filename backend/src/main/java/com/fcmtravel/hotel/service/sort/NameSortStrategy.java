package com.fcmtravel.hotel.service.sort;

import com.fcmtravel.hotel.domain.Hotel;

import java.util.Comparator;

/**
 * Sorting strategy for alphabetical sorting based on Hotel name.
 *
 * Useful for UI listing or directory-style browsing.
 *
 * Selected By:
 *  → SortStrategyFactory if sortBy="name"
 */
public class NameSortStrategy implements SortStrategy {
    @Override
    public Comparator<Hotel> getComparator(String direction) {
        Comparator<Hotel> comparator =
                Comparator.comparing(Hotel::getName, String.CASE_INSENSITIVE_ORDER);
        return "desc".equalsIgnoreCase(direction) ? comparator.reversed() : comparator;
    }
}
