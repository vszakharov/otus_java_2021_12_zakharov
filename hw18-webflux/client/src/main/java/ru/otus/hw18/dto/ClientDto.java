package ru.otus.hw18.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDto {

    private final Long id;

    private final String name;

    private final AddressDto address;

    private final List<PhoneDto> phones;

    @JsonCreator
    public ClientDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("address") AddressDto address,
            @JsonProperty("phones") List<PhoneDto> phones
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressDto getAddress() {
        return address;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
