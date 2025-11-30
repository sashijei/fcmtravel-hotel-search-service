package com.fcmtravel.hotel.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hotel {

    private String id;
    private String name;
    private HotelType hotelType;
    private RoomType roomType;
    private String city;
    private String state;

    private double basePricePerNight;
    private double finalPricePerNight;
    private double rating;
}
