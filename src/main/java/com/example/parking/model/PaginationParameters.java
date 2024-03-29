package com.example.parking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationParameters {
    private Integer pageNumber;
    private Integer pageSize;
    private String sortParameter;
}
