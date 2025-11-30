package com.fcmtravel.hotel.api;

import com.fcmtravel.hotel.api.dto.HotelDto;
import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.api.dto.PagedResponse;
import com.fcmtravel.hotel.domain.HotelType;
import com.fcmtravel.hotel.domain.RoomType;
import com.fcmtravel.hotel.service.HotelAggregationService;
import com.fcmtravel.hotel.service.HotelJpaSearchService;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing Hotel APIs for UI and integration.
 *
 * Available Endpoints:
 *
 * GET /api/hotels/db
 *    → Fetch paginated hotels from MySQL
 *
 * GET /api/hotels/aggregated
 *    → Async aggregation from multiple hotel providers (mock implemented)
 *
 * Response Format:
 *   PagedResponse<HotelDto>
 */
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelSearchController {

	private final HotelJpaSearchService jpaSearchService;
	private final HotelAggregationService aggregationService;

	public HotelSearchController(HotelJpaSearchService jpaSearchService,
			HotelAggregationService aggregationService) {
		this.jpaSearchService = jpaSearchService;
		this.aggregationService = aggregationService;
	}

	@GetMapping("/db")
	public PagedResponse<HotelDto> searchHotelsFromDb(
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String state,
			@RequestParam(required = false) List<HotelType> hotelTypes,
			@RequestParam(required = false) List<RoomType> roomTypes,
			@RequestParam(defaultValue = "price") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection,
			@RequestParam(defaultValue = "0") @Min(0) int page,
			@RequestParam(defaultValue = "10") @Min(1) int size
			) {
		HotelSearchRequest request = HotelSearchRequest.builder()
				.city(city)
				.state(state)
				.hotelTypes(hotelTypes)
				.roomTypes(roomTypes)
				.sortBy(sortBy)
				.sortDirection(sortDirection)
				.page(page)
				.size(size)
				.build();

		return jpaSearchService.search(request);
	}

	@GetMapping("/aggregated")
	public PagedResponse<HotelDto> searchHotelsFromAggregatedProviders(
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String state,
			@RequestParam(required = false) List<HotelType> hotelTypes,
			@RequestParam(required = false) List<RoomType> roomTypes,
			@RequestParam(defaultValue = "price") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection,
			@RequestParam(defaultValue = "0") @Min(0) int page,
			@RequestParam(defaultValue = "10") @Min(1) int size
			) {
		HotelSearchRequest request = HotelSearchRequest.builder()
				.city(city)
				.state(state)
				.hotelTypes(hotelTypes)
				.roomTypes(roomTypes)
				.sortBy(sortBy)
				.sortDirection(sortDirection)
				.page(page)
				.size(size)
				.build();

		return aggregationService.search(request);
	}
}
