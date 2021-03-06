package com.example.vehicle.dto.location;

import javax.persistence.*;

public class City {

    private Long id;

    private String value;

    public City() {
    }

    public City(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
