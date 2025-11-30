package com.fcmtravel.hotel.api.dto;

import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * request payload capturing user search filters.
 *
 * Supports:
 *  • city, state filters  
 *  • hotel type(s)  
 *  • room type(s)  
 *  • sorting + pagination  
 *
 * Used by:
 *  → HotelService.searchHotelsFromDb()
 */
@Data
@Builder
public class HotelSearchRequest {
    private String city;
    private String state;
    private List<HotelType> hotelTypes;
    private List<RoomType> roomTypes;
    private String sortBy;
    private String sortDirection;
    private int page;
    private int size;
}
