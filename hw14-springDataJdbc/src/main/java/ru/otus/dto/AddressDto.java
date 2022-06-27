package ru.otus.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDto {

    private final Long id;

    private final String street;

    @JsonCreator
    public AddressDto(
            @JsonProperty("id") Long id,
            @JsonProperty("street") String street
    ) {
        this.id = id;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressDto addressDto = (AddressDto) o;
        return Objects.equals(id, addressDto.id) && Objects.equals(street, addressDto.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
