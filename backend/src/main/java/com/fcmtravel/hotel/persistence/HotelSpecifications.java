package com.fcmtravel.hotel.persistence;

import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Static specification builder for dynamic Hotel filtering in JPA.
 *
 * <p>Generates JPA Criteria predicates for:</p>
 * - City
 * - State
 * - HotelType
 * - RoomType
 *
 * Used By:
 *  → HotelJpaSearchService for dynamic DB search
 *
 * This supports multiple optional search parameters combined seamlessly.
 */
public class HotelSpecifications {

    public static Specification<HotelEntity> fromSearchRequest(HotelSearchRequest request) {
        return Specification
                .where(cityEquals(request.getCity()))
                .and(stateEquals(request.getState()))
                .and(hotelTypeIn(request.getHotelTypes()))
                .and(roomTypeIn(request.getRoomTypes()));
    }

    private static Specification<HotelEntity> cityEquals(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("city")), city.toLowerCase());
        };
    }

    private static Specification<HotelEntity> stateEquals(String state) {
        return (root, query, cb) -> {
            if (state == null || state.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("state")), state.toLowerCase());
        };
    }

    private static Specification<HotelEntity> hotelTypeIn(List<HotelType> hotelTypes) {
        return (root, query, cb) -> {
            if (hotelTypes == null || hotelTypes.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("hotelType").in(hotelTypes);
        };
    }

    private static Specification<HotelEntity> roomTypeIn(List<RoomType> roomTypes) {
        return (root, query, cb) -> {
            if (roomTypes == null || roomTypes.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("roomType").in(roomTypes);
        };
    }
}
