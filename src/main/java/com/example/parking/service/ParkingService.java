package com.example.parking.service;

import com.example.parking.dao.ParkingRepository;
import com.example.parking.exception.UnsuccessfulTransactionException;
import com.example.parking.model.PaginationParameters;
import com.example.parking.util.ParkingHelper;
import com.example.parking.model.entity.ParkingEntity;
import com.example.parking.model.dto.ParkingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.parking.model.Status.PAYED;
import static com.example.parking.model.Status.UNPAYED;

@Service
public class ParkingService {
    private final ParkingRepository repository;
    private final ParkingHelper helper;

    public ParkingService(ParkingRepository repository, ParkingHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    public ParkingDTO registerParking() {
        ParkingEntity parking = helper.buildParkingCheck();
        repository.save(parking);
        return helper.mapParkingEntityToParkingDTO(parking);
    }

    public Integer getDebt(String code) {
        ParkingEntity parking = repository.findByCode(code);
        parking.setDebt(helper.calculateDebt(parking));
        repository.save(parking);
        return repository.findDebtByCode(code);
    }

    public void billDebt(String code, Integer amount) {
        Integer debt = getDebt(code);
        if (debt == 0 && amount == 0) {
            ParkingEntity parking = repository.findByCode(code);
            parking.setStatus(PAYED);
            repository.save(parking);
        } else if (amount >= debt) {
            ParkingEntity parking = repository.findByCode(code);
            if (parking.getStatus() == UNPAYED) {
                parking.setStatus(PAYED);
                repository.save(parking);
            } else {
                throw new UnsuccessfulTransactionException("Check was payed");
            }
        } else throw new UnsuccessfulTransactionException("Amount less than debt");
    }

    public Map<Integer, List<ParkingDTO>> getAll(PaginationParameters parameters) {
        Pageable sortedParameter = PageRequest.of(parameters.getPageNumber(), parameters.getPageSize(),
                Sort.by(parameters.getSortParameter()).descending());

        Page<ParkingEntity> entities = repository.findAll(sortedParameter);
        int totalPage = entities.getTotalPages();
        Map<Integer, List<ParkingDTO>> page = new HashMap<>();

        page.put(totalPage, helper.mapParkingEntityListToParkingDTOList(entities.getContent()));

        return page;
    }
}