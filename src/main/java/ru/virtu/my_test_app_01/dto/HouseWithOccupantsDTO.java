package ru.virtu.my_test_app_01.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HouseWithOccupantsDTO {
    private Long id;

    @NotEmpty(message = "Address shouldn't be empty")
    @Size(min = 2, max = 100, message = "Address should be from 2 to 100 symbols")
    private String address;

    private OwnerDTO owner;

    private List<PersonDTO> personDTOList;

    public HouseWithOccupantsDTO(Long id, String address, OwnerDTO owner, List<PersonDTO> personDTOList) {
        this.id = id;
        this.address = address;
        this.owner = owner;
        this.personDTOList = personDTOList;
    }
}
