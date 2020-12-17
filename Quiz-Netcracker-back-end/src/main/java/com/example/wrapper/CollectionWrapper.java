package com.example.wrapper;

import lombok.Data;

import java.util.Collection;

@Data
public class CollectionWrapper<T> {
    private final Collection<T> values;

    public int getCount() {
        return values.size();
    }
}
