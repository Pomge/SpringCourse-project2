package ru.berkovets.springcourse.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;
import ru.berkovets.springcourse.repositories.BooksRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	private final BooksRepository booksRepository;

	@Autowired
	public BooksService(BooksRepository booksRepository) {
		this.booksRepository = booksRepository;
	}

	public List<Book> findAll() {
		return booksRepository.findAll();
	}

	public List<Book> findAll(Integer page, Integer books_per_page, Boolean sort_by_year) {
		if (page != null && books_per_page != null) {
			if (sort_by_year != null && sort_by_year) {
				return booksRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
			} else {
				return booksRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
			}
		} else {
			if (sort_by_year != null && sort_by_year) {
				return booksRepository.findAll(Sort.by("year"));
			} else {
				return booksRepository.findAll();
			}
		}
	}

	public Book findById(int id) {
		Optional<Book> book = booksRepository.findById(id);
		return book.orElse(null);
	}

	public List<Book> findByNameStartingWith(String name) {
		if (name == null) {
			name = "";
		}
		List<Book> books = booksRepository.findByNameStartingWith(name);
		return books;
	}

	@Transactional
	public void save(Book book) {
		booksRepository.save(book);
	}

	@Transactional
	public void update(int id, Book updatedBook) {
		updatedBook.setId(id);
		booksRepository.save(updatedBook);
	}

	@Transactional
	public void setPersonId(int id, Person person) {
		Book book = findById(id);
		book.setTaken_at(new Date());
		book.setOwner(person);
	}

	@Transactional
	public void freePersonId(int id) {
		Book book = findById(id);
		book.setOverdue(false);
		book.setOwner(null);
	}

	@Transactional
	public void delete(int id) {
		booksRepository.deleteById(id);
	}
}