package com.fcmtravel.hotel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for performing DB operations on Hotel.
 *
 * Extends:
 *  - JpaRepository → CRUD + Pagination support
 *  - JpaSpecificationExecutor → dynamic filtering (city/state/type etc.)
 *
 * This is used by the HotelService to fetch paginated results.
 */
public interface HotelRepository extends JpaRepository<HotelEntity, Long>, JpaSpecificationExecutor<HotelEntity> {
}
