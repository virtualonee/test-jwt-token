package ru.virtu.my_test_app_01.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 symbols")
    @Column(name = "username")
    private String username;

    @Min(value = 0, message = "Age should be more then 0")
    @Max(value = 100, message = "Age should be less then 100")
    @Column(name = "age")
    private Short age;

    @Column(name = "password")
    @Size(min = 5, max = 100, message = "Password should be from 5 to 200 symbols")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "owner")
    private List<House> ownedHouses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_house",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id"))
    private List<House> houses;

    public Person(String username, Short age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username) && Objects.equals(age, person.age) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, age, password);
    }
}