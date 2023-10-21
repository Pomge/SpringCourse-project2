drop table Book;
drop table Person;

create table Person(
	id int generated by default as identity primary key,
	fio varchar(80) not null unique,
	year int not null
);

create table Book(
	id int generated by default as identity primary key,
	person_id int references Person(id) ON DELETE SET NULL,
	name varchar(80) not null unique,
	author varchar(80) not null,
	year int not null,
	taken_at timestamp
);

select * from Person;
select * from Book;