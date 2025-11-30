package com.fcmtravel.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fcmtravel.hotel.api.dto.HotelDto;
import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.api.dto.PagedResponse;
import com.fcmtravel.hotel.domain.Hotel;
import com.fcmtravel.hotel.service.provider.HotelProviderClient;
import com.fcmtravel.hotel.service.sort.SortStrategy;
import com.fcmtravel.hotel.service.sort.SortStrategyFactory;

/**
 * Service layer that merges asynchronous results from multiple external
 * hotel providers and returns a combined unified response.
 *
 * Responsibilities:
 * - Trigger async provider requests in parallel
 * - Merge & de-duplicate results
 * - Normalize pricing/rating before returning to UI
 *
 * Called By:
 *  → HotelController (/aggregated)
 *
 * Extension Points:
 * - Add more provider client implementations without modifying this class
 */
@Service
public class HotelAggregationService {

    private static final double SERVICE_CHARGE_PERCENT = 10.0;

    private final List<HotelProviderClient> providerClients;

    public HotelAggregationService(List<HotelProviderClient> providerClients) {
        this.providerClients = providerClients;
    }

    public PagedResponse<HotelDto> search(HotelSearchRequest request) {
        List<CompletableFuture<List<Hotel>>> futures = providerClients.stream()
                .map(client -> client.fetchHotelsAsync(request))
                .toList();

        CompletableFuture<Void> allOf =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allOf.join();

        List<Hotel> allHotels = new ArrayList<>();
        for (CompletableFuture<List<Hotel>> future : futures) {
            allHotels.addAll(future.join());
        }

        allHotels.forEach(h -> {
            double finalPrice = h.getBasePricePerNight()
                    + (h.getBasePricePerNight() * SERVICE_CHARGE_PERCENT / 100.0);
            h.setFinalPricePerNight(finalPrice);
        });

        List<Hotel> filtered = allHotels.stream()
                .filter(h -> request.getCity() == null
                        || h.getCity().equalsIgnoreCase(request.getCity()))
                .filter(h -> request.getState() == null
                        || h.getState().equalsIgnoreCase(request.getState()))
                .filter(h -> request.getHotelTypes() == null
                        || request.getHotelTypes().contains(h.getHotelType()))
                .filter(h -> request.getRoomTypes() == null
                        || request.getRoomTypes().contains(h.getRoomType()))
                .toList();

        SortStrategy sortStrategy = SortStrategyFactory.getStrategy(request.getSortBy());
        filtered = filtered.stream()
                .sorted(sortStrategy.getComparator(request.getSortDirection()))
                .toList();

        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() <= 0 ? 10 : request.getSize();
        int total = filtered.size();
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min(fromIndex + size, total);

        List<Hotel> pageContent = filtered.subList(fromIndex, toIndex);

        List<HotelDto> dtos = pageContent.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) total / size);

        return PagedResponse.<HotelDto>builder()
                .content(dtos)
                .page(page)
                .size(size)
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }

    private HotelDto toDto(Hotel h) {
        return HotelDto.builder()
                .id(h.getId())
                .name(h.getName())
                .hotelType(h.getHotelType())
                .roomType(h.getRoomType())
                .city(h.getCity())
                .state(h.getState())
                .finalPricePerNight(h.getFinalPricePerNight())
                .rating(h.getRating())
                .build();
    }
}
