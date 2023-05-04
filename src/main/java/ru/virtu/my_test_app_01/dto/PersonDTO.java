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
public class PersonDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 symbols")
    private String username;

    @Min(value = 0, message = "Age should be more then 0")
    @Max(value = 100, message = "Age should be less then 100")
    private Short age;

    @Size(min = 5, max = 100, message = "Password should be from 5 to 200 symbols")
    private String password;
}
