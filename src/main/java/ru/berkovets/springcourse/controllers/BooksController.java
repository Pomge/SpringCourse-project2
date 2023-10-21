package ru.berkovets.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;
import ru.berkovets.springcourse.services.BooksService;
import ru.berkovets.springcourse.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {

	private final BooksService booksService;
	private final PeopleService peopleService;

	@Autowired
	public BooksController(BooksService booksService, PeopleService peopleService) {
		this.booksService = booksService;
		this.peopleService = peopleService;
	}

	@GetMapping()
	public String index(Model model, @RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "books_per_page", required = false) Integer books_per_page,
			@RequestParam(name = "sort_by_year", required = false) Boolean sort_by_year) {
		model.addAttribute("books", booksService.findAll(page, books_per_page, sort_by_year));
		return "books/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
		Book book = booksService.findById(id);
		model.addAttribute("book", book);
		Person owner = book.getOwner();

		if (owner == null) {
			model.addAttribute("people", peopleService.findAll());
		} else {
			model.addAttribute("owner", owner);
		}

		return "books/show";
	}

	@PatchMapping("/{id}/occupy")
	public String occupy(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
		booksService.setPersonId(id, person);

		return "redirect:/books/{id}";
	}

	@PatchMapping("/{id}/free")
	public String free(@PathVariable("id") int id) {
		booksService.freePersonId(id);

		return "redirect:/books/{id}";
	}

	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/new";
		}

		booksService.save(book);
		return "redirect:/books";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		model.addAttribute("books", booksService.findByNameStartingWith(keyword));
		return "books/search";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", booksService.findById(id));
		return "books/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
			@PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "book/edit";
		}

		booksService.update(id, book);
		return "redirect:/books/{id}";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		booksService.delete(id);
		return "redirect:/books";
	}
}
