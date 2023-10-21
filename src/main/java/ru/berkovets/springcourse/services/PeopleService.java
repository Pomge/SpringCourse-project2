package ru.berkovets.springcourse.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;
import ru.berkovets.springcourse.repositories.PeopleRepository;

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

	public Person findById(int id) {
		Optional<Person> person = peopleRepository.findById(id);
		return person.orElse(null);
	}
	
	public Person findByFio(String fio) {
		Optional<Person> person = peopleRepository.findByFio(fio);
		return person.orElse(null);
	}
	
	public List<Book> getBooksById(int id) {
		Person owner = findById(id);
		
		if (owner != null) {
			Hibernate.initialize(owner.getBooks());
			List<Book> books = owner.getBooks();
			
			Date today = new Date();
			for (Book book : books) {
				long diffInMillies = today.getTime() - book.getTaken_at().getTime();
				long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				
				if (diffInDays > 10) {
					book.setOverdue(true);
				}
			}
			return books;
		} else {
			return null;
		}
	}

	@Transactional
	public void save(Person person) {
		peopleRepository.save(person);
	}

	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepository.save(updatedPerson);
	}

	@Transactional
	public void delete(int id) {
		peopleRepository.deleteById(id);
	}
}
