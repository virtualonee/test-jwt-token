package ru.virtu.my_test_app_01.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.my_test_app_01.dto.OccupantDTO;
import ru.virtu.my_test_app_01.models.Person;
import ru.virtu.my_test_app_01.repositories.HousesRepository;
import ru.virtu.my_test_app_01.models.House;
import ru.virtu.my_test_app_01.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class HousesService {

    private final HousesRepository housesRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public HousesService(HousesRepository housesRepository, PeopleRepository peopleRepository) {
        this.housesRepository = housesRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<House> findAll() {

        return housesRepository.findAll();
    }
    

    public House findOne(Long id) {
        Optional<House> house = housesRepository.findById(id);
        return house.orElse(null);
    }

    @Transactional
    public void save(House house) {
        housesRepository.save(house);
    }

    @Transactional
    public void update(Long id, House updatedHouse) {
        House house = housesRepository.findById(id).orElse(null);
        updatedHouse.setOwner(house.getOwner());
        updatedHouse.setPersonList(house.getPersonList());

        updatedHouse.setId(id);
        housesRepository.save(updatedHouse);
    }

    @Transactional
    public void delete(Long id) {
        housesRepository.deleteById(id);
    }

    @Transactional
    public Boolean addOccupant(Long id, OccupantDTO housesOccupantDTO, Person user) {
        House house = housesRepository.findById(id).orElse(null);
        if (house == null){
            return false;
        }
        else if (!house.getOwner().equals(user)){
            return false;
        }
        Person occupant = peopleRepository.findByUsername(housesOccupantDTO.getUsername()).orElse(null);

        if (occupant == null){
            return false;
        }
        List<Person> people = house.getPersonList();
        if (people.contains(occupant)){
            return false;
        }
        occupant.setHouses(Collections.singletonList(house));
        peopleRepository.save(occupant);

        return true;
    }

    @Transactional
    public void deleteAllOccupants(Long id){
        House house = housesRepository.findById(id).orElse(null);
        if (house == null){
            return;
        }
        house.setPersonList(null);
    }
}
