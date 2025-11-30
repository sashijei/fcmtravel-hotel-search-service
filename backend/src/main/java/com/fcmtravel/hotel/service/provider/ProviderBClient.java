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
 * Second external provider client implementation.
 *
 * Works exactly like ProviderAClient but can return
 * different results, pricing or coverage areas.
 *
 * This demonstrates extensibility in multi-provider architecture.
 *
 * Used By:
 *  → HotelAggregationService
 */
@Component
public class ProviderBClient implements HotelProviderClient {

    @Override
    @Async("hotelTaskExecutor")
    public CompletableFuture<List<Hotel>> fetchHotelsAsync(HotelSearchRequest request) {
        List<Hotel> hotels = new ArrayList<>();

        hotels.add(Hotel.builder()
                .id("B-1")
                .name("Seaside Retreat")
                .hotelType(HotelType.FOUR_STAR)
                .roomType(RoomType.DELUXE)
                .city("Goa")
                .state("Goa")
                .basePricePerNight(6000)
                .rating(4.5)
                .build());

        hotels.add(Hotel.builder()
                .id("B-2")
                .name("Budget Inn")
                .hotelType(HotelType.BUDGET)
                .roomType(RoomType.STANDARD)
                .city("Bangalore")
                .state("Karnataka")
                .basePricePerNight(2000)
                .rating(3.9)
                .build());

        return new AsyncResult<>(hotels).completable();
    }
}
