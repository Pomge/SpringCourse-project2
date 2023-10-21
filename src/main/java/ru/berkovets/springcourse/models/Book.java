package ru.berkovets.springcourse.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Book")
public class Book {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person owner;

	@NotEmpty(message = "Book name should not be empty")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Book author should not be empty")
	@Column(name = "author")
	private String author;

	@Min(value = 0, message = "Year should be greater than 0")
	@Max(value = 2023, message = "Year should be less than 2023")
	@Column(name = "year")
	private int year;

	@Column(name = "taken_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date taken_at;

	@Transient
	private boolean overdue;

	public Book() {

	}

	public Book(String name, String author, int year) {
		super();
		this.name = name;
		this.author = author;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getTaken_at() {
		return taken_at;
	}

	public void setTaken_at(Date taken_at) {
		this.taken_at = taken_at;
	}

	public boolean isOverdue() {
		return overdue;
	}

	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}

}
