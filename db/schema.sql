create table if not exists type(
    id serial primary key,
    name VARCHAR(255)
);

create table if not exists rule(
    id serial primary key,
    name VARCHAR(255)
);

create table if not exists accident (
    id serial primary key,
    name varchar(255),
    text text,
    address varchar(255),
    type_id int references type(id)
);

create table if not exists rule_accident(
    id serial primary key,
    accident_id int references accident(id),
    rule_id int references rule(id)
);