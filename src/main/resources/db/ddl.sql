drop table if exists book_author;
drop table if exists book_genre;
drop table if exists book_publisher;
drop table if exists book;
drop table if exists author;
drop table if exists genre;
drop table if exists publisher;

create table if not exists book
(
    book_id    		bigserial    primary key,
    title 		    varchar(255)    not null,
    description     text            not null
);

create table if not exists author
(
    author_id       bigserial   primary key,
    author_name     varchar(255)    not null
);

create table if not exists genre
(
    genre_id    bigserial   primary key,
    genre_name 	varchar(255)    not null
);

create table if not exists publisher
(
    publisher_id    bigserial   primary key,
    publisher_name 	varchar(255)    not null
);

create table if not exists book_author
(
    book_id 	bigint,
    author_id   bigint,
    CONSTRAINT fk_book FOREIGN key (book_id) references book (book_id) on delete cascade,
    CONSTRAINT fk_author FOREIGN key (author_id) references author (author_id) on delete cascade,
    PRIMARY KEY (book_id, author_id)
);

create table if not exists book_genre
(
    book_id 	bigint,
    genre_id    bigint,
    CONSTRAINT fk_book FOREIGN key (book_id) references book (book_id) on delete cascade,
    CONSTRAINT fk_genre FOREIGN key (genre_id) references genre (genre_id) on delete cascade,
    PRIMARY KEY (book_id, genre_id)
);

create table if not exists book_publisher
(
    book_id 	    bigint,
    publisher_id    bigint,
    CONSTRAINT fk_book FOREIGN key (book_id) references book (book_id) on delete cascade,
    CONSTRAINT fk_publisher FOREIGN key (publisher_id) references publisher (publisher_id) on delete cascade,
    PRIMARY KEY (book_id, publisher_id)
);
