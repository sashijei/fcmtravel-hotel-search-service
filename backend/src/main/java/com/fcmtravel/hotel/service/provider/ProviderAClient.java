package com.fcmtravel.hotel.service.provider;

import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.domain.Hotel;
import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Sample mock provider integration implementation.
 *
 * <p>This class simulates calling a real external hotel API
 * (e.g., Booking.com, Agoda, MakeMyTrip) asynchronously.</p>
 *
 * Responsibilities:
 * - Fetch hotel listings
 * - Simulate network delay
 * - Map response → HotelDto
 *
 * Used By:
 *  → HotelAggregationService for parallel aggregation
 */
@Component
public class ProviderAClient implements HotelProviderClient {

    @Override
    @Async("hotelTaskExecutor")
    public CompletableFuture<List<Hotel>> fetchHotelsAsync(HotelSearchRequest request) {
        List<Hotel> hotels = new ArrayList<>();

        hotels.add(Hotel.builder()
                .id("A-1")
                .name("Grand Palace")
                .hotelType(HotelType.FIVE_STAR)
                .roomType(RoomType.SUITE)
                .city("Bangalore")
                .state("Karnataka")
                .basePricePerNight(8000)
                .rating(4.7)
                .build());

        hotels.add(Hotel.builder()
                .id("A-2")
                .name("City Comfort")
                .hotelType(HotelType.THREE_STAR)
                .roomType(RoomType.STANDARD)
                .city("Chennai")
                .state("Tamil Nadu")
                .basePricePerNight(3500)
                .rating(4.1)
                .build());

        return new AsyncResult<>(hotels).completable();
    }
}
