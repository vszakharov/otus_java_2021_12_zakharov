package ru.otus.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneDto {

    private final Long id;

    private final String number;

    @JsonCreator
    public PhoneDto(
            @JsonProperty("id") Long id,
            @JsonProperty("number") String number
    ) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneDto phoneDto = (PhoneDto) o;
        return Objects.equals(id, phoneDto.id) && Objects.equals(number, phoneDto.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "PhoneDto{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
