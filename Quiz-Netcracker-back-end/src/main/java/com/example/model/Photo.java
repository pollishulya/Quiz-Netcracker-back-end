package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Photo {
    String photo;

    public Photo(String photo) {
        this.photo = photo;
    }
}
