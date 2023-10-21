package ru.berkovets.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.berkovets.springcourse.models.Person;
import ru.berkovets.springcourse.services.PeopleService;

@Component
public class PersonValidator implements Validator {

	private final PeopleService peopleService;

	@Autowired
	public PersonValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		Person personFromDataBase = peopleService.findByFio(person.getFio());
		if (personFromDataBase != null && (person.getId() != personFromDataBase.getId())) {
			errors.rejectValue("fio", "", "This fio is already exist");
		}
	}

}
