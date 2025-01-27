--liquibase formatted sql

--changeset vhodakovskiy:1

create table if not exists public.comments
(
    pk                  BIGSERIAL PRIMARY KEY,
    author              BIGINT,
    authorImage         varchar,
    authorFirstname     varchar,
    createdAt           BIGINT,
    query               varchar(64)
    );

CREATE TABLE ads (
    id              BIGSERIAL PRIMARY KEY,
    title           varchar,
    description     varchar,
    price           INT,
    image_id        INT,
    user_id         INT
    );

CREATE TABLE users (
    id               BIGSERIAL PRIMARY KEY,
    username         varchar NOT NULL UNIQUE,
    password         varchar NOT NULL,
    first_name       varchar NOT NULL,
    last_name        varchar NOT NULL,
    phone            varchar NOT NULL,
    role             varchar NOT NULL
    );
