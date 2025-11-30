package com.fcmtravel.hotel.service.sort;

/**
 * Factory returning correct SortStrategy implementation
 * based on input filter (price/name/rating/hotelType future extension).
 *
 * Advantages:
 * - Extensible without modification (OCP)
 * - New strategies plug-in cleanly
 *
 * Used In:
 *  → HotelJpaSearchService
 *  → HotelAggregationService
 */
public class SortStrategyFactory {

    public static SortStrategy getStrategy(String sortBy) {
        if (sortBy == null) {
            return new PriceSortStrategy();
        }
        return switch (sortBy.toLowerCase()) {
            case "price" -> new PriceSortStrategy();
            case "rating" -> new RatingSortStrategy();
            case "name" -> new NameSortStrategy();
            default -> new PriceSortStrategy();
        };
    }
}
