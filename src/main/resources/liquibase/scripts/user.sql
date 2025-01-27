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


