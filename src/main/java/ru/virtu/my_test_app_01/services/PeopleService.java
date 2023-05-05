package ru.virtu.my_test_app_01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virtu.my_test_app_01.models.Person;
import ru.virtu.my_test_app_01.repositories.HousesRepository;
import ru.virtu.my_test_app_01.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    

    public Person findOne(Long id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Long id, Person updatedPerson) {
        Person person = peopleRepository.findById(id).get();

        updatedPerson.setId(id);
        updatedPerson.setHouses(person.getHouses());
        updatedPerson.setPassword(person.getPassword());
        updatedPerson.setRole(person.getRole());

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(Long id) {
        peopleRepository.deleteById(id);
    }
}
