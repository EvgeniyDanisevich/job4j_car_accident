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

insert into type values (1, 'Две машины');
insert into type values (2, 'Машина и человек');
insert into type values (3, 'Машина и велосипед');

insert into rule values (1, 'Статья 1');
insert into rule values (2, 'Статья 2');
insert into rule values (3, 'Статья 3');