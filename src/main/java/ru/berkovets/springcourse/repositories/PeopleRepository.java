package ru.berkovets.springcourse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.berkovets.springcourse.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByFio(String fio);
}