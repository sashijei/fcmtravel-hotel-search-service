package com.fcmtravel.hotel.service.provider;

import com.fcmtravel.hotel.api.dto.HotelSearchRequest;
import com.fcmtravel.hotel.domain.Hotel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Common provider integration interface for fetching hotels from
 * external third-party hotel APIs.
 *
 * This is the abstraction for external providers. 
 * Every provider implementation returns a CompletableFuture<List<Hotel>> 
 * so that the aggregator can run them in parallel.
 *
 * Implementors should:
 * - Call remote API
 * - Convert response to HotelDto
 * - Return list of hotels asynchronously (CompletableFuture)
 *
 * Implemented By:
 *  → ProviderAClient
 *  → ProviderBClient
 *  → More providers can be plugged in easily
 */
public interface HotelProviderClient {
    CompletableFuture<List<Hotel>> fetchHotelsAsync(HotelSearchRequest request);
}
