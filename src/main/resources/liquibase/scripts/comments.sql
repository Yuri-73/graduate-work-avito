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
