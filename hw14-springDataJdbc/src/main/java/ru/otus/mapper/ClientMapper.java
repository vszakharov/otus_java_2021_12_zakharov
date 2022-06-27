package ru.otus.mapper;

import java.util.stream.Collectors;

import ru.otus.dto.AddressDto;
import ru.otus.dto.ClientDto;
import ru.otus.dto.PhoneDto;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientDto toDto(Client model) {
        var addressDto = new AddressDto(model.getAddress().getId(), model.getAddress().getStreet());
        var phonesDto = model.getPhones().stream()
                .map(phone -> new PhoneDto(phone.getId(), phone.getNumber()))
                .toList();

        return new ClientDto(
                model.getId(),
                model.getName(),
                addressDto,
                phonesDto
        );
    }

    public static Client toModel(ClientDto dto) {
        var address = new Address(dto.getAddress().getId(), dto.getAddress().getStreet());
        var phones = dto.getPhones().stream()
                .map(phone -> new Phone(phone.getId(), phone.getNumber()))
                .collect(Collectors.toSet());

        return new Client(
                dto.getId(),
                dto.getName(),
                address,
                phones
        );
    }
}
