--liquibase formatted sql

-- changeset Yuri-73:1
CREATE TABLE public.image
(
    id          SERIAL PRIMARY KEY,
    image_path  VARCHAR(255)
);

--changeset vhodakovskiy:1
CREATE TABLE public.users
(
    id                  SERIAL PRIMARY KEY,
    username            varchar(255) NOT NULL UNIQUE,
    password            varchar(255) NOT NULL,
    firstname           varchar(255) NOT NULL,
    lastname            varchar(255) NOT NULL,
    phone               varchar(255) NOT NULL,
    role                varchar(255) NOT NULL,
    author_image_id     INTEGER,

    FOREIGN KEY (author_image_id) REFERENCES public.image (id)
);

CREATE TABLE public.ads
(
    id              SERIAL PRIMARY KEY,
    title           varchar(255) NOT NULL,
    description     varchar(255) NOT NULL,
    price           INTEGER      NOT NULL,
    ad_image_id     INTEGER,
    user_id         INTEGER      NOT NULL,


    FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE,
    FOREIGN KEY (ad_image_id) REFERENCES public.image (id)
);

create table public.comments
(
    pk              SERIAL PRIMARY KEY,
    author_id       INT          NOT NULL,
    created_At      TIMESTAMP    NOT NULL,
    text            varchar(255) NOT NULL,
    ad_id           INTEGER      NOT NULL,

    FOREIGN KEY (ad_id) REFERENCES ads (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES public.users (id) ON DELETE CASCADE
);




