package com.fcmtravel.hotel.service.sort;

import com.fcmtravel.hotel.domain.Hotel;

import java.util.Comparator;

/**
 * Sorting strategy that orders hotels based on user rating score.
 *
 * ascending = lowest rated first
 * descending = highest rated first
 *
 * Used For:
 *  "Best rated hotels first" type UI sorting filter
 */
public class RatingSortStrategy implements SortStrategy {
    @Override
    public Comparator<Hotel> getComparator(String direction) {
        Comparator<Hotel> comparator =
                Comparator.comparingDouble(Hotel::getRating);
        return "desc".equalsIgnoreCase(direction) ? comparator.reversed() : comparator;
    }
}
