package com.example.parking.controller;

import com.example.parking.model.PaginationParameters;
import com.example.parking.model.dto.ParkingDTO;
import com.example.parking.model.entity.ParkingEntity;
import com.example.parking.service.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Parking")
public class ParkingController {
    private ParkingService service;


    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @PostMapping
    public ParkingDTO registerParking() {
        return service.registerParking();
    }

    @GetMapping("/{code}")
    public Integer getDebt(@PathVariable String code) {
        return service.getDebt(code);
    }

    @PostMapping("/{code}/{amount}")
    public void billDebt(@PathVariable String code, @PathVariable Integer amount) {
        service.billDebt(code, amount);
    }

    @PostMapping("/sort")
    public Map<Integer, List<ParkingDTO>> getAll(@RequestBody PaginationParameters parameters) {
        return service.getAll(parameters);
    }
}