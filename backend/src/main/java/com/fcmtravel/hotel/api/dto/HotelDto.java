package com.fcmtravel.hotel.api.dto;

import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import lombok.Builder;
import lombok.Data;

/**
 * DTO used to send hotel response data to UI.
 *
 * <p>Contains only relevant fields displayed in UI:</p>
 * Name, Type, Room Type, City, State, Rating, Final Price per Night.
 *
 * Used in both Aggregated API & DB-backed API responses.
 */
@Data
@Builder
public class HotelDto {
    private String id;
    private String name;
    private HotelType hotelType;
    private RoomType roomType;
    private String city;
    private String state;
    private double finalPricePerNight;
    private double rating;
}
