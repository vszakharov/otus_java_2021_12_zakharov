create table address
(
    id     bigserial primary key,
    street varchar(50)
);

create table client
(
    id         bigserial primary key,
    name       varchar(50),
    address_id bigint references address
);

create table phone
(
    id        bigserial primary key,
    number    varchar(50),
    client_id bigint not null references client
);
