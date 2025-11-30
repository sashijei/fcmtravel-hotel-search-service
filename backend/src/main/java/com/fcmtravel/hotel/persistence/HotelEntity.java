package com.fcmtravel.hotel.persistence;

import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Hotel stored in MySQL.
 *
 * <p>This model stores core hotel attributes such as rating,
 * room type, hotel type, location (city/state) and pricing.
 * It maps to the database table <b>hotel</b> using JPA. </p>
 *
 * Features:
 * ✔ Stores hotel category (3/4/5 star)  
 * ✔ Stores room type (Standard/Deluxe/Suite)  
 * ✔ Stored in DB and served via REST API  
 */
@Entity
@Table(
        name = "hotel",
        indexes = {
                @Index(name = "idx_city_state", columnList = "city, state"),
                @Index(name = "idx_city_state_price", columnList = "city, state, final_price"),
                @Index(name = "idx_city_state_rating", columnList = "city, state, rating")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "hotel_type", nullable = false, length = 20)
    private HotelType hotelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false, length = 20)
    private RoomType roomType;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "base_price", nullable = false)
    private double basePrice;

    @Column(name = "final_price", nullable = false)
    private double finalPrice;

    @Column(nullable = false)
    private double rating;
}
