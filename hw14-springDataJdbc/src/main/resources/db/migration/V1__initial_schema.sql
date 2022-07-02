create table client
(
    id         bigserial primary key,
    name       varchar(50) not null
);

create table address
(
    id     bigserial primary key,
    street varchar(50) not null,
    client_id bigint references client
);

create table phone
(
    id        bigserial primary key,
    number    varchar(50) not null,
    client_id bigint not null references client
);
