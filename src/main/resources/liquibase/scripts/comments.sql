--liquibase formatted sql

--changeset vhodakovskiy:1

create table if not exists public.comments
(
    author                  INT,
    author_image            varchar,
    author_first_name       varchar,
    created_at              BIGINT,
    pk                      INT PRIMARY KEY,
    text                    varchar(64)
    );

CREATE TABLE ads (
    id              INT PRIMARY KEY,
    title           varchar,
    description     varchar,
    price           INT,
    image           varchar,
    author          INT
    );

CREATE TABLE users (
    id               INT PRIMARY KEY,
    username         varchar NOT NULL UNIQUE,
    password         varchar NOT NULL,
    first_name       varchar NOT NULL,
    last_name        varchar NOT NULL,
    phone            varchar NOT NULL,
    role             varchar NOT NULL
    );
