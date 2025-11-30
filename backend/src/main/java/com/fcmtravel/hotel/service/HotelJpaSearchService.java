package com.fcmtravel.hotel.service;

import com.fcmtravel.hotel.api.dto.HotelDto;
import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.api.dto.PagedResponse;
import com.fcmtravel.hotel.persistence.HotelEntity;
import com.fcmtravel.hotel.persistence.HotelRepository;
import com.fcmtravel.hotel.persistence.HotelSpecifications;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Hotel search implementation backed by MySQL using Spring Data JPA.
 *
 * Responsibilities:
 * - Apply dynamic filters based on HotelSpecifications
 * - Handle pagination + total record count
 * - Sorting by price/name/rating/hotelType using SortStrategyFactory
 *
 * Called From:
 *  → HotelController (/db)
 *
 * Ideal when data is locally stored instead of provider fetched.
 */
@Service
public class HotelJpaSearchService {

    private static final double SERVICE_CHARGE_PERCENT = 10.0;

    private final HotelRepository hotelRepository;

    public HotelJpaSearchService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public PagedResponse<HotelDto> search(HotelSearchRequest request) {
        var spec = HotelSpecifications.fromSearchRequest(request);

        Sort sort = buildSort(request.getSortBy(), request.getSortDirection());

        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() <= 0 ? 10 : request.getSize();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<HotelEntity> entityPage = hotelRepository.findAll(spec, pageable);

        List<HotelDto> dtos = entityPage.getContent().stream()
                .map(this::applyServiceChargeAndMap)
                .toList();

        return PagedResponse.<HotelDto>builder()
                .content(dtos)
                .page(entityPage.getNumber())
                .size(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .build();
    }

    private Sort buildSort(String sortBy, String direction) {
        String property;
        if (sortBy == null) {
            property = "finalPrice";
        } else {
            property = switch (sortBy.toLowerCase()) {
                case "price" -> "finalPrice";
                case "rating" -> "rating";
                case "name" -> "name";
                default -> "finalPrice";
            };
        }

        Sort sort = Sort.by(property);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        return sort;
    }

    private HotelDto applyServiceChargeAndMap(HotelEntity entity) {
        double finalPrice = entity.getBasePrice()
                + (entity.getBasePrice() * SERVICE_CHARGE_PERCENT / 100.0);

        entity.setFinalPrice(finalPrice);

        return HotelDto.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .hotelType(entity.getHotelType())
                .roomType(entity.getRoomType())
                .city(entity.getCity())
                .state(entity.getState())
                .finalPricePerNight(entity.getFinalPrice())
                .rating(entity.getRating())
                .build();
    }
}
