package com.example.parking.util;

import com.example.parking.dao.ParkingRepository;
import com.example.parking.model.entity.ParkingEntity;

import com.example.parking.model.dto.ParkingDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.parking.model.Status.UNPAYED;

@Component
public class ParkingHelper {
    private ParkingRepository repository;

    public ParkingHelper(ParkingRepository repository) {
        this.repository = repository;
    }

    public ParkingEntity buildParkingCheck() {
        ParkingEntity parking = new ParkingEntity();
        parking.setCode(generateParkingCode());
        parking.setStatus(UNPAYED);
        return parking;
    }

    public String generateParkingCode() {
        int parkingCode = (int) (1 + Math.random() * 10000000);
        String code = Integer.valueOf(parkingCode).toString();
        while (repository.findByCode(code) != null) {
            return generateParkingCode();
        }
        return code;
    }

    public Integer calculateDebt(ParkingEntity parking) {
        Integer currentTime = LocalDateTime.now().getHour() * 60 + LocalDateTime.now().getMinute();
        Integer parkingTime = parking.getCreatedAt().getHour() * 60 + parking.getCreatedAt().getMinute();


        if (currentTime - parkingTime < 15) return 0;
        else if (currentTime - parkingTime < 60) return 1;
        else if (currentTime - parkingTime < 180) return 2;
        else if (currentTime - parkingTime < 300) return 3;
        else return 5;
    }

    public ParkingDTO mapParkingEntityToParkingDTO(ParkingEntity parking) {
        ParkingDTO dto = new ParkingDTO();
        dto.setCode(parking.getCode());
        dto.setCreatedAt(LocalDateTime.now());
        return dto;
    }

    public List<ParkingDTO> mapParkingEntityListToParkingDTOList(List<ParkingEntity> entities) {
        List<ParkingDTO> dtoList = new ArrayList<>();
        for (ParkingEntity p : entities) {
            ParkingDTO dto = new ParkingDTO();
            dto.setCode(p.getCode());
            dto.setCreatedAt(p.getCreatedAt());
            dtoList.add(dto);
        }
        return dtoList;
    }
}