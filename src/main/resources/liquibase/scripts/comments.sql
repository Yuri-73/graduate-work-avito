--liquibase formatted sql

--changeset vhodakovskiy:1

CREATE TABLE public.users
(
    id         SERIAL PRIMARY KEY,
    username   varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    firstname  varchar(255) NOT NULL,
    lastname   varchar(255) NOT NULL,
    phone      varchar(255) NOT NULL,
    role       varchar(255) NOT NULL,
    image      BYTEA
);

CREATE TABLE public.ads
(
    id          SERIAL PRIMARY KEY,
    title       varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    price       INTEGER NOT NULL,
    image       BYTEA,

    user_id     INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.users (id)
);

create table public.comments
(
    pk                INT PRIMARY KEY,
    author            INT,
    author_Image      varchar(255),
    author_First_name varchar(255),
    created_At        BIGINT,
    text              varchar(255)
);
