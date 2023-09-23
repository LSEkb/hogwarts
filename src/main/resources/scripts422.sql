create table car(
id serial primary key,
make varchar(30),
model varchar(30),
cost money
);
create table people(
id serial primary key,
name varchar(30) not null,
age smallserial,
license boolean default false,
car_id serial references car(id)
)