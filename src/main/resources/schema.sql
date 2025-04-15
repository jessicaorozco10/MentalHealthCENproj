create table if not exists availability
(
    id          integer
        primary key,
    day_of_week tinyint,
    check (day_of_week between 0 and 6)
);

create table if not exists discussion_comments
(
    id                 integer
        primary key,
    content            varchar(255),
    created_date       timestamp,
    created_by_user_id integer not null,
    discussion_id      integer not null
);

create table if not exists discussions
(
    id                 integer
        primary key,
    content            varchar(255),
    created_date       timestamp,
    title              varchar(255),
    created_by_user_id integer not null
);

create table if not exists roles
(
    id     integer
        primary key,
    name   varchar(255) not null
        unique,
    status tinyint,
    check (status between 0 and 1)
);

create table if not exists therapists
(
    id             integer
        primary key,
    experience     integer not null,
    license_info   varchar(255),
    specialization varchar(255),
    user_id        integer
        unique
);

create table if not exists therapists_availability
(
    therapist_id    integer not null,
    availability_id integer not null
);

create table if not exists user_roles
(
    user_id  integer not null,
    roles_id integer not null
);

create table if not exists users
(
    id               INTEGER
        constraint USERS_PK
            primary key,
    first_name       VARCHAR,
    last_name        VARCHAR,
    password         VARCHAR,
    email            VARCHAR,
    publication_date DATE,
    user_type        TINYINT
);

