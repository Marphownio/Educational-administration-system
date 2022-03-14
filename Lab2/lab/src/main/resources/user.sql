create table if not exists user
(
    id           varchar(8)     not null unique primary key,
    password     varchar(32) not null default '123456',
    name         varchar(32) not null,
    role         int         not null,
    id_number    varchar(18)    not null unique,
    phone_number varchar(11) unique,
    email        varchar(32) unique
);

replace into user(id, password, name, role, id_number) values ('admin', 'admin', 'admin', 0, 'admin');
