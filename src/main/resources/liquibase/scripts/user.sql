-- liquibase formatted sql

-- changeset Yuri-73:1

CREATE TABLE ads (
    id INT GENERATED ALWAYS AS IDENTITY,
    title TEXT,
    description TEXT,
    price INT,
    image_id INT,
    user_id INT,
    CONSTRAINT ads_pk PRIMARY KEY (id)
    );

CREATE TABLE users (
                       id INT GENERATED ALWAYS AS IDENTITY,
                       username TEXT NOT NULL UNIQUE,
                       password TEXT NOT NULL,
                       first_name TEXT NOT NULL,
                       last_name TEXT NOT NULL,
                       phone TEXT NOT NULL,
                       role TEXT NOT NULL,

                       CONSTRAINT users_pk PRIMARY KEY (id)
                       );

