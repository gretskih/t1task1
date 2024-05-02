create table url (
    id serial primary key,
    url varchar(70) not null unique,
    code varchar(10) not null
);