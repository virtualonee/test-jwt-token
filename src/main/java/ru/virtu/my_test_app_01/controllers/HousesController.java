package ru.virtu.my_test_app_01.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.virtu.my_test_app_01.dto.*;
import ru.virtu.my_test_app_01.models.House;
import ru.virtu.my_test_app_01.models.Person;
import ru.virtu.my_test_app_01.security.PersonDetails;
import ru.virtu.my_test_app_01.services.AdminService;
import ru.virtu.my_test_app_01.services.HousesService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/houses")
public class HousesController {
    private final AdminService adminService;
    private final HousesService housesService;
    private final ModelMapper modelMapper;

    @Autowired
    public HousesController(AdminService adminService, HousesService housesService, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.housesService = housesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<HouseWithOccupantsDTO> getAllHouses() {
        return convertToHouseWithOccupantsDTO(housesService.findAll());
    }

    @GetMapping("/{id}/get")
    public HouseWithOccupantsDTO getHouse(@PathVariable Long id){
        return convertToHouseWithOccupantsDTO(housesService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addHouse(@RequestBody @Valid HouseDTO houseDTO){
        Person person = getPersonFromSecurityContext();

        House house = convertToHouse(houseDTO);
        house.setOwner(person);
        housesService.save(house);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HttpStatus> updateHouse(@PathVariable Long id, @RequestBody @Valid HouseDTO houseDTO){

        House house = convertToHouse(houseDTO);
        housesService.update(id, house);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity <HttpStatus> deleteHouse(@PathVariable Long id) {
        Person person = getPersonFromSecurityContext();

        if(housesService.delete(id, person)){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else {
            return  ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/add_occupant")
    public ResponseEntity<HttpStatus> addHousesOccupant(@PathVariable Long id, @RequestBody @Valid OccupantDTO occupantDTO) {
        Person person = getPersonFromSecurityContext();

        if (housesService.addOccupant(id, occupantDTO, person)){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else {
            return  ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}/delete_occupants")
    public ResponseEntity <HttpStatus> deleteAllHousesOccupants(@PathVariable Long id) {
        Person person = getPersonFromSecurityContext();

        if(housesService.deleteAllOccupants(id, person)){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else {
            return  ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }

    public HouseDTO convertToHouseDTO(House house){
        return modelMapper.map(house, HouseDTO.class);
    }

    public House convertToHouse(HouseDTO houseDTO){
        return modelMapper.map(houseDTO, House.class);
    }

    public HouseWithOccupantsDTO convertToHouseWithOccupantsDTO(House house){
        return new HouseWithOccupantsDTO(
                house.getId(), house.getAddress(), convertToOwnerDTO(house.getOwner()), convertToPersonDTOList(house.getPersonList())
        );
    }

    private List<HouseWithOccupantsDTO> convertToHouseWithOccupantsDTO(List<House> houses){
        List<HouseWithOccupantsDTO> houseDTOList = new ArrayList<>();

        for (House house:
                houses) {
            houseDTOList.add(convertToHouseWithOccupantsDTO(house));
        }

        return houseDTOList;
    }

    private OwnerDTO convertToOwnerDTO(Person person){
        return modelMapper.map(person, OwnerDTO.class);
    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    private List<PersonDTO> convertToPersonDTOList(List<Person> people){
        List<PersonDTO> personDTOList = new ArrayList<>();

        for (Person person:
                people) {
            personDTOList.add(convertToPersonDTO(person));
        }

        return personDTOList;
    }

    public static Person getPersonFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }
}
