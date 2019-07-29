package com.example.parking.dao;

import com.example.parking.model.entity.ParkingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, Integer> {
    ParkingEntity findByCode(String code);

    @Query(value = "SELECT debt FROM parking_payments WHERE code =?1", nativeQuery = true)
    Integer findDebtByCode(String code);

    Page<ParkingEntity> findAll(Pageable pageable);
}