package ru.virtu.my_test_app_01.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class OccupantDTO {
    @NotEmpty(message = "Address shouldn't be empty")
    @Size(min = 2, max = 100, message = "Address should be from 2 to 100 symbols")
    private String username;
}
