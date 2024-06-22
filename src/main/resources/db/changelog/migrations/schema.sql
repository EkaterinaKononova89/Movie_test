create table if not exists movie
(
    id bigserial primary key,
    name varchar not null,
    description varchar(300) not null,
    film_type varchar not null,
    genre varchar not null,
    release_date date not null
);