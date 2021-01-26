package com.example.model;

import lombok.AllArgsConstructor;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    private List<Game> games;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
