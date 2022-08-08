create table document
(
    id     varchar(256) primary key,
    name   varchar(256) not null,
    size   bigint,
    owner  varchar(256),
    public boolean not null default false
);
