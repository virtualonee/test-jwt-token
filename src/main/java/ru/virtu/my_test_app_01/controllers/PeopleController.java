package ru.virtu.my_test_app_01.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.virtu.my_test_app_01.dto.PersonDTO;
import ru.virtu.my_test_app_01.dto.PersonDTO;
import ru.virtu.my_test_app_01.models.Person;
import ru.virtu.my_test_app_01.models.Person;
import ru.virtu.my_test_app_01.services.AdminService;
import ru.virtu.my_test_app_01.services.HousesService;
import ru.virtu.my_test_app_01.services.PeopleService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final AdminService adminService;
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(AdminService adminService, PeopleService peopleService, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getAllPeople() {
        return convertToPersonDTOList(peopleService.findAll());
    }

    @GetMapping("/{id}/get")
    public PersonDTO getPerson(@PathVariable Long id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO){

        Person person = convertToPerson(personDTO);
        peopleService.update(id, person);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity <HttpStatus> deletePerson(@PathVariable Long id) {
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    private List<PersonDTO> convertToPersonDTOList(List<Person> people){
        List<PersonDTO> personDTOList = new ArrayList<>();

        for (Person person:
                people) {
            personDTOList.add(convertToPersonDTO(person));
        }

        return personDTOList;
    }
}
