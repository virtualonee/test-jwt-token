package ru.virtu.my_test_app_01.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.virtu.my_test_app_01.models.House;

import java.util.Optional;

@Repository
public interface HousesRepository extends JpaRepository<House, Long> {
    Optional<House> findByAddress(String name);
}

