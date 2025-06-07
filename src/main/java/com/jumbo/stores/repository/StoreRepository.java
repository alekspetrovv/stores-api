package com.jumbo.stores.repository;

import com.jumbo.stores.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    @Query(value = "SELECT * FROM stores " +
            "ORDER BY ST_Distance(ST_MakePoint(longitude, latitude), ST_MakePoint(:userLongitude, :userLatitude)) " +
            "LIMIT :limit", nativeQuery = true)
    List<Store> findClosestStoresByDistance(
            @Param("userLatitude") double userLatitude,
            @Param("userLongitude") double userLongitude,
            @Param("limit") int limit);
}
