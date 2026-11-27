package io.github.ezequiellima10.productservice.shared.dto;


import java.util.List;

public record ResponsePageDTO<T>(

        List<T> dtos,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages) {
}
