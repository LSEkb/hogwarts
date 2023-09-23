--liquibase formatted sql

--changeset laserova:1
create index faculty_name_and_color_index on faculty(name, color)
