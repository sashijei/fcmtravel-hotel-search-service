package com.fcmtravel.hotel.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Generic wrapper for paginated data sent to frontend.
 *
 * Returns result set along with:
 * <ul>
 *   <li>page number</li>
 *   <li>page size</li>
 *   <li>total result count</li>
 *   <li>total number of pages</li>
 * </ul>
 */
@Data
@Builder
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
